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
import nanoverse.runtime.geometry.lattice.Lattice;
import nanoverse.runtime.structural.annotations.FactoryTarget;

public class Line extends Shape {

    private int length;

    @FactoryTarget
    public Line(Lattice lattice, int length) {
        super(lattice);
        this.length = length;

        init();
    }

    @Override
    protected void verify(Lattice lattice) {
        if (lattice.getDimensionality() != 1) {
            throw new IllegalArgumentException("Line shape requires a linear" +
                " lattice connectivity.");
        }
    }

    @Override
    protected Coordinate[] calcSites() {
        Coordinate[] sites = new Coordinate[length];

        for (int y = 0; y < length; y++) {
            sites[y] = new Coordinate1D(y, 0);
        }

        return sites;
    }

    @Override
    public Coordinate getCenter() {
        // 0-indexed coordinates
        int y = (length - 1) / 2;

        Coordinate center = new Coordinate1D(y, 0);
//
//        Coordinate adjusted = lattice.adjust(center);

        return center;
    }

    @Override
    public Coordinate[] getBoundaries() {

        return new Coordinate[]{
            new Coordinate1D(0, 0),
            new Coordinate1D(length - 1, 0)
        };
    }

    @Override
    public Coordinate getOverbounds(Coordinate coord) {
        Coordinate origin = new Coordinate1D(0, 0);
        Coordinate d = lattice.getDisplacement(origin, coord);

        int ob;
        if (d.y() >= length) {
            ob = d.y() - length + 1;
        } else if (d.y() < 0) {
            ob = d.y();
        } else {
            ob = 0;
        }

        return new Coordinate1D(ob, Flags.VECTOR);
    }

    @Override
    public int getDistanceOverBoundary(Coordinate coord) {
        Coordinate ob = getOverbounds(coord);
        return ob.norm();
    }

    @Override
    public int[] getDimensions() {
        return new int[]{length};
    }

    @Override
    public boolean equals(Object obj) {
        // Is it a Line?
        if (!(obj instanceof Line)) {
            return false;
        }

        Line other = (Line) obj;

        if (other.length != this.length) {
            return false;
        }

        // If these things are OK, return true
        return true;
    }

    @Override
    public Shape cloneAtScale(Lattice clonedLattice, double rangeScale) {
        int scaledLength;
        scaledLength = (int) Math.round(length * rangeScale);
        return new Line(clonedLattice, scaledLength);
    }
}
