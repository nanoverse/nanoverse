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

/**
 * Created by dbborens on 10/18/2015.
 */
public class CoordAgentMapper {
    private final AgentResolver resolver;

    public CoordAgentMapper(LayerManager layerManager) {
        resolver = new AgentResolver(layerManager);
    }

    public CoordAgentMapper(AgentResolver resolver) {
        this.resolver = resolver;
    }

    public Agent resolveCaller(Coordinate caller) {
        // The caller is null, indicating that the call came from
        // a top-down process. Return null.
        if (caller == null) {
            return null;
        }

        Agent callerAgent = resolveAgent(caller);
        return callerAgent;
    }

    public Agent resolveAgent(Coordinate coord) {
        return resolver.resolveAgent(coord);
    }

    public LayerManager getLayerManager() {
        return resolver.getLayerManager();
    }

}
