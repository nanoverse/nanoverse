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
import geometry.lattice.Lattice;
import geometry.shape.Shape;
import structural.annotations.FactoryTarget;

/**
 * A boundary condition that returns null for any overbound
 * position. Since Geometry removes null values, the result
 * is that cells adjacent to the boundary have fewer neighbors
 * than cells at the interior.
 * <p>
 * Created by David B Borenstein on 12/22/13.
 */
public class Absorbing extends Boundary {

    @FactoryTarget
    public Absorbing(Shape shape, Lattice lattice) {
        super(shape, lattice);
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
    protected void verify(Shape shape, Lattice lattice) {
        // Absorbing is compatible with all lattice geometries and
        // shapes, so no verification is needed.
    }

    @Override
    public Boundary clone(Shape scaledShape, Lattice clonedLattice) {
        return new Absorbing(scaledShape, clonedLattice);
    }
}
