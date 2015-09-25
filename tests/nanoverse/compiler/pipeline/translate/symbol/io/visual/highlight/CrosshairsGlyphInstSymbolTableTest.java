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

package nanoverse.compiler.pipeline.translate.symbol.io.visual.highlight;

import nanoverse.compiler.pipeline.translate.symbol.MapSymbolTable;
import nanoverse.compiler.pipeline.translate.symbol.tables.MapSymbolTableTest;
import nanoverse.runtime.control.arguments.*;
import nanoverse.runtime.io.visual.highlight.CrosshairsGlyph;
import org.junit.*;

public class CrosshairsGlyphInstSymbolTableTest extends MapSymbolTableTest {

    @Override
    protected MapSymbolTable getQuery() {
        return new CrosshairsGlyphInstSymbolTable();
    }

    @Override
    protected Class getExpectedClass() {
        return CrosshairsGlyph.class;
    }

    @Test
    public void color() throws Exception {
        verifyReturnSymbol("color", StringArgument.class);
    }

    @Test
    public void cross() throws Exception {
        verifyReturnSymbol("cross", DoubleArgument.class);
    }

    @Test
    public void circle() throws Exception {
        verifyReturnSymbol("circle", DoubleArgument.class);
    }
}