/*
 * Copyright (c) 2014, 2015 David Bruce Borenstein and the
 * Trustees of Princeton University.
 *
 * This file is part of the Nanoverse simulation framework
 * (patent pending).
 *
 * This program is free software: you can redistribute it
 * and/or modify it under the terms of the GNU Affero General
 * Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * This program is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 * PURPOSE.  See the GNU Affero General Public License for
 * more details.
 *
 * You should have received a copy of the GNU Affero General
 * Public License along with this program.  If not, see
 * <http://www.gnu.org/licenses/>.
 */

package nanoverse.runtime.agent.action;

import nanoverse.runtime.agent.Agent;
import nanoverse.runtime.agent.targets.TargetRule;
import nanoverse.runtime.control.arguments.IntegerArgument;
import nanoverse.runtime.control.halt.HaltCondition;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.geometry.Geometry;
import nanoverse.runtime.layers.LayerManager;
import nanoverse.runtime.layers.cell.AgentUpdateManager;
import nanoverse.runtime.processes.discrete.ShoveHelper;

import java.util.*;

/**
 * Places a copy or copies of the current cell at the target site(s).
 * This uses the "replicate" method, rather than the "divide" method, meaning
 * that the state of the cell is exactly preserved.
 * <p>
 * Created by dbborens on 5/2/14.
 */
public class ExpandTo extends Action {

    // Highlight channels for the targeting and targeted nanoverse.runtime.cells
    private IntegerArgument selfChannel;
    private IntegerArgument targetChannel;

    // Displaces nanoverse.runtime.cells along a trajectory in the event that the cell is
    // divided into an occupied site and replace is disabled.
    private ShoveHelper shoveHelper;

    private Random random;

    private TargetRule targetRule;

    public ExpandTo(Agent callback, LayerManager layerManager, TargetRule targetRule,
                    IntegerArgument selfChannel, IntegerArgument targetChannel, Random random) {
        super(callback, layerManager);
        this.selfChannel = selfChannel;
        this.targetChannel = targetChannel;
        this.random = random;
        this.targetRule = targetRule;

        shoveHelper = new ShoveHelper(layerManager, random);
    }

    @Override
    public void run(Coordinate caller) throws HaltCondition {
        Agent callerAgent = mapper.resolveCaller(caller);
        List<Coordinate> targets = targetRule.report(callerAgent);
        for (Coordinate target : targets) {
            preferentialExpand(target);
        }
    }

    private void preferentialExpand(Coordinate target) throws HaltCondition {
        Coordinate origin = identity.getOwnLocation();

        // Find out the shortest shoving path available, given the specified
        // parent and its preferred progeny destination.
        DisplacementOption shortestOption = getShortestOption(target);

        // Create a vacancy either at the parent or child site, depending on
        // which had a shorter shoving path.
        doShove(shortestOption);

        // Now that the nanoverse.runtime.cells have been shoved toward the vacancy, the formerly
        // occupied site is now vacant.
        Coordinate newlyVacant = shortestOption.occupied;

        // Place a cloned cell at the newly vacated position.
        cloneToVacancy(newlyVacant);

        // Clean up out-of-bounds nanoverse.runtime.cells.
        shoveHelper.removeImaginary();

        // Highlight the parent and target locations.
        highlight(target, origin);
    }

    private void cloneToVacancy(Coordinate vacancy) throws HaltCondition {
        AgentUpdateManager u = mapper.getLayerManager().getAgentLayer().getUpdateManager();

        // Clone parent.
        Agent child = identity.getSelf().copy();

        // Place child in parent location.
        u.place(child, vacancy);
    }

    private void doShove(DisplacementOption shortestOption) throws HaltCondition {
        Coordinate occupied = shortestOption.occupied;
        Coordinate vacant = shortestOption.vacant;
        shoveHelper.shove(occupied, vacant);
    }

    /**
     * Compare the distance between the origin and its nearest vacancy,
     * and the target and its nearest vacancy. Report the closer one as the
     * preferred direction of expansion.
     */
    private DisplacementOption getShortestOption(Coordinate target) throws HaltCondition {
        Coordinate origin = identity.getOwnLocation();

        // Get option that starts with origin.
        DisplacementOption originOption = getOption(origin);

        // Get option that starts with target.
        DisplacementOption targetOption = getOption(target);

        // Origin closer to vacancy?
        if (originOption.distance < targetOption.distance) {
            return originOption;
            // Target closer to vacancy?
        } else if (originOption.distance > targetOption.distance) {
            return targetOption;
            // Same?
        } else {
            // The coin toss arbitrarily favors shoving parent on true.
            return (random.nextBoolean() ? originOption : targetOption);
        }
    }

    private DisplacementOption getOption(Coordinate start) throws HaltCondition {
        Geometry geom = mapper.getLayerManager().getAgentLayer().getGeometry();
        Coordinate end = shoveHelper.chooseVacancy(start);
        int distance = geom.getL1Distance(start, end, Geometry.APPLY_BOUNDARIES);

        DisplacementOption ret = new DisplacementOption();
        ret.occupied = start;
        ret.vacant = end;
        ret.distance = distance;

        return ret;
    }

    private void highlight(Coordinate target, Coordinate ownLocation) throws HaltCondition {
        highlighter.doHighlight(targetChannel, target);
        highlighter.doHighlight(selfChannel, ownLocation);
    }

    @Override
    public Action copy(Agent child) {
        TargetRule clonedTargetRule = targetRule.copy(child);
        return new ExpandTo(child, mapper.getLayerManager(), clonedTargetRule, selfChannel,
            targetChannel, random);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        return true;
    }

    private class DisplacementOption {
        // This is the site that would start out occupied and end up vacant
        // (unless occupied == vacant).
        public Coordinate occupied;

        // This is the site that would start out vacant and end up occupied
        // (unless occupied == vacant).
        public Coordinate vacant;

        // L1 distance between origin and vacancy.
        public int distance;
    }
}
