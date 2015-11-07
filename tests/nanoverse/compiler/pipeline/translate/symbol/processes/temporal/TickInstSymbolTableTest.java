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

package nanoverse.compiler.pipeline.translate.symbol.processes.temporal;

import nanoverse.compiler.pipeline.translate.symbol.MapSymbolTable;
import nanoverse.compiler.pipeline.translate.symbol.processes.ProcessInstSymbolTableTest;
import nanoverse.runtime.control.arguments.DoubleArgument;
import nanoverse.runtime.processes.temporal.Tick;
import org.junit.Test;

public class TickInstSymbolTableTest extends ProcessInstSymbolTableTest {

    @Override
    protected MapSymbolTable getQuery() {
        return new TickInstSymbolTable();
    }

    @Override
    protected Class getExpectedClass() {
        return Tick.class;
    }

    @Test
    public void dt() throws Exception {
        verifyReturnSymbol("dt", DoubleArgument.class);
    }
}