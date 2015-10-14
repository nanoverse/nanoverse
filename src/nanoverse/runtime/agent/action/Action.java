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
import nanoverse.runtime.agent.AbstractAgent;
import nanoverse.runtime.control.arguments.IntegerArgument;
import nanoverse.runtime.control.halt.HaltCondition;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.layers.LayerManager;
import nanoverse.runtime.layers.cell.*;
import nanoverse.runtime.processes.StepState;

/**
 * Actions are the consituent members of Behaviors. They
 * are strung together as an ordered list, called an
 * "action sequence." You can think of actions as anonymous,
 * predefined Behaviors.
 * <p>
 * Created by David B Borenstein on 1/22/14.
 */
public abstract class Action {

    private final Agent callback;
    private final LayerManager layerManager;

    public Action(Agent callback, LayerManager layerManager) {
        this.callback = callback;
        this.layerManager = layerManager;
    }

    public abstract void run(Coordinate caller) throws HaltCondition;

    /**
     * Returns the location of the cell whose behavior this is.
     *
     * @return
     */
    protected Coordinate getOwnLocation() {
        AgentLookupManager lookup = getLayerManager().getAgentLayer().getLookupManager();
        Agent self = getCallback();

        // If the acting cell has been removed from the lattice, return no coordinate.
        // It's up to the particular Action to decide what happens at that point.
        if (!getLayerManager().getAgentLayer().getViewer().exists(self)) {
            return null;
        }

        Coordinate location = lookup.getAgentLocation(self);
        return location;
    }

    protected LayerManager getLayerManager() {
        return layerManager;
    }

    public Agent getCallback() {
        return callback;
    }

    protected Agent resolveCaller(Coordinate caller) {
        // The caller is null, indicating that the call came from
        // a top-down process. Return null.
        if (caller == null) {
            return null;
        }

        // Blow up unless target coordinate contains a behavior cell.
        // In that case, return that cell.
        Agent callerAgent = getWithCast(caller);

        return callerAgent;
    }

    protected Agent getWithCast(Coordinate coord) {
        AgentLayerViewer viewer = getLayerManager().getAgentLayer().getViewer();

        if (!viewer.isOccupied(coord)) {
            return null;
//            throw new IllegalStateException("Expected, but did not find, an occupied site at " + coord
//                    + ".");
        }

        AbstractAgent putative = viewer.getAgent(coord);

        if (!(putative instanceof Agent)) {
            throw new UnsupportedOperationException("Only BehaviorAgents and top-down nanoverse.runtime.processes may trigger behaviors.");
        }

        Agent result = (Agent) putative;

        return result;
    }

    /**
     * Actions should be considered equal if they perform
     * the same function, but should NOT be concerned with
     * the identity of the callback.
     *
     * @param obj
     * @return
     */
    public abstract boolean equals(Object obj);

    public abstract Action clone(Agent child);

    protected void doHighlight(IntegerArgument channelArg, Coordinate toHighlight) throws HaltCondition {
        // If not using highlights, do nothing
        if (channelArg == null) {
            return;
        }

        if (!layerManager.getAgentLayer().getGeometry().isInBounds(toHighlight)) {
            return;
        }

        Integer channel = channelArg.next();
        StepState stepState = getLayerManager().getStepState();
        stepState.highlight(toHighlight, channel);
    }

    protected boolean callbackExists() {
        return getLayerManager()
            .getAgentLayer()
            .getViewer()
            .exists(getCallback());
    }
}
