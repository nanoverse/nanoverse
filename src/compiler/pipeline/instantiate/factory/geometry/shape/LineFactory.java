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
package compiler.pipeline.instantiate.factory.geometry.shape;

import geometry.shape.Line;
import geometry.lattice.Lattice;
import compiler.pipeline.instantiate.factory.Factory;

public class LineFactory implements Factory<Line> {

    private final LineFactoryHelper helper;

    private Lattice lattice;
    private int length;

    public LineFactory() {
        helper = new LineFactoryHelper();
    }

    public LineFactory(LineFactoryHelper helper) {
        this.helper = helper;
    }

    public void setLattice(Lattice lattice) {
        this.lattice = lattice;
    }

    public void setLength(int length) {
        this.length = length;
    }

    @Override
    public Line build() {
        return helper.build(lattice, length);
    }
}