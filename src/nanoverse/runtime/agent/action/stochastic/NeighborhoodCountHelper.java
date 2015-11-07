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

package nanoverse.runtime.agent.action.stochastic;

import nanoverse.runtime.agent.Agent;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.layers.cell.AgentLayer;

import java.util.stream.Stream;

/**
 * Created by dbborens on 10/13/2015.
 */
public class NeighborhoodCountHelper {

    private final AgentLayer layer;
    private final Agent agent;

    public NeighborhoodCountHelper(AgentLayer layer, Agent agent) {
        this.layer = layer;
        this.agent = agent;
    }

    public double getNeighborCount() {
        Coordinate location = layer
                .getLookupManager()
                .getAgentLocation(agent);

        Stream<String> neighborNames = layer.getLookupManager().getNeighborNames(location, true);
        double neighborCount = neighborNames.count();
        return neighborCount;
    }

    public NeighborhoodCountHelper clone(Agent child) {
        return new NeighborhoodCountHelper(layer, child);
    }
}
