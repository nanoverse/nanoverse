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

package geometry.boundaries;

import control.identifiers.Coordinate;
import geometry.boundaries.helpers.PlaneRingHelper;
import geometry.lattice.Lattice;
import geometry.shape.Rectangle;
import geometry.shape.Shape;
import structural.annotations.FactoryTarget;

public class PlaneRingReflecting extends Boundary {

    private PlaneRingHelper helper;

    @FactoryTarget
    public PlaneRingReflecting(Shape shape, Lattice lattice) {
        super(shape, lattice);
        helper = new PlaneRingHelper(lattice, shape.getDimensions());
    }

    @Override
    public Coordinate apply(Coordinate c) {
        Coordinate ob = shape.getOverbounds(c);

        // First, fix x if needed.
        Coordinate wrapped, reflected;

        if (ob.x() != 0) {
            wrapped = helper.wrap(c);
        } else {
            wrapped = c;
        }

        // Next, fix y if needed.
        if (ob.y() != 0) {
            reflected = helper.reflect(wrapped);
        } else {
            reflected = wrapped;
        }

        return reflected;
    }

    @Override
    public boolean isInfinite() {
        return false;
    }

    @Override
    protected void verify(Shape shape, Lattice lattice) {
        // PlaneRing is compatible only with Rectangle shapes.
        if (!(shape instanceof Rectangle)) {
            throw new IllegalArgumentException("PlaneRingReflecting boundary requires a Rectangle shape.");
        }
    }

    @Override
    public Boundary clone(Shape scaledShape, Lattice clonedLattice) {
        return new PlaneRingReflecting(scaledShape, clonedLattice);
    }
}
