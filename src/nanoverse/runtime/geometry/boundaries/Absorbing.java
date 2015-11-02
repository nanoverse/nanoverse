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

package nanoverse.runtime.geometry.boundaries;

import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.geometry.lattice.Lattice;
import nanoverse.runtime.geometry.shape.Shape;
import nanoverse.runtime.structural.annotations.FactoryTarget;

/**
 * A boundary condition that returns null for any overbound
 * position. Since Geometry removes null values, the result
 * is that agents adjacent to the boundary have fewer neighbors
 * than agents at the interior.
 * <p>
 * Created by David B Borenstein on 12/22/13.
 */
public class Absorbing extends Boundary {

    @FactoryTarget
    public Absorbing(Shape shape, Lattice lattice) {
        super(shape, lattice);
    }

    @Override
    protected void verify(Shape shape, Lattice lattice) {
        // Absorbing is compatible with all lattice geometries and
        // shapes, so no verification is needed.
    }

    @Override
    public Coordinate apply(Coordinate c) {
        Coordinate ob = shape.getOverbounds(c);

        Coordinate ret;
        // Is any component overbound?
        if (ob.x() != 0 || ob.y() != 0 || ob.z() != 0) {
            // If yes return null
            ret = null;

        } else {
            // Otherwise, just return the coordinate.
            ret = c;
        }

        return ret;
    }

    @Override
    public boolean isInfinite() {
        return false;
    }

    @Override
    public Boundary clone(Shape scaledShape, Lattice clonedLattice) {
        return new Absorbing(scaledShape, clonedLattice);
    }
}
