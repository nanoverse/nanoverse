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
import control.identifiers.Flags;
import geometry.lattice.Lattice;
import geometry.lattice.RectangularLattice;
import geometry.shape.Rectangle;
import geometry.shape.Shape;

/**
 * Boundary condition that is periodic in X, reflecting on
 * the lower Y boundary, and absorbing on the upper Y boundary.
 * (Dirichlet conditions can be set on the upper Y boundary
 * using the DirichletEnforcerProcess.
 *
 * This is a special case, so it only works for rectangular
 * arenas with a rectangular coordinate system.
 *
 * @author Daniel Greenidge
 */
public class TetrisReflectingBoundary extends Boundary {
    private final int WIDTH;
    private final int HEIGHT;

    public TetrisReflectingBoundary(Shape shape, Lattice lattice) {
        super(shape, lattice);
        this.WIDTH = shape.getDimensions()[0];
        this.HEIGHT = shape.getDimensions()[1];
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void verify(Shape shape, Lattice lattice) {
        if (!(shape instanceof Rectangle)) {
            throw new IllegalArgumentException("TetrisReflectingBoundary is" +
                    " only defined on rectangular arenas.");
        }

        if (!(lattice instanceof RectangularLattice)) {
            throw new IllegalArgumentException("TetrisReflectingBoundary is" +
                    " only defined on rectangular lattices");
        }

        if (lattice.getDimensionality() != 2) {
            throw new IllegalArgumentException("TetrisReflectingBoundary is" +
                    " only defined in two dimensions");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Coordinate apply(Coordinate c) {
        return applyY(applyX(c));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isInfinite() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boundary clone(Shape scaledShape, Lattice clonedLattice) {
        return null;
    }

    /**
     * Applys the x-boundary by wrapping the coordinate in the x direction
     * (so this is a periodic x boundary)
     *
     * @param c the coordinate to wrap
     * @return a new wrapped coordinate
     */
    public Coordinate applyX(Coordinate c) {
        Coordinate ob = shape.getOverbounds(c);

        if (ob.x() == 0) {
            // Within bounds: do nothing
            return c;
        }

        int flags = c.flags() | Flags.BOUNDARY_APPLIED;

        if (ob.x() > 0) {
            // Out of bounds to the right: set to left edge
            return new Coordinate(0, c.y(), flags);
        } else {
            // Out of bounds to the left: set to right edge
            return new Coordinate(WIDTH - 1, c.y(), flags);
        }
    }

    /**
     * Reflects the coordinate from the lower y-boundary and absorbs it in the
     * top y-boundary.
     *
     * @param c the coordinate to reflect/absorb
     * @return the reflected or absorbed coordinate
     */
    public Coordinate applyY(Coordinate c) {
        int x = c.x();
        int y = c.y();

        if (y >= HEIGHT) {
            // Coordinate is above: absorb
            return null;
        } else if (y < 0) {
            int y1 = -1 * (y + 1);
            int flags = c.flags() | Flags.BOUNDARY_APPLIED;
            return applyY(new Coordinate(x, y1, flags));
        }

        // Base case: coordinate is not above or below, so return it.
        return c;
    }
}
