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

package compiler.pipeline.translate.symbol.io.visual.highlight;

import compiler.pipeline.translate.symbol.ClassSymbolTable;
import compiler.pipeline.translate.symbol.tables.ClassSymbolTableTest;
import io.visual.highlight.*;
import org.junit.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class GlyphClassSymbolTableTest extends ClassSymbolTableTest {

    @Override
    protected ClassSymbolTable getQuery() {
        return new GlyphClassSymbolTable();
    }

    @Override
    protected Class getExpectedClass() {
        return Glyph.class;
    }

    @Test
    public void crosshairs() throws Exception {
        verifyReturnSymbol("Crosshairs", CrosshairsGlyph.class);
    }

    @Test
    public void bullseye() throws Exception {
        verifyReturnSymbol("Bullseye", BullseyeGlyph.class);
    }

    @Test
    public void dot() throws Exception {
        verifyReturnSymbol("Dot", DotGlyph.class);
    }
}