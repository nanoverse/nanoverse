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

package compiler.pipeline.translate.symbol.tables.processes.discrete;

import compiler.pipeline.translate.symbol.tables.MapSymbolTable;
import control.arguments.*;
import org.junit.*;
import processes.discrete.*;
import processes.discrete.cluster.ScatterClustersHelper;

public class ScatterClustersInstSymbolTableTest extends DiscreteProcessInstSymbolTableTest{

    @Override
    protected MapSymbolTable getQuery() {
        return new ScatterClustersInstSymbolTable();
    }

    @Override
    protected Class getExpectedClass() {
        return ScatterClusters.class;
    }

    @Test
    public void description() throws Exception {
        verifyReturnSymbol("description", CellDescriptor.class);
    }

    @Test
    public void separation() throws Exception {
        verifyReturnSymbol("separation", ScatterClustersHelper.class);
    }

    @Test
    public void neighbors() throws Exception {
        verifyReturnSymbol("neighbors", IntegerArgument.class);
    }
}