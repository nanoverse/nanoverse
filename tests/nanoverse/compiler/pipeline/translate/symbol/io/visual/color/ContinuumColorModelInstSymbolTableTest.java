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

package nanoverse.compiler.pipeline.translate.symbol.io.visual.color;

import nanoverse.compiler.pipeline.translate.symbol.MapSymbolTable;
import nanoverse.compiler.pipeline.translate.symbol.tables.MapSymbolTableTest;
import nanoverse.runtime.control.arguments.*;
import nanoverse.runtime.io.visual.color.*;
import org.junit.Test;

public class ContinuumColorModelInstSymbolTableTest extends MapSymbolTableTest {

    @Override
    protected MapSymbolTable getQuery() {
        return new ContinuumColorModelInstSymbolTable();
    }

    @Override
    protected Class getExpectedClass() {
        return NormalizedContinuumColorManager.class;
    }

    @Test
    public void id() throws Exception {
        verifyReturnSymbol("id", StringArgument.class);
    }

    @Test
    public void base() throws Exception {
        verifyReturnSymbol("base", ColorManager.class);
    }

    @Test
    public void minHue() throws Exception {
        verifyReturnSymbol("minHue", DoubleArgument.class);
    }

    @Test
    public void maxHue() throws Exception {
        verifyReturnSymbol("maxHue", DoubleArgument.class);
    }

    @Test
    public void minSat() throws Exception {
        verifyReturnSymbol("minSat", DoubleArgument.class);
    }

    @Test
    public void maxSat() throws Exception {
        verifyReturnSymbol("maxSat", DoubleArgument.class);
    }

    @Test
    public void minLum() throws Exception {
        verifyReturnSymbol("minLum", DoubleArgument.class);
    }

    @Test
    public void maxLum() throws Exception {
        verifyReturnSymbol("maxLum", DoubleArgument.class);
    }

    @Test
    public void useLuminanceAverage() throws Exception {
        verifyReturnSymbol("useLuminanceAverage", BooleanArgument.class);
    }
}