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

import java.util.Random;

/**
 * Created by dbborens on 10/20/2015.
 */
public class RandomNeighborChooser {

    private final Geometry geometry;
    private final Random random;

    public RandomNeighborChooser(Geometry geometry, Random random) {
        this.geometry = geometry;
        this.random = random;
    }

    /**
     * choose a random direction to shove among the cardinal directions by selecting
     * one of the neighbors at random. the displacement vector for that choice will
     * be used for all subsequent shoving in the path in shoveCardinal()
     *
     * @param parentLocation
     * @return target neighbor
     */
    public Coordinate chooseRandomNeighbor(Coordinate parentLocation) {
        Coordinate[] options = geometry.getNeighbors(parentLocation, Geometry.APPLY_BOUNDARIES);
        int i = random.nextInt(options.length);
        Coordinate target = options[i];
        return target;
    }
}
