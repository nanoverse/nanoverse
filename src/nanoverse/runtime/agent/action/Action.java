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

import nanoverse.compiler.error.UserError;
import nanoverse.runtime.agent.Agent;
import nanoverse.runtime.agent.action.helper.*;
import nanoverse.runtime.control.arguments.IntegerArgument;
import nanoverse.runtime.control.halt.HaltCondition;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.layers.LayerManager;
import nanoverse.runtime.layers.cell.*;
import nanoverse.runtime.processes.StepState;
import nanoverse.runtime.structural.NotYetImplementedException;

/**
 * Actions are the consituent members of Behaviors. They
 * are strung together as an ordered list, called an
 * "action sequence." You can think of actions as anonymous,
 * predefined Behaviors.
 * <p>
 * Created by David B Borenstein on 1/22/14.
 */
public abstract class Action {

    protected final ActionIdentityManager identity;
    protected final CoordAgentMapper mapper;
    protected final ActionHighlighter highlighter;

    /**
     * Main constructor
     */
    public Action(Agent callback, LayerManager layerManager) {
        identity = new ActionIdentityManager(callback, layerManager.getAgentLayer());
        mapper = new CoordAgentMapper(layerManager);
        highlighter = new ActionHighlighter(layerManager);
    }

    /**
     * Testing constructor
     */
    public Action(ActionIdentityManager identity,
                  CoordAgentMapper mapper,
                  ActionHighlighter highlighter) {

        this.identity = identity;
        this.mapper = mapper;
        this.highlighter = highlighter;
    }

    public abstract void run(Coordinate caller) throws HaltCondition;

    public abstract Action copy(Agent child);


}
