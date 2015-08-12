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
import control.identifiers.Coordinate2D;
import control.identifiers.Coordinate3D;
import control.identifiers.Flags;

import java.util.ArrayList;

public class TriangularLattice extends Lattice {

    protected void defineBasis() {
        Coordinate southeast = new Coordinate2D(1, 0, 0);
        Coordinate northeast = new Coordinate2D(1, 1, 0);
        Coordinate north = new Coordinate2D(0, 1, 0);

        basis = new Coordinate[]{southeast, northeast, north};
    }

    @Override
    public int getConnectivity() {
        return 3;
    }

    @Override
    public int getDimensionality() {
        return 2;
    }

    @Override
    public Coordinate adjust(Coordinate i) {
        if (!i.hasFlag(Flags.PLANAR)) {
            throw new IllegalArgumentException("Triangular lattice is a planar geometry.");
        }

        int yAdj;
        int x = i.x();
        if (x >= 0) {
            yAdj = (x / 2);
        } else {
            yAdj = ((x - 1) / 2);
        }

        Coordinate o = new Coordinate2D(i.x(), i.y() + yAdj, i.flags());

        return o;
    }

    @Override
    public Coordinate invAdjust(Coordinate i) {
        if (!i.hasFlag(Flags.PLANAR)) {
            throw new IllegalArgumentException("Triangular lattice is a planar geometry.");
        }

        int yAdj;
        int x = i.x();
        if (x >= 0) {
            yAdj = (x / 2);
        } else {
            yAdj = ((x - 1) / 2);
        }
        Coordinate o = new Coordinate2D(i.x(), i.y() - yAdj, i.flags());

        return o;
    }

    @Override
    public Coordinate[] getAnnulus(Coordinate coord, int r) {

        // De-index coordinate.
        int x0 = coord.x();
        int y0 = coord.y();

        int n = getAnnulusSize(r);

        // r=0 case (a point)
        if (r == 0) {
            return new Coordinate[]{new Coordinate2D(x0, y0, 0)};
        }

        // All other cases
        ArrayList<Coordinate> ring = new ArrayList<Coordinate>(n);

        for (int k = 1; k <= r; k++) {
            // Right side
            Coordinate candidate = new Coordinate2D(x0 + r, y0 + k - 1, 0);
            ring.add(candidate);

            // Lower right side
            candidate = new Coordinate2D(x0 + r - k, y0 - k, 0);
            ring.add(candidate);

            // Lower left side
            candidate = new Coordinate2D(x0 - k, y0 - r, 0);
            ring.add(candidate);

            // Left side
            candidate = new Coordinate2D(x0 - r, y0 - r + k, 0);
            ring.add(candidate);

            // Upper left side
            candidate = new Coordinate2D(x0 - r + k, y0 + k, 0);
            ring.add(candidate);

            // Upper right side
            candidate = new Coordinate2D(x0 + k, y0 + r, 0);
            ring.add(candidate);
        }

        return (ring.toArray(new Coordinate[0]));

    }

    /* Get (naive) size of an annulus of the specified L1 radius from a
     * point, i.e., size assuming no out-of-bounds points.
     */
    private int getAnnulusSize(int radius) {
        if (radius == 0) {
            return (1);
        }

        return (6 * radius);
    }

    @Override
    public Coordinate getDisplacement(Coordinate p, Coordinate q) {
        // Extract p coordinate
        int xp = p.x();
        int yp = p.y();

        // Extract q coordinate
        int xq = q.x();
        int yq = q.y();

        int dx = xq - xp;
        int dy = yq - yp;

        // A triangular lattice is formed by a non-orthogonal basis:
        //    u = [1, 0] --> southeast
        //    v = [1, 1] --> northeast
        //    w = [0, 1] --> north

        // Step 1: get v component.
        int dv = 0;
        if (dx < 0 && dy < 0) {
            dv = Math.max(dx, dy);
        }

        if (dx > 0 && dy > 0) {
            dv = Math.min(dx, dy);
        }

        // Step 2: subtracting du from dx and dy, we get dw and du respectively.
        int du = dx - dv;
        int dw = dy - dv;

        // Populate the vector in the new basis -- done!
        return new Coordinate3D(du, dv, dw, Flags.VECTOR);
    }

    @Override
    public Coordinate getOrthoDisplacement(Coordinate p, Coordinate q) {
        // The 'u' and 'w' components are linearly independent. (Not
        // exactly 'orthogonal,' but oh well.)

        // Extract p coordinate
        int xp = p.x();
        int yp = p.y();

        // Extract q coordinate
        int xq = q.x();
        int yq = q.y();

        int du = xq - xp;
        int dv = 0;
        int dw = yq - yp;

        return new Coordinate3D(du, dv, dw, Flags.VECTOR);
    }


    @Override
    public Coordinate rel2abs(Coordinate coord, Coordinate displacement) {
        if (displacement.hasFlag(Flags.PLANAR)) {
            throw new IllegalArgumentException("Expected three-component coordinate.");
        }

        int x = coord.x();
        int y = coord.y();

        // Apply u component
        x += displacement.x();

        // Apply v component
        x += displacement.y();
        y += displacement.y();

        // Apply w component
        y += displacement.z();

        Coordinate target = new Coordinate2D(x, y, 0);

        return target;
    }

    @Override
    public Lattice clone() {
        return new TriangularLattice();
    }

    @Override
    public Coordinate getZeroVector() {
        return new Coordinate2D(0, 0, 0);
    }
}
