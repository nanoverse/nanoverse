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

import nanoverse.runtime.geometry.lattice.Lattice;
import nanoverse.runtime.geometry.shape.Shape;
import nanoverse.runtime.structural.annotations.FactoryTarget;

/**
 * The HaltBoundary works just like an arena. The class itself is used to flag
 * the fact that reaching the boundary should result in halting the simulation.
 * <p>
 * Created by dbborens on 7/30/14.
 */
public class HaltArena extends Arena implements HaltBoundary {

    @FactoryTarget
    public HaltArena(Shape shape, Lattice lattice) {
        super(shape, lattice);
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public Boundary clone(Shape scaledShape, Lattice clonedLattice) {
        return new HaltArena(scaledShape, clonedLattice);
    }
}
