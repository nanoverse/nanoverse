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
import nanoverse.runtime.geometry.lattice.Lattice;
import nanoverse.runtime.geometry.shape.Shape;

/**
 * Created by dbborens on 5/7/14.
 */
public abstract class WrapHelper {
    protected Lattice lattice;
    protected Shape shape;

    public WrapHelper(Shape shape, Lattice lattice) {
        this.lattice = lattice;
        this.shape = shape;
    }

    public abstract Coordinate wrapAll(Coordinate toWrap);

    public abstract Coordinate xWrap(Coordinate toWrap);

    public abstract Coordinate yWrap(Coordinate toWrap);

    public abstract Coordinate zWrap(Coordinate toWrap);

    protected abstract void checkValid(Coordinate toWrap);

    @Override
    public boolean equals(Object o) {
        return (o.getClass() == this.getClass());
    }
}
