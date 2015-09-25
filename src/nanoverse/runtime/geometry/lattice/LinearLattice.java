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

package nanoverse.runtime.geometry.lattice;

import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.control.identifiers.Coordinate1D;
import nanoverse.runtime.control.identifiers.Flags;
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
    public Coordinate adjust(Coordinate toAdjust) {
        if (!toAdjust.hasFlag(Flags.PLANAR)) {
            throw new IllegalArgumentException("Linear lattice (unfortunately) " +
                    "expects planar coordinates. I'm working on it.");
        }

        // A rectangular lattice requires no offset adjustment to be consistent
        // with Cartesian coordinates.
        return toAdjust;
    }

    protected void defineBasis() {

        Coordinate north = new Coordinate1D(1, 0);

        basis = new Coordinate[]{north};
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
    public Coordinate rel2abs(Coordinate coord, Coordinate displacement) {
        int x = coord.x();

        if (coord.x() != 0 || coord.z() != 0) {
            throw new IllegalStateException("Expect strictly 0 x and z " +
                    "coordinates for linear lattice.");
        }
        int y = coord.y();

        // Apply x component
        x += displacement.x();


        // Apply y component
        y += displacement.y();

        Coordinate target = new Coordinate1D(y, 0);

        return target;
    }

    @Override
    public Coordinate getOrthoDisplacement(Coordinate pCoord, Coordinate qCoord) {
        return getDisplacement(pCoord, qCoord);
    }

    @Override
    public Coordinate invAdjust(Coordinate toAdjust) {
        return toAdjust;
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
