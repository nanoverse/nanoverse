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

package nanoverse.compiler.pipeline.translate.symbol.io.serialize;

import nanoverse.compiler.pipeline.translate.symbol.MapSymbolTable;
import nanoverse.compiler.pipeline.translate.symbol.tables.MapSymbolTableTest;
import nanoverse.runtime.control.arguments.StringArgument;
import nanoverse.runtime.io.serialize.binary.VisualizationSerializer;
import nanoverse.runtime.io.visual.Visualization;
import org.junit.*;

public class VisualizationSerializerInstSymbolTableTest extends MapSymbolTableTest {

    @Override
    protected MapSymbolTable getQuery() {
        return new VisualizationSerializerInstSymbolTable();
    }

    @Override
    protected Class getExpectedClass() {
        return VisualizationSerializer.class;
    }

    @Test
    public void prefix() throws Exception {
        verifyReturnSymbol("prefix", StringArgument.class);
    }

    @Test
    public void visualization() throws Exception {
        verifyReturnSymbol("visualization", Visualization.class);
    }
}