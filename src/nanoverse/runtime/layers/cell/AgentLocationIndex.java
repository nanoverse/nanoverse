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

package nanoverse.runtime.layers.cell;

import nanoverse.runtime.agent.Agent;
import nanoverse.runtime.control.identifiers.Coordinate;

import java.util.IdentityHashMap;

/**
 * Class for associating agents to coordinates, with
 * agents identified by reference (==) rather than
 * object equality.
 * <p>
 * Created by David B Borenstein on 2/5/14.
 */
public class AgentLocationIndex extends IdentityHashMap<Agent, Coordinate> {


    public AgentLocationIndex() {
        super();
    }

    public void add(Agent agent, Coordinate coordinate) {
        if (containsKey(agent)) {
            throw new IllegalStateException("Attempting to overwrite existing location key.");
        }

        put(agent, coordinate);
    }

    public Coordinate locate(Agent agent) {
        if (!containsKey(agent)) {
            throw new IllegalStateException("Attempting to locate a agent that does not have an indexed spatial location.");
        }

        return get(agent);

    }

    @Override
    public Coordinate remove(Object key) {
        return super.remove(key);
    }

    public boolean isIndexed(Agent agent) {
        return containsKey(agent);
    }

}
