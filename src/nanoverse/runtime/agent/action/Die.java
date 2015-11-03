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
import nanoverse.runtime.agent.action.helper.*;
import nanoverse.runtime.control.arguments.IntegerArgument;
import nanoverse.runtime.control.halt.HaltCondition;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.layers.LayerManager;

/**
 * Created by dbborens on 2/10/14.
 */
public class Die extends Action {

    private final IntegerArgument channel;

    /**
     * Main constructor
     */
    public Die(Agent callback, LayerManager layerManager, IntegerArgument channel) {
        super(callback, layerManager);
        this.channel = channel;
    }

    /**
     * Testing constructor
     */
    public Die(ActionIdentityManager identityManager,
               CoordAgentMapper mapper,
               ActionHighlighter highlighter,
               IntegerArgument channel) {

        super(identityManager, mapper, highlighter);
        this.channel = channel;
    }

    @Override
    public void run(Coordinate caller) throws HaltCondition {
        Coordinate location = identity.getOwnLocation();
        highlighter.doHighlight(channel, location);

        Agent self = identity.getSelf();
        self.die();
    }

    @Override
    public Action copy(Agent child) {
        LayerManager lm = mapper.getLayerManager();
        return new Die(child, lm, channel);
    }
}
