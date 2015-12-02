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

package nanoverse.runtime.geometry.boundaries.helpers;

import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.control.identifiers.Coordinate2D;
import nanoverse.runtime.control.identifiers.Flags;
import nanoverse.runtime.geometry.basis.BasisHelper2D;
import nanoverse.runtime.geometry.lattice.Lattice;
import nanoverse.runtime.geometry.shape.Rectangle;
import nanoverse.runtime.geometry.shape.Shape;

/**
 * Created by dbborens on 12/2/2015.
 */
public class ReflectHelper2D {
    private final int width, height;
    private final BasisHelper2D basisHelper;
    private final Rectangle shape;
    private final Lattice lattice;

    public ReflectHelper2D(Shape shape, Lattice lattice) {
        this.lattice = lattice;
        if (shape.getClass() != Rectangle.class) {
            throw new IllegalArgumentException("WrapHelper2D only supports Rectangle arenas.");
        }
        this.shape = (Rectangle) shape;
        Rectangle rect = (Rectangle) shape;

        width = rect.getDimensions()[0];
        height = rect.getDimensions()[1];

        basisHelper = rect.getBasisHelper();
    }

    public Coordinate yReflect(Coordinate toReflect) {
        Coordinate ua = basisHelper.invAdjust(toReflect);
        Coordinate refl = doReflection(ua);
        Coordinate adj = basisHelper.adjust(refl);
        return adj;
    }

    private Coordinate doReflection(Coordinate c) {
        int x = c.x();
        int y = c.y();
        int flags = c.flags() | Flags.BOUNDARY_APPLIED;

        // Coordinate is above: reflect down.
        if (y >= height) {
            int y1 = (2 * height) - y - 1;
            return doReflection(new Coordinate2D(x, y1, flags));
        }

        // Coordinate is below: reflect up.
        if (y < 0) {
            int y1 = -1 * (y + 1);
            return doReflection(new Coordinate2D(x, y1, flags));
        }

        // Base case: coordinate is not above or below, so return it.
        return c;
    }
}
