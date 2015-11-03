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

package nanoverse.runtime.geometry.set;

import nanoverse.runtime.control.arguments.IntegerArgument;
import nanoverse.runtime.control.halt.HaltCondition;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.geometry.Geometry;
import nanoverse.runtime.structural.annotations.FactoryTarget;

/**
 * Created by dbborens on 7/28/14.
 */
public class DiscSet extends CoordinateSet {

    @FactoryTarget(displayName = "DiscCoordinateSet")
    public DiscSet(Geometry geom, IntegerArgument radiusArg, Coordinate offset) {
        Coordinate origin = geom.rel2abs(geom.getCenter(), offset, Geometry.APPLY_BOUNDARIES);
        int radius;
        try {
            radius = radiusArg.next();
        } catch (HaltCondition ex) {
            throw new IllegalStateException(ex);
        }

        for (int r = 0; r <= radius; r++) {
            Coordinate[] annulus = geom.getAnnulus(origin, r, Geometry.APPLY_BOUNDARIES);
            for (Coordinate c : annulus) {
                add(c);
            }
        }
    }
}
