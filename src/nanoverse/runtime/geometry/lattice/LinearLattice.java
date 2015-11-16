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

public class LinearLattice extends Lattice {

    @FactoryTarget
    public LinearLattice() {
        super();
    }


    @Override
    public int getConnectivity() {
        return 1;
    }

    @Override
    public int getDimensionality() {
        return 1;
    }

    @Override
    public Coordinate[] getAnnulus(Coordinate coord, int r) {
        // De-index coordinate.
        int x0 = coord.x();

        if (x0 != 0) {
            throw new IllegalStateException("Linear lattice expects strictly zero x  values.");
        }

        int y0 = coord.y();

        // r=0 case (a point)
        if (r == 0) {
            return new Coordinate[]{new Coordinate1D(y0, 0)};
        }

        Coordinate northern = new Coordinate1D(y0 + r, 0);
        Coordinate southern = new Coordinate1D(y0 - r, 0);

        return new Coordinate[]{northern, southern};
    }

    @Override
    public Coordinate getOrthoDisplacement(Coordinate pCoord, Coordinate qCoord) {
        return getDisplacement(pCoord, qCoord);
    }

    @Override
    public Coordinate rel2abs(Coordinate coord, Coordinate displacement) {

        if (coord.x() != 0 || coord.z() != 0) {
            throw new IllegalStateException("Expect strictly 0 x and z " +
                "coordinates for linear lattice.");
        }
        int y = coord.y();

        // Apply y component
        y += displacement.y();

        Coordinate target = new Coordinate1D(y, 0);

        return target;
    }

    @Override
    public Coordinate getDisplacement(Coordinate pCoord, Coordinate qCoord) {
        if (!pCoord.hasFlag(Flags.PLANAR) || !qCoord.hasFlag(Flags.PLANAR)) {
            throw new IllegalArgumentException("Expect planar coordinates in " +
                "linear lattice. (I know, it doesn't make sense. I have not" +
                "yet implemented 1D coordinates.)");
        }

        if (pCoord.x() != 0 || pCoord.z() != 0) {
            throw new IllegalStateException("Expect strictly 0 x coordinate for" +
                " linear lattice.");
        }
        int dy = qCoord.y() - pCoord.y();

        return new Coordinate1D(dy, Flags.VECTOR);
    }

    @Override
    public Lattice clone() {
        return new LinearLattice();
    }

    @Override
    public Coordinate getZeroVector() {
        return new Coordinate1D(0, 0);
    }
}
