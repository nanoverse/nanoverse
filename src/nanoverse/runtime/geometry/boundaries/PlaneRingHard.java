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

package nanoverse.runtime.geometry.boundaries;

import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.geometry.boundaries.helpers.PlaneRingHelper;
import nanoverse.runtime.geometry.lattice.Lattice;
import nanoverse.runtime.geometry.shape.*;
import nanoverse.runtime.structural.annotations.FactoryTarget;

/**
 * One axis is a hard boundary, and the other is periodic.
 *
 * @author David Bruce Borenstein
 * @untested
 */
public class PlaneRingHard extends Boundary {

    private PlaneRingHelper helper;

    @FactoryTarget
    public PlaneRingHard(Shape shape, Lattice lattice) {
        super(shape, lattice);
        helper = new PlaneRingHelper(lattice, shape.getDimensions());
    }

    @Override
    protected void verify(Shape shape, Lattice lattice) {
        // PlaneRing is compatible only with Rectangle shapes.
        if (!(shape instanceof Rectangle)) {
            throw new IllegalArgumentException("PlaneRingHard boundary requires a Rectangle shape.");
        }
    }

    @Override
    public Coordinate apply(Coordinate c) {
        Coordinate ob = shape.getOverbounds(c);

        // If overbounds in Y direction, return undefined.
        if (ob.y() != 0) {
            return null;

            // If overbounds in X direction, return wrapped. Make sure to
            // adjust.
        } else if (ob.x() != 0) {
            return helper.wrap(c);

            // Otherwise, don't do anything.
        } else {
            return c;
        }
    }

    @Override
    public boolean isInfinite() {
        return false;
    }

    @Override
    public Boundary clone(Shape scaledShape, Lattice clonedLattice) {
        return new PlaneRingHard(scaledShape, clonedLattice);
    }
}
