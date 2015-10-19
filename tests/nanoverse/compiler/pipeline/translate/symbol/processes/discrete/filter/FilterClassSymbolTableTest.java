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

package nanoverse.compiler.pipeline.translate.symbol.processes.discrete.filter;

import nanoverse.compiler.pipeline.translate.symbol.ClassSymbolTable;
import nanoverse.compiler.pipeline.translate.symbol.tables.ClassSymbolTableTest;
import nanoverse.runtime.processes.discrete.filter.*;
import org.junit.Test;

public class FilterClassSymbolTableTest extends ClassSymbolTableTest {

    @Override
    protected ClassSymbolTable getQuery() {
        return new FilterClassSymbolTable();
    }

    @Override
    protected Class getExpectedClass() {
        return Filter.class;
    }

    @Test
    public void cellState() throws Exception {
        verifyReturnSymbol("Name", AgentNameFilter.class);
    }

    @Test
    public void composite() throws Exception {
        verifyReturnSymbol("Composite", CompositeFilter.class);
    }

    @Test
    public void depth() throws Exception {
        verifyReturnSymbol("Depth", DepthFilter.class);
    }

    @Test
    public void nullFilter() throws Exception {
        verifyReturnSymbol("Null", NullFilter.class);
    }

    @Test
    public void sampleFilter() throws Exception {
        verifyReturnSymbol("Sample", SampleFilter.class);
    }
}