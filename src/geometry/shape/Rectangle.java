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

package geometry.shape;

import control.identifiers.Coordinate;
import control.identifiers.Coordinate2D;
import control.identifiers.Flags;
import geometry.lattice.Lattice;
import org.dom4j.Element;
import structural.annotations.FactoryTarget;

import java.util.ArrayList;

public class Rectangle extends Shape {

    private int height, width;

    @FactoryTarget
    public Rectangle(Lattice lattice, int width, int height) {
        super(lattice);

        this.height = height;
        this.width = width;

        init();
    }

    public Rectangle(Lattice lattice, Element descriptor) {
        super(lattice);

        height = Integer.valueOf(descriptor.element("height").getTextTrim());
        width = Integer.valueOf(descriptor.element("width").getTextTrim());

        init();
    }

    @Override
    protected void verify(Lattice lattice) {
        if (lattice.getDimensionality() != 2) {
            throw new IllegalArgumentException("Rectangular lattice shape requires a planar lattice connectivity.");
        }
    }

    @Override
    public Coordinate getCenter() {
        // 0-indexed coordinates
        int x = (width - 1) / 2;
        int y = (height - 1) / 2;

        Coordinate center = new Coordinate2D(x, y, 0);

        Coordinate adjusted = lattice.adjust(center);

        return adjusted;
    }

    @Override
    public Coordinate[] getBoundaries() {

        // This is off by a couple, but it will get shortened
        ArrayList<Coordinate> coords = new ArrayList<Coordinate>(2 * (width + height));

        // North and south
        for (int x = 0; x < width; x++) {

            include(coords, new Coordinate2D(x, height - 1, 0));
            include(coords, new Coordinate2D(x, 0, 0));
        }

        // East and west
        for (int y = 1; y < height - 1; y++) {
            include(coords, new Coordinate2D(width - 1, y, 0));
            include(coords, new Coordinate2D(0, y, 0));
        }

        return coords.toArray(new Coordinate[0]);
    }

    @Override
    protected Coordinate[] calcSites() {
        ArrayList<Coordinate> coords = new ArrayList<Coordinate>(width * height);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                include(coords, new Coordinate2D(x, y, 0));
            }
        }

        return coords.toArray(new Coordinate[0]);
    }

    @Override
    public Coordinate getOverbounds(Coordinate coord) {
        // Get orthogonal distance from (0, 0) to this point.
        Coordinate origin = new Coordinate2D(0, 0, 0);
        Coordinate d = lattice.getOrthoDisplacement(origin, coord);


        // The following are both cloodge and I hate them.

        // Get rid of unnecessary dimension, when applicable
        d = dimensionReduce(d);

        // Get rid of coordinate adjustment, when applicable
        d = lattice.invAdjust(d);
        int dx, dy;


        // Handle x coordinate overbounds
        if (d.x() < 0) {
            dx = d.x();
        } else if (d.x() >= width) {
            dx = d.x() - width + 1;
        } else {
            // If within bounds, overbounds amount is zero.
            dx = 0;
        }

        // Handle y coordinate overbounds
        if (d.y() < 0) {
            dy = d.y();
        } else if (d.y() >= height) {
            dy = d.y() - height + 1;
        } else {
            // If within bounds, overbounds amount is zero.
            dy = 0;
        }

        return new Coordinate2D(dx, dy, Flags.VECTOR);
    }

    /**
     * The triangular lattice has a degenerate, three-coordinate
     * "basis." When getOrthoDisplacement is called, it returns a
     * 3D displacement vector with a zero middle coordinate. This
     * can be converted to a 2D displacement vector by eliding
     * this middle zero.
     * <p>
     * This is admittedly cloodgy and hard to understand. My apologies.
     */
    private Coordinate dimensionReduce(Coordinate d) {
        if (d.hasFlag(Flags.PLANAR)) {
            return d;
        }

        int u = d.x();
        int v = d.z();
        int f = d.flags();

        return new Coordinate2D(u, v, f);
    }

    @Override
    public int[] getDimensions() {
        return new int[]{width, height};
    }

    @Override
    public boolean equals(Object obj) {
        // Is it a Rectangle?
        if (!(obj instanceof Rectangle)) {
            return false;
        }

        Rectangle other = (Rectangle) obj;

        // Does it have the same dimensions?
        if (other.width != this.width) {
            return false;
        }

        if (other.height != this.height) {
            return false;
        }

        // If these things are OK, return true
        return true;
    }

    @Override
    public Shape cloneAtScale(Lattice clonedLattice, double rangeScale) {
        int scaledWidth, scaledHeight;
        scaledWidth = (int) Math.round(width * rangeScale);
        scaledHeight = (int) Math.round(height * rangeScale);
        return new Rectangle(clonedLattice, scaledHeight, scaledWidth);
    }
}
