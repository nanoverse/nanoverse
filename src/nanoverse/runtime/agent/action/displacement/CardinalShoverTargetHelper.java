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

package nanoverse.runtime.agent.action.displacement;

import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.geometry.Geometry;
import nanoverse.runtime.layers.cell.AgentLayer;

import java.util.Random;

/**
 * Created by dbborens on 10/20/2015.
 */
public class CardinalShoverTargetHelper {

    private final AgentLayer layer;
    private final RandomNeighborChooser neighborChooser;

    public CardinalShoverTargetHelper(AgentLayer layer, Random random) {
        this.layer = layer;
        neighborChooser = new RandomNeighborChooser(layer.getGeometry(), random);
    }

    public CardinalShoverTargetHelper(AgentLayer layer, RandomNeighborChooser neighborChooser) {
        this.layer = layer;
        this.neighborChooser = neighborChooser;
    }

    public Coordinate getDisplacementToRandomTarget(Coordinate origin) {
        Coordinate target = neighborChooser.chooseRandomNeighbor(origin);
        Coordinate displacement = layer.getGeometry().
            getDisplacement(origin,
                target, Geometry.APPLY_BOUNDARIES);

        return displacement;
    }
}
