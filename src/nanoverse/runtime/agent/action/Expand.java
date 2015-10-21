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
import nanoverse.runtime.agent.action.displacement.DisplacementManager;
import nanoverse.runtime.agent.action.helper.*;
import nanoverse.runtime.control.arguments.IntegerArgument;
import nanoverse.runtime.control.halt.HaltCondition;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.layers.LayerManager;
import nanoverse.runtime.layers.cell.AgentUpdateManager;

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

    private DisplacementManager displacementManager;
    private SelfTargetHighlighter stHighlighter;

    private Random random;

    public Expand(Agent callback, LayerManager layerManager,
                  IntegerArgument selfChannel, IntegerArgument targetChannel, Random random) {

        super(callback, layerManager);
        stHighlighter = new SelfTargetHighlighter(highlighter, selfChannel, targetChannel);
        this.random = random;

        displacementManager = new DisplacementManager(layerManager.getAgentLayer(), random);
    }

    public Expand(ActionIdentityManager identity, CoordAgentMapper mapper, ActionHighlighter highlighter, DisplacementManager displacementManager, SelfTargetHighlighter stHighlighter, Random random) {
        super(identity, mapper, highlighter);
        this.displacementManager = displacementManager;
        this.stHighlighter = stHighlighter;
        this.random = random;
    }

    @Override
    public void run(Coordinate caller) throws HaltCondition {
        Coordinate parentLocation = identity.getOwnLocation();

        AgentUpdateManager u = mapper.getLayerManager().getAgentLayer().getUpdateManager();

        // Step 1: identify nearest vacant site.
        Coordinate target = displacementManager.chooseVacancy(parentLocation);

        // Step 2: shove parent toward nearest vacant site.
        displacementManager.shove(parentLocation, target);

        // Step 3: Clone parent.
        Agent child = identity.getSelf().copy();

        // Step 4: Place child in parent location.
        u.place(child, parentLocation);

        // Step 5: Clean up out-of-bounds nanoverse.runtime.cells.
        displacementManager.removeImaginary();

        // Step 6: Highlight the parent and target locations.
        stHighlighter.highlight(target, parentLocation);
    }

    @Override
    public Action copy(Agent child) {
        IntegerArgument selfChannel = stHighlighter.getSelfChannel();
        IntegerArgument targetChannel = stHighlighter.getTargetChannel();
        return new Expand(child, mapper.getLayerManager(), selfChannel, targetChannel,
            random);
    }
}
