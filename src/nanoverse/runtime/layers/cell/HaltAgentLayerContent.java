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
import nanoverse.runtime.control.halt.BoundaryReachedEvent;
import nanoverse.runtime.control.identifiers.*;
import nanoverse.runtime.geometry.Geometry;

/**
 * A cell layer content object that throws a BoundaryReachedEvent
 * Created by David B Borenstein on 4/10/14.
 */
public class HaltAgentLayerContent extends InfiniteAgentLayerContent {

    public HaltAgentLayerContent(Geometry geom, AgentLayerIndices indices) {
        super(geom, indices);
    }

    @Override
    public void put(Coordinate coord, Agent current) throws BoundaryReachedEvent {
        if (coord.hasFlag(Flags.END_OF_WORLD)) {
            throw new BoundaryReachedEvent();
        }
        super.put(coord, current);
    }
}
