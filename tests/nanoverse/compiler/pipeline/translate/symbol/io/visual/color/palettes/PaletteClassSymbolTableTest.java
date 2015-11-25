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

package nanoverse.compiler.pipeline.translate.symbol.io.visual.color.palettes;

import nanoverse.compiler.pipeline.translate.symbol.ClassSymbolTable;
import nanoverse.compiler.pipeline.translate.symbol.tables.ClassSymbolTableTest;
import nanoverse.runtime.io.visual.color.palettes.CustomPalette;
import nanoverse.runtime.io.visual.color.palettes.Palette;
import nanoverse.runtime.io.visual.color.palettes.RainbowColorPalette;
import org.junit.Test;

/**
 * Created by dbborens on 11/25/2015.
 */
public class PaletteClassSymbolTableTest extends ClassSymbolTableTest {

    @Override
    protected ClassSymbolTable getQuery() {
        return new PaletteClassSymbolTable();
    }

    @Override
    protected Class getExpectedClass() {
        return Palette.class;
    }

    @Test
    public void rainbow() throws Exception {
        verifyReturnSymbol("Rainbow", RainbowColorPalette.class);
    }

    @Test
    public void custom() throws Exception {
        verifyReturnSymbol("Custom", CustomPalette.class);
    }

}