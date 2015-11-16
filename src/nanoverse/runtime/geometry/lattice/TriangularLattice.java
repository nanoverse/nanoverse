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

import java.util.ArrayList;

public class TriangularLattice extends Lattice {

    @FactoryTarget
    public TriangularLattice() {
        super();
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
        ArrayList<Coordinate> ring = new ArrayList<>(n);

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
    public Coordinate getOrthoDisplacement(Coordinate p, Coordinate q) {
        return getDisplacement(p, q);
    }

    @Override
    public Coordinate rel2abs(Coordinate coord, Coordinate displacement) {
        if (!displacement.hasFlag(Flags.PLANAR)) {
            throw new IllegalArgumentException("Expected 2D coordinate.");
        }

        int x = coord.x();
        int y = coord.y();

        x += displacement.x();
        y += displacement.y();

        Coordinate target = new Coordinate2D(x, y, 0);

        return target;
    }

    @Override
    public Coordinate getDisplacement(Coordinate p, Coordinate q) {
        int xp = p.x();
        int yp = p.y();

        int xq = q.x();
        int yq = q.y();

        int dx = xq - xp;
        int dy = yq - yp;

        return new Coordinate2D(dx, dy, Flags.VECTOR);
    }

    @Override
    public int getNeighborhoodDistance(Coordinate p, Coordinate q) {
        Coordinate d = getDisplacement(p, q);
        int aX = Math.abs(d.x());
        int aY = Math.abs(d.y());

        if (signum(d.x()) == signum(d.y())) {
            return aX + aY - Math.min(aX, aY);
        } else {
            return aX + aY;
        }
    }

    public int signum(int x) {
        if (x == 0) {
            return 0;
        }
        return Math.abs(x) / x;
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
