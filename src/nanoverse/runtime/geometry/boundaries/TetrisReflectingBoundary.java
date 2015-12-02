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
import nanoverse.runtime.geometry.basis.BasisHelper2D;
import nanoverse.runtime.geometry.boundaries.helpers.*;
import nanoverse.runtime.geometry.lattice.Lattice;
import nanoverse.runtime.geometry.shape.*;
import nanoverse.runtime.structural.annotations.FactoryTarget;

/**
 * Boundary condition that is periodic in X, reflecting on
 * the lower Y boundary, and absorbing on the upper Y boundary.
 * (Dirichlet conditions can be set on the upper Y boundary
 * using the DirichletEnforcerProcess.
 * <p>
 * This is a special case, so it only works for rectangular
 * arenas with a rectangular coordinate system.
 *
 * @author Daniel Greenidge
 */
public class TetrisReflectingBoundary extends Boundary {

    private final int height;
    private final WrapHelper2D wrapHelper;
    private final ReflectHelper2D reflectHelper;

    @FactoryTarget
    public TetrisReflectingBoundary(Shape shape, Lattice lattice) {
        super(shape, lattice);
        this.height = shape.getDimensions()[1];
        this.wrapHelper = new WrapHelper2D(shape, lattice);
        this.reflectHelper = new ReflectHelper2D(shape, lattice);
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
        Coordinate cX = applyX(c);
        Coordinate cXY = applyY(cX);
        return cXY;
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
        return new TetrisReflectingBoundary(scaledShape, clonedLattice);
    }

    /**
     * Applys the x-boundary by wrapping the coordinate in the x direction
     * (so this is a periodic x boundary)
     *
     * @param c the coordinate to wrap
     * @return a new wrapped coordinate
     */
    public Coordinate applyX(Coordinate c) {
        return wrapHelper.xWrap(c);
    }

    /**
     * Reflects the coordinate from the lower y-boundary and absorbs it in the
     * top y-boundary.
     *
     * @param c the coordinate to reflect/absorb
     * @return the reflected or absorbed coordinate
     */
    public Coordinate applyY(Coordinate c) {
        BasisHelper2D basisHelper = wrapHelper.getBasisHelper();
        int yAdj = basisHelper.invAdjust(c).y();
        if (yAdj >= height) {
            return null;
        } else if (yAdj < 0) {
            return reflectHelper.yReflect(c);
        }

        return c;
    }
}
