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

package nanoverse.compiler.pipeline.instantiate.factory.geometry;

import nanoverse.compiler.pipeline.instantiate.loader.geometry.shape.*;
import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.geometry.lattice.*;
import nanoverse.runtime.geometry.shape.*;

/**
 * Created by dbborens on 8/19/2015.
 */
public class GeometryDescriptorDefaults {

    public Lattice lattice() {
        return new RectangularLattice();
    }

    public Shape shape(Lattice lattice, GeneralParameters p) {
        if (lattice instanceof RectangularLattice) {
            RectangleLoader loader = new RectangleLoader();
            return loader.instantiate(lattice, p);
        } else if (lattice instanceof TriangularLattice) {
            HexagonLoader loader = new HexagonLoader();
            return loader.instantiate(lattice, p);
        } else if (lattice instanceof LinearLattice) {
            LineLoader loader = new LineLoader();
            return loader.instantiate(lattice, p);
        } else if (lattice instanceof  CubicLattice) {
            CuboidLoader loader = new CuboidLoader();
            return loader.instantiate(lattice, p);
        } else {
            throw new IllegalStateException();
        }
    }
}
