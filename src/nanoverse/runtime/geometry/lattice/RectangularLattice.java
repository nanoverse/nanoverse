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

package nanoverse.runtime.geometry.lattice;

import nanoverse.runtime.control.identifiers.*;
import nanoverse.runtime.structural.annotations.FactoryTarget;

public class RectangularLattice extends Lattice {

    @FactoryTarget
    public RectangularLattice() {
        super();
    }

    protected void defineBasis() {

        Coordinate east = new Coordinate2D(1, 0, 0);
        Coordinate north = new Coordinate2D(0, 1, 0);

        basis = new Coordinate[]{east, north};
    }

    @Override
    public int getConnectivity() {
        return 2;
    }

    @Override
    public int getDimensionality() {
        return 2;
    }

    @Override
    public Coordinate adjust(Coordinate toAdjust) {
        if (!toAdjust.hasFlag(Flags.PLANAR)) {
            throw new IllegalArgumentException("Rectangular lattice is a planar nanoverse.runtime.geometry.");
        }

        // A rectangular lattice requires no offset adjustment to be consistent
        // with Cartesian coordinates.
        return toAdjust;
    }

    @Override
    public Coordinate invAdjust(Coordinate toAdjust) {
        return toAdjust;
    }

    @Override
    public Coordinate[] getAnnulus(Coordinate coord, int r) {
        // De-index coordinate.
        int x0 = coord.x();
        int y0 = coord.y();

        // r=0 case (a point)
        if (r == 0) {
            return new Coordinate[]{new Coordinate2D(x0, y0, 0)};
        }

        // All other cases
        Coordinate[] ret = new Coordinate[4 * r];

        for (int i = 0; i < r; i++) {
            int j = r - i;

            int base = 4 * i;

            ret[base + 0] = new Coordinate2D(x0 + i, y0 + j, 0);
            ret[base + 1] = new Coordinate2D(x0 + j, y0 - i, 0);
            ret[base + 2] = new Coordinate2D(x0 - i, y0 - j, 0);
            ret[base + 3] = new Coordinate2D(x0 - j, y0 + i, 0);
        }

        return ret;
    }

    @Override
    public Coordinate getOrthoDisplacement(Coordinate pCoord, Coordinate qCoord) {
        return getDisplacement(pCoord, qCoord);
    }

    @Override
    public Coordinate rel2abs(Coordinate coord, Coordinate displacement) {
        if (!displacement.hasFlag(Flags.PLANAR)) {
            throw new IllegalArgumentException("Expected two arguments to rectangular lattice rel2abs.");
        }

        int x = coord.x();
        int y = coord.y();

        // Apply x component
        x += displacement.x();


        // Apply y component
        y += displacement.y();

        Coordinate target = new Coordinate2D(x, y, 0);

        return target;
    }

    @Override
    public Coordinate getDisplacement(Coordinate pCoord, Coordinate qCoord) {
        if (!pCoord.hasFlag(Flags.PLANAR) || !qCoord.hasFlag(Flags.PLANAR)) {
            throw new IllegalArgumentException("Expect planar coordinates in rectangular lattice.");
        }

        int dx = qCoord.x() - pCoord.x();
        int dy = qCoord.y() - pCoord.y();

        return new Coordinate2D(dx, dy, Flags.VECTOR);
    }

    @Override
    public Lattice clone() {
        return new RectangularLattice();
    }

    @Override
    public Coordinate getZeroVector() {
        return new Coordinate2D(0, 0, 0);
    }

    /* Get (naive) size of an annulus of the specified L1 radius from a
     * point, i.e., size assuming no out-of-bounds points.
     */
    private int getAnnulusSize(int radius) {
        if (radius == 0) {
            return (1);
        }

        return (4 * radius);
    }
}
