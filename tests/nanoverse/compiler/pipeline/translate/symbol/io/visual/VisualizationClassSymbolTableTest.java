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

package nanoverse.compiler.pipeline.translate.symbol.io.visual;

import nanoverse.compiler.pipeline.translate.symbol.ClassSymbolTable;
import nanoverse.compiler.pipeline.translate.symbol.tables.ClassSymbolTableTest;
import nanoverse.runtime.io.visual.*;
import nanoverse.runtime.io.visual.kymograph.Kymograph;
import nanoverse.runtime.io.visual.map.MapVisualization;
import org.junit.Test;

public class VisualizationClassSymbolTableTest extends ClassSymbolTableTest {

    @Override
    protected ClassSymbolTable getQuery() {
        return new VisualizationClassSymbolTable();
    }

    @Override
    protected Class getExpectedClass() {
        return Visualization.class;
    }

    @Test
    public void mock() throws Exception {
        verifyReturnSymbol("Mock", MockVisualization.class);
    }

    @Test
    public void kymograph() throws Exception {
        verifyReturnSymbol("Kymograph", Kymograph.class);
    }

    @Test
    public void map() throws Exception {
        verifyReturnSymbol("Map", MapVisualization.class);

    }
}