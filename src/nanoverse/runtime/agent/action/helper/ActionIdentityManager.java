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
import nanoverse.runtime.layers.cell.*;

/**
 * Keeps track of agent that owns this action, and resolves its location.
 *
 * Created by dbborens on 10/18/2015.
 */
public class ActionIdentityManager {
    private final Agent self;
    private final LayerManager layerManager;

    public ActionIdentityManager(Agent self, LayerManager layerManager) {
        this.self = self;
        this.layerManager = layerManager;
    }

    /**
     * Returns the location of the cell whose behavior this is.
     *
     * @return
     */
    public Coordinate getOwnLocation() {
        AgentLayer layer = layerManager.getAgentLayer();
        AgentLookupManager lookup = layer.getLookupManager();

        // If the acting cell has been removed from the lattice, return no coordinate.
        // It's up to the particular Action to decide what happens at that point.
        if (!layer.getViewer().exists(self)) {
            return null;
        }

        Coordinate location = lookup.getAgentLocation(self);
        return location;
    }

    public Agent getSelf() {
        return self;
    }

    public boolean selfExists() {
        AgentLayer layer = layerManager.getAgentLayer();
        return layer.getViewer().exists(self);
    }

}
