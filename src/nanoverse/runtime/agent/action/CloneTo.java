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
import nanoverse.runtime.layers.LayerManager;
import nanoverse.runtime.layers.cell.*;

import java.util.*;

/**
 * Places a copy or copies of the current cell at the target site(s).
 * This uses the "replicate" method, meaning that the state of the cell is
 * exactly preserved.
 * <p>
 * Created by dbborens on 5/2/14.
 */
public class CloneTo extends Action {

    private TargetRule targetRule;

    // Replace occupied sites?
    private boolean noReplace;

    // Highlight channels for the targeting and targeted nanoverse.runtime.cells
    private IntegerArgument selfChannel;
    private IntegerArgument targetChannel;

    private Random random;

    public CloneTo(Agent agent, LayerManager layerManager,
                   TargetRule targetRule, boolean noReplace,
                   IntegerArgument selfChannel,
                   IntegerArgument targetChannel, Random random) {

        super(agent, layerManager);
        this.targetRule = targetRule;
        this.selfChannel = selfChannel;
        this.targetChannel = targetChannel;
        this.noReplace = noReplace;
        this.random = random;
    }

    @Override
    public void run(Coordinate caller) throws HaltCondition {
        Agent callerAgent = resolveCaller(caller);

        Coordinate self = getOwnLocation();

        List<Coordinate> targets = targetRule.report(callerAgent);

        AgentUpdateManager u = getLayerManager().getAgentLayer().getUpdateManager();
        AgentLayerViewer v = getLayerManager().getAgentLayer().getViewer();

        for (Coordinate target : targets) {

            // Make replicate
            Agent child = getCallback().copy();

            // Place replicate at target site
            if (!v.isOccupied(target)) {
                u.place(child, target);
            } else if (noReplace) {
                throw new IllegalStateException("In CloneTo: Attempted to " +
                    "place child at occupied position (leading to " +
                    "replacement), but <no-replacment /> flag is set.");
            } else {
                u.banish(target);
                u.place(child, target);
            }
            // Highlight sites
            highlight(target, self);
        }

    }

    private void highlight(Coordinate target, Coordinate ownLocation) throws HaltCondition {
        doHighlight(targetChannel, target);
        doHighlight(selfChannel, ownLocation);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CloneTo cloneTo = (CloneTo) o;

        if (targetRule != null ? !targetRule.equals(cloneTo.targetRule) : cloneTo.targetRule != null)
            return false;

        return true;
    }

    @Override
    public Action clone(Agent child) {
        TargetRule clonedTargeter = targetRule.clone(child);
        return new CloneTo(child, getLayerManager(), clonedTargeter, noReplace,
            selfChannel, targetChannel, random);
    }

    @Override
    public int hashCode() {
        int result = targetRule != null ? targetRule.hashCode() : 0;
        result = 31 * result + (selfChannel != null ? selfChannel.hashCode() : 0);
        result = 31 * result + (targetChannel != null ? targetChannel.hashCode() : 0);
        return result;
    }
}
