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

package nanoverse.runtime.agent.action.helper;

import nanoverse.runtime.agent.Agent;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.layers.LayerManager;
import nanoverse.runtime.layers.cell.AgentLayerViewer;

/**
 * Created by dbborens on 10/18/2015.
 */
public class AgentResolver {

    private final LayerManager layerManager;

    public AgentResolver(LayerManager layerManager) {
        this.layerManager = layerManager;
    }

    public Agent resolveAgent(Coordinate coord) {
        AgentLayerViewer viewer = layerManager.getAgentLayer().getViewer();

        if (coord == null) {
            throw new IllegalStateException("Attempting to resolve null coordinate to agent.");
        }
        if (!viewer.isOccupied(coord)) {
            return null;
        }

        Agent result = viewer.getAgent(coord);

        return result;
    }

    public LayerManager getLayerManager() {
        return layerManager;
    }
}
