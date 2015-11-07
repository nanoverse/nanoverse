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

package nanoverse.compiler.pipeline.translate.symbol.layers.continuum;

import nanoverse.compiler.pipeline.translate.symbol.MapSymbolTable;
import nanoverse.compiler.pipeline.translate.symbol.tables.MapSymbolTableTest;
import nanoverse.runtime.control.arguments.*;
import nanoverse.runtime.geometry.boundaries.Boundary;
import nanoverse.runtime.layers.continuum.ContinuumLayer;
import nanoverse.runtime.layers.continuum.solvers.ContinuumSolver;
import org.junit.Test;

public class ContinuumLayerInstSymbolTableTest extends MapSymbolTableTest {

    @Override
    protected MapSymbolTable getQuery() {
        return new ContinuumLayerInstSymbolTable();
    }

    @Override
    protected Class getExpectedClass() {
        return ContinuumLayer.class;
    }

    @Test
    public void solver() throws Exception {
        verifyReturnSymbol("solver", ContinuumSolver.class);
    }

    @Test
    public void disableOperators() throws Exception {
        verifyReturnSymbol("disableOperators", BooleanArgument.class);
    }

    @Test
    public void boundary() throws Exception {
        verifyReturnSymbol("boundary", Boundary.class);
    }

    @Test
    public void id() throws Exception {
        verifyReturnSymbol("id", StringArgument.class);
    }
}