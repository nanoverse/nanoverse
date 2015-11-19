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

package nanoverse.runtime.geometry.shape;

import nanoverse.runtime.control.identifiers.*;
import nanoverse.runtime.geometry.basis.*;
import nanoverse.runtime.geometry.lattice.*;
import nanoverse.runtime.structural.NotYetImplementedException;
import nanoverse.runtime.structural.annotations.FactoryTarget;

import java.util.*;

import static java.lang.Math.abs;

/**
 * A hexagon-shaped arena. This is a strange nanoverse.runtime.geometry,
 * in that it has a number of sides equal to its connectivity,
 * rather than its dimension, and in that its coordinate
 * system is best thought of in terms of the non-orthogonal
 * basis vectors of the triangular lattice. For this reason,
 * it overrides many functions.
 *
 * @author dbborens
 */
public class Hexagon extends Shape {

    private final int radius;

    @FactoryTarget
    public Hexagon(Lattice lattice, int radius) {
        super(lattice);
        this.radius = radius;
        init();
    }

    @Override
    protected void verify(Lattice lattice) {
        if (!(lattice instanceof TriangularLattice)) {
            throw new IllegalArgumentException("Hexagonal nanoverse.runtime.geometry shape requires a triangular lattice.");
        }
    }

    @Override
    protected Coordinate[] calcSites() {
        ArrayList<Coordinate> coords = new ArrayList<>();
        for (int r = 0; r <= radius; r++) {
            ring(coords, r);
        }

        return coords.toArray(new Coordinate[0]);
    }

    @Override
    public Coordinate getCenter() {
        // This nanoverse.runtime.geometry is defined around a center coordinate of (r, r).
        // It is built up as a ring around this coordinate.
        Coordinate center = new Coordinate2D(radius, radius, 0);
        return center;
    }

    @Override
    public Coordinate[] getBoundaries() {
        ArrayList<Coordinate> coords = new ArrayList<>();
        ring(coords, radius);

        return coords.toArray(new Coordinate[0]);
    }

    @Override
    /**
     * Get extent overbound of the specified coordinate, if any.
     *
     * The getOverbounds method for Hexagon is degenerate in that there are
     * ambiguous cases. For example, consider the case of (0, 4) with an r=2
     * hexagon. This could be considered +2w from the corner coordinate (0, 2)
     * or -2u from the corner coordinate (2, 4). In this case, we favor the use
     * of the "true" basis vector <u, w> and therefore prefer the latter.
     *
     */
    public Coordinate getOverbounds(Coordinate target) {
        throw new NotYetImplementedException();
    }

    @Override
    public int getDistanceOverBoundary(Coordinate target) {
        Coordinate origin = getCenter();
        int r = lattice.getNeighborhoodDistance(origin, target);

        if (r < radius) {
            return 0;
        }

        return r - radius;
    }

    @Override
    public int[] getDimensions() {
        int d = (2 * radius) + 1;

        return new int[]{d, d, d};
    }

    @Override
    public boolean equals(Object obj) {
        // Is it a Hexagon?
        if (!(obj instanceof Hexagon)) {
            return false;
        }

        // Does it have the same dimensions?
        Hexagon other = (Hexagon) obj;
        if (other.radius != this.radius) {
            return false;
        }

        // If these things are true, then the objects
        // are equal.
        return true;
    }

    @Override
    public Shape cloneAtScale(Lattice clonedLattice, double rangeScale) {
        int scaledRadius;
        scaledRadius = (int) Math.round(radius * rangeScale);
        return new Hexagon(clonedLattice, scaledRadius);
    }

    private int mnz(int[] dArr) {
        int mnz = Integer.MAX_VALUE;

        for (int x : dArr) {
            if (x == 0) {
                continue;
            } else if (abs(x) < mnz) {
                mnz = abs(x);
            }
        }

        return mnz;
    }

    /**
     * We call Math.abs(int) so many times here in algorithms
     * that it is easier to give a shorthand. Cloodgy but easy
     * to read (= Laziness.)
     */
    private int a(int i) {
        return abs(i);
    }

    /**
     * Returns L1 norm.
     */
    private int norm(int[] arr) {
        int sum = 0;

        for (int elem : arr) {
            sum += abs(elem);
        }

        return sum;
    }

    private int getOverage(int u) {
        int du;
        if (u > radius) {
            du = u - radius;
        } else if (u < radius * -1) {
            du = u + radius;
        } else {
            du = 0;
        }

        return du;
    }

    private void ring(ArrayList<Coordinate> coords, int r) {
        if (r == 0) {
            include(coords, getCenter());
            return;
        }

        int x0 = getCenter().x();
        int y0 = getCenter().y();
        for (int k = 1; k <= r; k++) {
            // Right side
            include(coords, new Coordinate2D(x0 + r, y0 + k - 1, 0));

            // Lower right side
            include(coords, new Coordinate2D(x0 + r - k, y0 - k, 0));

            // Lower left side
            include(coords, new Coordinate2D(x0 - k, y0 - r, 0));

            // Left side
            include(coords, new Coordinate2D(x0 - r, y0 - r + k, 0));

            // Upper left side
            include(coords, new Coordinate2D(x0 - r + k, y0 + k, 0));

            // Upper right side
            include(coords, new Coordinate2D(x0 + k, y0 + r, 0));
        }
    }

    private void include(ArrayList<Coordinate> list, Coordinate c) {
        list.add(c);
    }

}
