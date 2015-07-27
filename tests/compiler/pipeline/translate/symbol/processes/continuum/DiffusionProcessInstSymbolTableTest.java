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

package compiler.pipeline.translate.symbol.processes.continuum;

import compiler.pipeline.translate.symbol.MapSymbolTable;
import compiler.pipeline.translate.symbol.processes.ProcessInstSymbolTableTest;
import control.arguments.*;
import org.junit.*;
import processes.continuum.OperatorProcess;

public class DiffusionProcessInstSymbolTableTest extends ProcessInstSymbolTableTest {

    @Override
    protected MapSymbolTable getQuery() {
        return new DiffusionProcessInstSymbolTable();
    }

    @Override
    protected Class getExpectedClass() {
        return OperatorProcess.class;
    }

    @Test
    public void layer() throws Exception {
        verifyReturnSymbol("layer", StringArgument.class);
    }

    @Test
    public void constant() throws Exception {
        verifyReturnSymbol("constant", DoubleArgument.class);
    }
}