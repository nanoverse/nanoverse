/*
 * Nanoverse: a declarative agent-based modeling language for natural and
 * social science.
 *
 * Copyright (c) 2015 David Bruce Borenstein and Nanoverse, LLC.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package nanoverse.runtime.agent.action;

import nanoverse.runtime.agent.Agent;
import nanoverse.runtime.agent.action.displacement.DisplacementManager;
import nanoverse.runtime.agent.action.helper.*;
import nanoverse.runtime.control.arguments.*;
import nanoverse.runtime.control.halt.HaltCondition;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.layers.LayerManager;
import nanoverse.runtime.layers.cell.AgentUpdateManager;
import org.slf4j.*;

import java.util.Random;

/**
 * Places an agent matching the specified descriptor in an adjacent vacancy.
 */
public class Make extends Action {

    private final DisplacementManager displacementManager;
    private final SelfTargetHighlighter stHighlighter;
    private final Logger logger;
    private final AgentDescriptor descriptor;

    private final Random random;

    public Make(Agent callback, LayerManager layerManager, AgentDescriptor descriptor,
                IntegerArgument selfChannel, IntegerArgument targetChannel, Random random) {

        super(callback, layerManager);
        this.descriptor = descriptor;
        stHighlighter = new SelfTargetHighlighter(highlighter, selfChannel, targetChannel);
        this.random = random;

        displacementManager = new DisplacementManager(layerManager.getAgentLayer(), random);
        logger = LoggerFactory.getLogger(Make.class);
    }

    public Make(ActionIdentityManager identity, CoordAgentMapper mapper, ActionHighlighter highlighter, DisplacementManager displacementManager, AgentDescriptor descriptor, SelfTargetHighlighter stHighlighter, Random random) {
        super(identity, mapper, highlighter);
        this.descriptor = descriptor;
        this.displacementManager = displacementManager;
        this.stHighlighter = stHighlighter;
        this.random = random;
        logger = LoggerFactory.getLogger(Make.class);
    }

    @Override
    public void run(Coordinate caller) throws HaltCondition {
        Coordinate parentLocation = identity.getOwnLocation();

        AgentUpdateManager u = mapper.getLayerManager().getAgentLayer().getUpdateManager();

        // Step 1: identify nearest vacant site.
        Coordinate target = displacementManager.chooseVacancy(parentLocation);

        logger.debug("Origin {}. Nearest vacancy is {}.", parentLocation, target);

        // Step 2: shove parent toward nearest vacant site.
        displacementManager.shove(parentLocation, target);

        // Step 3: Clone parent.
        Agent child = descriptor.next();

        logger.debug("Attempting to place child in parent location {}.", parentLocation);
        // Step 4: Place child in parent location.
        u.place(child, parentLocation);

        // Step 5: Clean up out-of-bounds agents.
        displacementManager.removeImaginary();

        // Step 6: Highlight the parent and target locations.
        stHighlighter.highlight(target, parentLocation);
    }

    @Override
    public Action copy(Agent child) {
        IntegerArgument selfChannel = stHighlighter.getSelfChannel();
        IntegerArgument targetChannel = stHighlighter.getTargetChannel();
        return new Make(child, mapper.getLayerManager(), descriptor, selfChannel, targetChannel,
            random);
    }
}
