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

package nanoverse.compiler.pipeline.translate.symbol.processes;

import nanoverse.compiler.pipeline.translate.symbol.MapSymbolTable;
import nanoverse.compiler.pipeline.translate.symbol.processes.discrete.*;
import nanoverse.runtime.control.arguments.*;
import nanoverse.runtime.processes.discrete.TriggerProcess;
import nanoverse.runtime.processes.discrete.filter.Filter;
import org.junit.Test;

public class TriggerProcessInstSymbolTableTest extends DiscreteProcessInstSymbolTableTest {

    @Override
    protected MapSymbolTable getQuery() {
        return new TriggerProcessInstSymbolTable();
    }

    @Override
    protected Class getExpectedClass() {
        return TriggerProcess.class;
    }

    @Test
    public void behavior() throws Exception {
        verifyReturnSymbol("behavior", StringArgument.class);
    }

    @Test
    public void skipFilledSites() throws Exception {
        verifyReturnSymbol("skipVacantSites", BooleanArgument.class);
    }

    @Test
    public void requireNeighbors() throws Exception {
        verifyReturnSymbol("requireNeighbors", BooleanArgument.class);
    }

    public void filter() throws Exception {
        verifyReturnSymbol("filter", Filter.class);
    }
}