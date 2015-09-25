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
package nanoverse.compiler.pipeline.instantiate.factory.geometry.boundaries;

import nanoverse.compiler.pipeline.instantiate.factory.Factory;
import nanoverse.runtime.geometry.boundaries.Periodic;
import nanoverse.runtime.geometry.lattice.Lattice;
import nanoverse.runtime.geometry.shape.Shape;

public class PeriodicFactory implements Factory<Periodic> {

    private final PeriodicFactoryHelper helper;

    private Shape shape;
    private Lattice lattice;

    public PeriodicFactory() {
        helper = new PeriodicFactoryHelper();
    }

    public PeriodicFactory(PeriodicFactoryHelper helper) {
        this.helper = helper;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }

    public void setLattice(Lattice lattice) {
        this.lattice = lattice;
    }

    @Override
    public Periodic build() {
        return helper.build(shape, lattice);
    }
}