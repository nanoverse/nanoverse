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

package nanoverse.compiler.pipeline.translate.symbol.processes.continuum;

import nanoverse.compiler.pipeline.translate.symbol.InstantiableSymbolTable;
import nanoverse.compiler.pipeline.translate.symbol.tables.MapSymbolTableTest;
import nanoverse.runtime.control.arguments.DoubleArgument;
import nanoverse.runtime.control.arguments.StringArgument;
import nanoverse.runtime.geometry.set.CoordinateSet;
import nanoverse.runtime.processes.continuum.DirichletBoundaryEnforcer;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by dbborens on 10/5/2015.
 */
public class DirichletBoundaryEnforcerInstSymbolTableTest extends MapSymbolTableTest {

    @Test
    public void testValue() throws Exception {
        verifyReturnSymbol("value", DoubleArgument.class);
    }

    @Test
    public void testLayer() throws Exception {
        verifyReturnSymbol("layer", StringArgument.class);

    }

    @Test
    public void testActiveSites() throws Exception {
        verifyReturnSymbol("activeSites", CoordinateSet.class);
    }

    @Override
    protected InstantiableSymbolTable getQuery() {
        return new DirichletBoundaryEnforcerInstSymbolTable();
    }

    @Override
    protected Class getExpectedClass() {
        return DirichletBoundaryEnforcer.class;
    }
}