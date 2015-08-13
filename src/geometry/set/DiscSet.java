/*
 * Copyright (c) 2014, 2015 David Bruce Borenstein and the
 * Trustees of Princeton University.
 *
 * This file is part of the Nanoverse simulation framework
 * (patent pending).
 *
 * This program is free software: you can redistribute it
 * and/or modify it under the terms of the GNU Affero General
 * Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * This program is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 * PURPOSE.  See the GNU Affero General Public License for
 * more details.
 *
 * You should have received a copy of the GNU Affero General
 * Public License along with this program.  If not, see
 * <http://www.gnu.org/licenses/>.
 */

package geometry.set;

import control.arguments.*;
import control.halt.HaltCondition;
import control.identifiers.Coordinate;
import geometry.Geometry;
import structural.annotations.FactoryTarget;

/**
 * Created by dbborens on 7/28/14.
 */
public class DiscSet extends CoordinateSet {

    @FactoryTarget(displayName = "DiscCoordinateSet")
    public DiscSet(Geometry geom, Argument<Integer> radiusArg, Coordinate offset) {
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
