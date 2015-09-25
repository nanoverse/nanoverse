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

package nanoverse.compiler.pipeline.translate.symbol.io.visual;

import nanoverse.compiler.pipeline.translate.symbol.MapSymbolTable;
import nanoverse.compiler.pipeline.translate.symbol.tables.MapSymbolTableTest;
import nanoverse.runtime.control.arguments.IntegerArgument;
import nanoverse.runtime.io.visual.color.ColorManager;
import nanoverse.runtime.io.visual.highlight.*;
import nanoverse.runtime.io.visual.map.MapVisualization;
import org.junit.*;

public class MapVisualizationInstSymbolTableTest extends MapSymbolTableTest {

    @Override
    protected MapSymbolTable getQuery() {
        return new MapVisualizationInstSymbolTable();
    }

    @Override
    protected Class getExpectedClass() {
        return MapVisualization.class;
    }

    @Test
    public void highlights() throws Exception {
        verifyReturnSymbol("highlights", Highlight.class);
    }

    @Test
    public void color() throws Exception {
        verifyReturnSymbol("color", ColorManager.class);
    }

    @Test
    public void outline() throws Exception {
        verifyReturnSymbol("outline", IntegerArgument.class);
    }

    @Test
    public void edge() throws Exception {
        verifyReturnSymbol("edge", IntegerArgument.class);
    }
}