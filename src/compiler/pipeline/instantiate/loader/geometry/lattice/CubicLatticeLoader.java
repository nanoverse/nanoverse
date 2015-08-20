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

package compiler.pipeline.instantiate.loader.geometry.lattice;

import compiler.pipeline.instantiate.factory.geometry.lattice.CubicLatticeFactory;
import compiler.pipeline.translate.nodes.ObjectNode;
import geometry.lattice.CubicLattice;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by dbborens on 8/4/2015.
 */
public class CubicLatticeLoader extends LatticeLoader<CubicLattice> {

    private final CubicLatticeFactory factory;

    public CubicLatticeLoader() {
        factory = new CubicLatticeFactory();
    }

    public CubicLatticeLoader(CubicLatticeFactory factory) {
        this.factory = factory;
    }

    @Override
    public CubicLattice instantiate() {
        return factory.build();
    }
}
