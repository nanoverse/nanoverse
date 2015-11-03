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
public class WrapHelper1D extends WrapHelper {

    private final int length;

    public WrapHelper1D(Shape shape, Lattice lattice) {
        super(shape, lattice);

        if (shape.getClass() != Line.class) {
            throw new UnsupportedOperationException("WrapHelper1D only supports Line arenas.");
        }

        Line line = (Line) shape;

        length = line.getDimensions()[0];
    }

    public Coordinate wrapAll(Coordinate toWrap) {
        checkValid(toWrap);
        Coordinate wrapped = yWrap(toWrap);
        return wrapped;
    }

    public Coordinate xWrap(Coordinate toWrap) {
        throw new UnsupportedOperationException();
    }

    public Coordinate yWrap(Coordinate toWrap) {
        checkValid(toWrap);
        Coordinate ret = toWrap;

        // Wrap y.
        int over = shape.getOverbounds(ret).y();

        while (over != 0) {
            if (over > 0) {
                // over == 1 --> wrap to 0
                ret = new Coordinate2D(ret.x(), over - 1, ret.flags());

            } else {
                // over == -1 --> wrap to xMax (which is width - 1)
                ret = new Coordinate2D(ret.x(), length + over, ret.flags());
            }

            over = shape.getOverbounds(ret).y();
        }

        // Return.
        return ret;
    }


    public Coordinate zWrap(Coordinate toWrap) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void checkValid(Coordinate toWrap) {
        if (toWrap.x() != 0 || toWrap.z() != 0) {
            throw new IllegalStateException("WrapHelper1D requires strictly 0 x and z values.");
        }
    }
}
