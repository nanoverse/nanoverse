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

import nanoverse.runtime.control.identifiers.*;
import nanoverse.runtime.geometry.basis.BasisHelper2D;
import nanoverse.runtime.geometry.lattice.Lattice;

/**
 * Wraps coordinates in the X direction, honoring any features of the
 * lattice.
 * <p>
 *
 * @author dbborens
 */
public class PlaneRingHelper {

    private final int width;
    private final int height;
    private final BasisHelper2D basisHelper;

    public PlaneRingHelper(BasisHelper2D basisHelper, int[] dims) {
        this.basisHelper = basisHelper;
        this.width = dims[0];
        this.height = dims[1];
    }

    /**
     * Wrap a coordinate in the x direction. This is really only public
     * for testing purposes.
     *
     * @param c
     * @return
     */
    public Coordinate wrap(Coordinate c) {
        // Calculate old x and y adjustment.
        Coordinate invAdj = basisHelper.invAdjust(c);

        // Calculate adjustment delta.
        int dx = c.x() - invAdj.x();
        int dy = c.y() - invAdj.y();
        Coordinate delta = new Coordinate2D(dx, dy, Flags.VECTOR);

        // Subtract old adjustments from coordinate.
        int x, y;

        x = c.x() - delta.x();
        y = c.y() - delta.y();

        Coordinate ua = new Coordinate2D(x, y, c.flags());

        // Wrap.
        int xw = ua.x();
        while (xw >= width) {
            xw -= width;
        }

        while (xw < 0) {
            xw += width;
        }

        // Apply adjustment again.
        Coordinate wrapped = new Coordinate2D(xw, y, c.flags());

        Coordinate adjusted = basisHelper.adjust(wrapped);

        // Calculate new adjustment deltas.
        return adjusted.addFlags(Flags.BOUNDARY_APPLIED);
    }

    public Coordinate reflect(Coordinate c) {
        Coordinate ua = basisHelper.invAdjust(c);
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
