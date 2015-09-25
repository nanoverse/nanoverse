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

package nanoverse.runtime.geometry.shape;

import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.control.identifiers.Coordinate1D;
import nanoverse.runtime.control.identifiers.Flags;
import nanoverse.runtime.geometry.lattice.Lattice;
import org.dom4j.Element;
import nanoverse.runtime.structural.annotations.FactoryTarget;

public class Line extends Shape {

    private int length;

    @FactoryTarget
    public Line(Lattice lattice, int length) {
        super(lattice);
        this.length = length;

        init();
    }

    public Line(Lattice lattice, Element descriptor) {
        super(lattice);

        if (descriptor.element("length") == null) {
            throw new IllegalArgumentException("Length not specified for line. Why are you even using this constructor?");
        }
        length = Integer.valueOf(descriptor.element("length").getTextTrim());

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
    public Coordinate getCenter() {
        // 0-indexed coordinates
        int y = (length - 1) / 2;

        Coordinate center = new Coordinate1D(y, 0);

        Coordinate adjusted = lattice.adjust(center);

        return adjusted;
    }

    @Override
    public Coordinate[] getBoundaries() {

        return new Coordinate[]{
                new Coordinate1D(0, 0),
                new Coordinate1D(length - 1, 0)
        };
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
    public Coordinate getOverbounds(Coordinate coord) {
        // Get orthogonal distance from (0, 0) to this point.
        Coordinate origin = new Coordinate1D(0, 0);
        Coordinate d = lattice.getOrthoDisplacement(origin, coord);

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
        int scaledWidth, scaledLength;
        scaledLength = (int) Math.round(length * rangeScale);
        return new Line(clonedLattice, scaledLength);
    }
}
