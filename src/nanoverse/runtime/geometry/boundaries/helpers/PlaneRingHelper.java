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

package nanoverse.runtime.geometry.boundaries.helpers;

import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.control.identifiers.Coordinate2D;
import nanoverse.runtime.control.identifiers.Flags;
import nanoverse.runtime.geometry.lattice.Lattice;

/**
 * Wraps coordinates in the X direction, honoring any features of the
 * lattice.
 * <p>
 * TODO: Generalize this cloodgy class to make it more reusable.
 *
 * @author dbborens
 */
public class PlaneRingHelper {

    private Lattice lattice;
    private int width;
    private int height;

    public PlaneRingHelper(Lattice lattice, int[] dims) {
        this.lattice = lattice;
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
        // We have to do a lot of work because the coordinate could be
        // in any basis. That might not be true; I'm having a bad day
        // and it seems like it's true right now.

        // TODO: Use the basis vectors to get rid of the adjust(...)
        // method and greatly shorten this code.

        //System.out.println("Trying to wrap " + c + ".");

        //System.out.println("   1. Reversing adjustments.");
        // Calculate old x and y adjustment.
        Coordinate invAdj = lattice.invAdjust(c);
        //System.out.println("      Inverse adjustment: " + invAdj);

        // Calculate adjustment delta.
        int dx = c.x() - invAdj.x();
        int dy = c.y() - invAdj.y();
        Coordinate delta = new Coordinate2D(dx, dy, Flags.VECTOR);

        //System.out.println("      Adjustment delta: " + delta);

        // Subtract old adjustments from coordinate.
        int x, y;

        x = c.x() - delta.x();
        y = c.y() - delta.y();

        Coordinate ua = new Coordinate2D(x, y, c.flags());
        //System.out.println("      Coordinate without adjustment: " + ua);

        //System.out.println("   2. Wrapping.");
        // Wrap.
        int xw = ua.x();
        while (xw >= width) {
            xw -= width;
        }

        while (xw < 0) {
            xw += width;
        }

        ////System.out.println("      Adjusted x to " + xw);

        // Apply adjustment again.
        Coordinate wrapped = new Coordinate2D(xw, y, c.flags());
        //System.out.println("   3. Wrapped coordinate: " + wrapped);

        Coordinate adjusted = lattice.adjust(wrapped);
        //System.out.println("   4. Adjusted, wrapped coordinate: " + adjusted);

        // Calculate new adjustment deltas.
        return adjusted.addFlags(Flags.BOUNDARY_APPLIED);
    }

    public Coordinate reflect(Coordinate c) {
        Coordinate ua = lattice.invAdjust(c);
        Coordinate refl = doReflection(ua);
        Coordinate adj = lattice.adjust(refl);
        return adj;
    }

    private Coordinate doReflection(Coordinate c) {
        //System.out.print(c);
        int x = c.x();
        int y = c.y();
        int flags = c.flags() | Flags.BOUNDARY_APPLIED;

        // Coordinate is above: reflect down.
        if (y >= height) {
            ////System.out.println(" A");
            int y1 = (2 * height) - y - 1;
            return doReflection(new Coordinate2D(x, y1, flags));
        }

        // Coordinate is below: reflect up.
        if (y < 0) {
            ////System.out.println(" B");
            int y1 = -1 * (y + 1);
            return doReflection(new Coordinate2D(x, y1, flags));
        }

        // Base case: coordinate is not above or below, so return it.
        ////System.out.println(" C");
        return c;
    }
}
