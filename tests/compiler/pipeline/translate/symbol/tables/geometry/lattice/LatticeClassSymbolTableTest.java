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

package compiler.pipeline.translate.symbol.tables.geometry.lattice;

import compiler.pipeline.translate.symbol.tables.*;
import geometry.lattice.*;
import org.junit.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class LatticeClassSymbolTableTest extends ClassSymbolTableTest {

    @Override
    protected ClassSymbolTable getQuery() {
        return new LatticeClassSymbolTable();
    }

    @Override
    protected Class getExpectedClass() {
        return Lattice.class;
    }

    @Test
    public void cubic() throws Exception {
        verifyReturnSymbol("Cubic", CubicLattice.class);
    }

    @Test
    public void triangular() throws Exception {
        verifyReturnSymbol("Triangular", TriangularLattice.class);
    }

    @Test
    public void rectangular() throws Exception {
        verifyReturnSymbol("Rectangular", RectangularLattice.class);
    }

    @Test
    public void linear() throws Exception {
        verifyReturnSymbol("Linear", LinearLattice.class);
    }
}