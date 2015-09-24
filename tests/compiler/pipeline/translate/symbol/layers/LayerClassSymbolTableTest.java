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

package compiler.pipeline.translate.symbol.layers;

import compiler.pipeline.translate.symbol.*;
import compiler.pipeline.translate.symbol.tables.ClassSymbolTableTest;
import layers.Layer;
import layers.cell.CellLayer;
import layers.continuum.ContinuumLayer;
import org.junit.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class LayerClassSymbolTableTest extends ClassSymbolTableTest {


    @Override
    protected ClassSymbolTable getQuery() {
        return new LayerClassSymbolTable();
    }

    @Override
    protected Class getExpectedClass() {
        return Layer.class;
    }

    @Test
    public void continuumLayer() throws Exception {
        verifyReturnSymbol("ContinuumLayer", ContinuumLayer.class);
    }

    @Test
    public void agentLayer() throws Exception {
        verifyReturnSymbol("AgentLayer", CellLayer.class);
    }
}