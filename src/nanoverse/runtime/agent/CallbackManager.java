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

package nanoverse.runtime.agent;

import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.layers.LayerManager;
import nanoverse.runtime.layers.cell.*;

/**
 * A helper class for agents that triggers
 * update events related to the life cycle
 * of the agent, such as death or divisibility.
 * <p>
 * Created by dbborens on 2/21/14.
 */
public class CallbackManager {

    private final Agent agent;
    private final LayerManager layerManager;

    public CallbackManager(Agent agent, LayerManager layerManager) {
        this.agent = agent;
        this.layerManager = layerManager;
    }

    /**
     * Signals to the LayerManager that the callback agent is dead
     * and should be removed from the simulation.
     */
    public void die() {
        Coordinate coord = getMyLocation();
        layerManager.getAgentLayer().getUpdateManager().banish(coord);
    }

    public Coordinate getMyLocation() {
        AgentLayer layer = layerManager.getAgentLayer();
        AgentLookupManager lookupManager = layer.getLookupManager();
        Coordinate coord = lookupManager.getAgentLocation(agent);
        return coord;
    }

    public LayerManager getLayerManager() {
        return layerManager;
    }
}
