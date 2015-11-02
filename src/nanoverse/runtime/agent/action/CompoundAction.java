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
import nanoverse.runtime.agent.action.helper.*;
import nanoverse.runtime.control.arguments.IntegerArgument;
import nanoverse.runtime.control.halt.HaltCondition;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.layers.LayerManager;

import java.util.List;
import java.util.stream.*;

/**
 * An action that calls other actions on run.
 *
 */
public class CompoundAction extends Action {

    private final List<Action> actionSequence;


    /**
     * Main constructor
     */
    public CompoundAction(Agent callback, LayerManager layerManager, Stream<Action> actions) {
        super(callback, layerManager);
        actionSequence = actions.collect(Collectors.toList());
    }

    /**
     * Testing constructor
     */
    public CompoundAction(ActionIdentityManager identityManager,
               CoordAgentMapper mapper,
               ActionHighlighter highlighter,
               Stream<Action> actions) {

        super(identityManager, mapper, highlighter);
        actionSequence = actions.collect(Collectors.toList());
    }

    public void run(Coordinate caller) throws HaltCondition {
        for (Action action : actionSequence) {
            action.run(caller);
        }
    }

    public Stream<Action> getActionSequence() {
        return actionSequence.stream();
    }

    public CompoundAction copy(Agent child) {
        Stream<Action> clonedActionSequence = cloneActionSequence(child);
        CompoundAction clone = new CompoundAction(child, mapper.getLayerManager(), clonedActionSequence);
        return clone;
    }

    protected Stream<Action> cloneActionSequence(Agent child) {
        return actionSequence.stream().map(action -> action.copy(child));
    }
}
