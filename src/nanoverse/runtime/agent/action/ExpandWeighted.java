/*
 * Copyright (c) 2014, 2015 David Bruce Borenstein, Annie Maslan,
 * and the Trustees of Princeton University.
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

import nanoverse.runtime.agent.BehaviorAgent;
import nanoverse.runtime.agent.AbstractAgent;
import nanoverse.runtime.control.arguments.IntegerArgument;
import nanoverse.runtime.control.halt.HaltCondition;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.layers.LayerManager;
import nanoverse.runtime.layers.cell.AgentUpdateManager;
import nanoverse.runtime.processes.discrete.ShoveHelper;

import java.util.*;

/**
 * Places a copy or copies of the current cell toward any vacant location
 * (not necessarily closest).
 * <p>
 * This uses the "replicate" method, rather than the "divide" method, meaning
 * that the state of the cell is exactly preserved.
 * <p>
 * Created by annie on 3/15/15.
 */
public class ExpandWeighted extends Action {

    // Highlight channels for the targeting and targeted nanoverse.runtime.cells
    private IntegerArgument selfChannel;
    private IntegerArgument targetChannel;

    // Displaces nanoverse.runtime.cells along a trajectory in the event that the cell is
    // divided into an occupied site and replace is disabled.
    private ShoveHelper shoveHelper;

    private Random random;

    public ExpandWeighted(BehaviorAgent callback, LayerManager layerManager,
                          IntegerArgument selfChannel, IntegerArgument targetChannel, Random random) {

        super(callback, layerManager);
        this.selfChannel = selfChannel;
        this.targetChannel = targetChannel;
        this.random = random;

        shoveHelper = new ShoveHelper(layerManager, random);
    }

    @Override
    public void run(Coordinate caller) throws HaltCondition {
        Coordinate parentLocation = getOwnLocation();

        AgentUpdateManager u = getLayerManager().getAgentLayer().getUpdateManager();

        // Step 1: shove parent toward vacant site in a cardinal direction; choice
        // weighted by the distance to the vacancy in each of the directions
        HashSet<Coordinate> affectedSites = shoveHelper.shoveWeighted(parentLocation);

        // Step 2: Clone parent.
        AbstractAgent child = getCallback().replicate();

        // Step 3: Place child in parent location.
        u.place(child, parentLocation);

        // Step 4: Clean up out-of-bounds nanoverse.runtime.cells.
        shoveHelper.removeImaginary();

        // Step 5: Highlight the parent and target locations.
        //         Sort array of affected sites and take target from appropriate array end
        Coordinate[] affectedArray = affectedSites.toArray(new Coordinate[0]);
        Arrays.sort(affectedArray);
        Coordinate target;
        if (parentLocation.compareTo(affectedArray[0]) == -1) {
            target = affectedArray[affectedArray.length - 1];
        } else {
            target = affectedArray[0];
        }
        highlight(target, parentLocation);
    }

    private void highlight(Coordinate target, Coordinate ownLocation) throws HaltCondition {
        doHighlight(targetChannel, target);
        doHighlight(selfChannel, ownLocation);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        return true;
    }

    @Override
    public Action clone(BehaviorAgent child) {
        return new ExpandWeighted(child, getLayerManager(), selfChannel, targetChannel,
            random);
    }
}
