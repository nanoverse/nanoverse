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
import nanoverse.runtime.control.arguments.IntegerArgument;
import nanoverse.runtime.control.halt.HaltCondition;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.layers.LayerManager;
import nanoverse.runtime.layers.cell.AgentUpdateManager;
import nanoverse.runtime.processes.discrete.ShoveHelper;

import java.util.Random;

/**
 * Places a copy or copies of the current cell toward any vacant location.
 * <p>
 * This uses the "replicate" method, rather than the "divide" method, meaning
 * that the state of the cell is exactly preserved.
 * <p>
 * Created by dbborens on 5/2/14.
 */
public class Expand extends Action {

    // Highlight channels for the targeting and targeted nanoverse.runtime.cells
    private IntegerArgument selfChannel;
    private IntegerArgument targetChannel;

    // Displaces nanoverse.runtime.cells along a trajectory in the event that the cell is
    // divided into an occupied site and replace is disabled.
    private ShoveHelper shoveHelper;

    private Random random;

    public Expand(Agent callback, LayerManager layerManager,
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

        // Step 1: identify nearest vacant site.
        Coordinate target = shoveHelper.chooseVacancy(parentLocation);

        // Step 2: shove parent toward nearest vacant site.
        shoveHelper.shove(parentLocation, target);

        // Step 3: Clone parent.
        Agent child = getCallback().copy();

        // Step 4: Place child in parent location.
        u.place(child, parentLocation);

        // Step 5: Clean up out-of-bounds nanoverse.runtime.cells.
        shoveHelper.removeImaginary();

        // Step 6: Highlight the parent and target locations.
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
    public Action clone(Agent child) {
        return new Expand(child, getLayerManager(), selfChannel, targetChannel,
            random);
    }
}
