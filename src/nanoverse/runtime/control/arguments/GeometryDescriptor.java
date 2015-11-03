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

package nanoverse.runtime.control.arguments;

import nanoverse.runtime.geometry.Geometry;
import nanoverse.runtime.geometry.boundaries.Boundary;
import nanoverse.runtime.geometry.lattice.Lattice;
import nanoverse.runtime.geometry.shape.Shape;
import nanoverse.runtime.structural.annotations.FactoryTarget;

/**
 * Created by dbborens on 11/24/14.
 */
public class GeometryDescriptor {

    private Lattice lattice;
    private Shape shape;

    @FactoryTarget
    public GeometryDescriptor(Lattice lattice, Shape shape) {
        this.shape = shape;
        this.lattice = lattice;
    }

    @Override
    public int hashCode() {
        int result = lattice.hashCode();
        result = 31 * result + shape.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GeometryDescriptor that = (GeometryDescriptor) o;

        if (!lattice.equals(that.lattice)) return false;
        if (!shape.equals(that.shape)) return false;

        return true;
    }

    public Geometry make(Boundary boundary) {
        return new Geometry(lattice, shape, boundary);
    }

    public Lattice getLattice() {
        return lattice;
    }

    public Shape getShape() {
        return shape;
    }
}
