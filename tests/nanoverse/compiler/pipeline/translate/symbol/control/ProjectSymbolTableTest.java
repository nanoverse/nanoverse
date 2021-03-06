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

package nanoverse.compiler.pipeline.translate.symbol.control;

import nanoverse.compiler.pipeline.translate.symbol.MapSymbolTable;
import nanoverse.compiler.pipeline.translate.symbol.control.run.ProjectSymbolTable;
import nanoverse.compiler.pipeline.translate.symbol.tables.MapSymbolTableTest;
import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.control.arguments.*;
import nanoverse.runtime.control.run.Runner;
import nanoverse.runtime.io.serialize.Serializer;
import nanoverse.runtime.layers.Layer;
import nanoverse.runtime.processes.NanoverseProcess;
import org.junit.Test;

public class ProjectSymbolTableTest extends MapSymbolTableTest {

    @Override
    protected MapSymbolTable getQuery() {
        return new ProjectSymbolTable();
    }

    @Override
    protected Class getExpectedClass() {
        return Runner.class;
    }

    @Test
    public void layerManager() throws Exception {
        verifyReturnSymbol("layers", Layer.class);
    }

    @Test
    public void output() throws Exception {
        verifyReturnSymbol("output", Serializer.class);
    }

    @Test
    public void processes() throws Exception {
        verifyReturnSymbol("processes", NanoverseProcess.class);
    }

    @Test
    public void parameters() throws Exception {
        verifyReturnSymbol("parameters", GeneralParameters.class);
    }

    @Test
    public void geometry() throws Exception {
        verifyReturnSymbol("geometry", GeometryDescriptor.class);
    }

    @Test
    public void version() throws Exception {
        verifyReturnSymbol("version", StringArgument.class);
    }
}