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

package geometry.lattice;

import control.identifiers.Coordinate;
import control.identifiers.Flags;

public class CubicLattice extends Lattice {

    protected void defineBasis() {
        Coordinate i = new Coordinate(1, 0, 0, 0);
        Coordinate j = new Coordinate(0, 1, 0, 0);
        Coordinate k = new Coordinate(0, 0, 1, 0);

        basis = new Coordinate[]{i, j, k};
    }

    @Override
    public int getConnectivity() {
        return 3;
    }

    @Override
    public int getDimensionality() {
        return 3;
    }

    @Override
    public Coordinate adjust(Coordinate i) {
        if (i.hasFlag(Flags.PLANAR)) {
            throw new IllegalArgumentException("Cubic lattice is a 3D geometry.");
        }

        return i;
    }

    @Override
    public Coordinate[] getAnnulus(Coordinate coord, int r) {
        // De-index coordinate.
        int x0 = coord.x();
        int y0 = coord.y();
        int z0 = coord.z();

        // r=0 case (a point)
        if (r == 0) {
            return new Coordinate[]{new Coordinate(x0, y0, z0, 0)};
        }

        /* I'm so bad at series. Let's see...
         *
         * r = 1 --> n = 1 + 4 + 1                  = 2 + 4(1)              = 6
         * r = 2 --> n = 1 + 4 + 8 + 4 + 1          = 2 + 4(2) + 2(4)       = 18
         * r = 3 --> n = 1 + 4 + 8 + 12 + 8 + 4 + 1 = 2 + 4(3) + 2(4 + 8)   = 38
         *                                          = 2 + 4(3) + 8(1 + 2)   = 66
         *
         * Generalizing, I find that
         * n(R) = 2 + 4R + 8*sum_(i=0)^(i=R-2){i+1}.
         *
         * Which is
         * n(R) = -6 + 12R + 8*sum_(i=0)^(i=R-2){i}.
         *
         * Solving that sum (by technology, of course), I get
         * n(R) = -6 + 12R + (4R^2 - 12R + 8)
         *      = 4R^2 + 2.
         *
         * That took me an hour and should have been totally obvious.
         */
        Coordinate[] ret = new Coordinate[(4 * r * r) + 2];

        int offset = 0;
        // Scan from the top of the shell to the bottom.
        for (int dz = 0; dz <= r; dz++) {
            // For a given height z, the points in the plane are just the 2D
            // annulus with radius r-z.
            offset = populateSlices(coord, ret, r, dz, offset);
        }

        return ret;
    }

//    private int getOffsetForSlices(int rShell, int dz) {
//        // The radius of the 2D annulus decreases with dz.
//        int r = rShell - dz;
//
//        // A 2D annulus has circumfrence 4r.
//        int circumfrence = 4*r;
//
//        // The middle slice is counted once; all others have a top and bottom.
//        if (dz == 0) {
//            return circumfrence;
//        } else {
//            return 2 * circumfrence;
//        }
//    }

    /**
     * Populate an array, starting at some offset, with the slice of a 3D
     * shell at +dz and -dz.
     *
     * @param origin The center coordinate of the annulus, in 3D space.
     * @param shell  The array to populate.
     * @param r      The radius of the 3D shell.
     * @param dz     The absolute value offset from the z coordinate of the center.
     * @param offset The first index to populate.
     */
    private int populateSlices(Coordinate origin, Coordinate[] shell, int r, int dz, int offset) {

        // Middle slice: only one of them.
        if (dz == 0) {
            return populateSlice(origin, shell, r, 0, offset);
        }

        // Otherwise, there are two: top and bottom.
        offset = populateSlice(origin, shell, r, dz, offset);
        return populateSlice(origin, shell, r, -dz, offset);
    }

    /**
     * Populate a slice, at height dz, of a 3D shell of radius rShell centered at
     * origin, inside of array shell, starting at array position offset.
     *
     * @param origin The center The center coordinate of the annulus, in 3D space.
     * @param shell  The array to populate.
     * @param rShell The radius of the shell.
     * @param dz     The z-offset of the slice.
     * @param offset The index offset at which to start writing to the array.
     */
    private int populateSlice(Coordinate origin, Coordinate[] shell, int rShell, int dz, int offset) {
        // De-index coordinate.
        int x0 = origin.x();
        int y0 = origin.y();
        int z = origin.z() + dz;

        // The middle slice (dz=0) has the radius of the shell. The top and bottom
        // slices (dz = +/- rShell) have a radius 0.
        int r = rShell - Math.abs(dz);

        // r=0 case (a point)
        if (r == 0) {
            shell[offset] = new Coordinate(x0, y0, z, 0);
            return offset + 1;
        }

        for (int i = 0; i < r; i++) {
            int j = r - i;

            int base = 4 * i;

            shell[offset + base + 0] = new Coordinate(x0 + i, y0 + j, z, 0);
            shell[offset + base + 1] = new Coordinate(x0 + j, y0 - i, z, 0);
            shell[offset + base + 2] = new Coordinate(x0 - i, y0 - j, z, 0);
            shell[offset + base + 3] = new Coordinate(x0 - j, y0 + i, z, 0);
        }

        return offset + 4 * r;
    }

    @Override
    public Coordinate getDisplacement(Coordinate pCoord, Coordinate qCoord) {
        if (pCoord.hasFlag(Flags.PLANAR) || qCoord.hasFlag(Flags.PLANAR)) {
            throw new IllegalArgumentException("Expect 3D coordinates in cubic lattice.");
        }

        int dx = qCoord.x() - pCoord.x();
        int dy = qCoord.y() - pCoord.y();
        int dz = qCoord.z() - pCoord.z();

        return new Coordinate(dx, dy, dz, Flags.VECTOR);
    }

    @Override
    public Coordinate rel2abs(Coordinate coord, Coordinate displacement) {
        if (displacement.hasFlag(Flags.PLANAR)) {
            throw new IllegalArgumentException("Expected three arguments to cubic lattice rel2abs.");
        }

        int x = coord.x();
        int y = coord.y();
        int z = coord.z();

        // Apply x component
        x += displacement.x();

        // Apply y component
        y += displacement.y();

        // Apply z component
        z += displacement.z();

        Coordinate target = new Coordinate(x, y, z, 0);

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
        return new CubicLattice();
    }

    @Override
    public Coordinate getZeroVector() {
        return new Coordinate(0, 0, 0, 0);
    }
}
