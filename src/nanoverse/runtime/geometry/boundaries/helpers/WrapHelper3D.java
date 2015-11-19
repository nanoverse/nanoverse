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

package nanoverse.runtime.geometry.boundaries.helpers;

import nanoverse.runtime.control.identifiers.*;
import nanoverse.runtime.geometry.lattice.Lattice;
import nanoverse.runtime.geometry.shape.*;

/**
 * Created by dbborens on 5/7/14.
 */
public class WrapHelper3D extends WrapHelper {

    private final int width, height, depth;

    public WrapHelper3D(Shape shape, Lattice lattice) {
        super(shape, lattice);

        if (shape.getClass() != Cuboid.class) {
            throw new UnsupportedOperationException("WrapHelper3D only supports Cuboid arenas.");
        }

        Cuboid cuboid = (Cuboid) shape;

        width = cuboid.getDimensions()[0];
        height = cuboid.getDimensions()[1];
        depth = cuboid.getDimensions()[2];
    }

    public Coordinate wrapAll(Coordinate toWrap) {
        checkValid(toWrap);
        Coordinate xWrapped = xWrap(toWrap);
        Coordinate yWrapped = yWrap(xWrapped);
        Coordinate wrapped = zWrap(yWrapped);
        return wrapped;
    }

    public Coordinate xWrap(Coordinate toWrap) {
        checkValid(toWrap);
        // Remove any x-adjustment from y.
//        Coordinate ret = lattice.invAdjust(toWrap);
        Coordinate ret = toWrap;
        // Wrap x.
        int over = shape.getOverbounds(ret).x();

        while (over != 0) {
            if (over > 0) {
                // over == 1 --> wrap to 0
                ret = new Coordinate3D(over - 1, ret.y(), ret.z(), ret.flags());

            } else {
                // over == -1 --> wrap to xMax (which is width - 1)
                ret = new Coordinate3D(width + over, ret.y(), ret.z(), ret.flags());
            }
            over = shape.getOverbounds(ret).x();
        }

        // Apply x-adjustment to y.
//        ret = lattice.adjust(ret);

        // Return new coordinate.
        return ret;
    }

    public Coordinate yWrap(Coordinate toWrap) {
        checkValid(toWrap);
        Coordinate ret = toWrap;

        // Wrap y.
        int over = shape.getOverbounds(ret).y();

        while (over != 0) {
            if (over > 0) {
                // over == 1 --> wrap to 0
                ret = new Coordinate3D(ret.x(), over - 1, ret.z(), ret.flags());

            } else {
                // over == -1 --> wrap to xMax (which is width - 1)
                ret = new Coordinate3D(ret.x(), height + over, ret.z(), ret.flags());
            }

            over = shape.getOverbounds(ret).y();
        }

        // Return.
        return ret;
    }


    public Coordinate zWrap(Coordinate toWrap) {
        checkValid(toWrap);
        Coordinate ret = toWrap;

        // Wrap z.
        int over = shape.getOverbounds(ret).z();

        while (over != 0) {
            if (over > 0) {
                // over == 1 --> wrap to 0
                ret = new Coordinate3D(ret.x(), ret.y(), over - 1, ret.flags());

            } else {
                // over == -1 --> wrap to xMax (which is width - 1)
                ret = new Coordinate3D(ret.x(), ret.y(), depth + over, ret.flags());
            }

            over = shape.getOverbounds(ret).z();
        }

        // Return.
        return ret;
    }

    @Override
    protected void checkValid(Coordinate toWrap) {
        if (toWrap.hasFlag(Flags.PLANAR)) {
            throw new IllegalArgumentException("WrapHelper3D requires a 3D coordinate.");
        }
    }
}
