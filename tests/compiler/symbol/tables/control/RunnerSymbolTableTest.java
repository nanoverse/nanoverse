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

package compiler.symbol.tables.control;

import compiler.symbol.tables.*;
import control.*;
import control.arguments.GeometryDescriptor;
import control.run.Runner;
import geometry.Geometry;
import io.serialize.*;
import layers.*;
import org.junit.Test;
import processes.NanoverseProcess;

public class RunnerSymbolTableTest extends MapSymbolTableTest {

    @Override
    protected MapSymbolTable getQuery() {
        return new RunnerSymbolTable();
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

}