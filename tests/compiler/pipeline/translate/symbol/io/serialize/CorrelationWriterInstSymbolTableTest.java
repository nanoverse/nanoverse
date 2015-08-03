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

package compiler.pipeline.translate.symbol.io.serialize;

import compiler.pipeline.translate.symbol.MapSymbolTable;
import compiler.pipeline.translate.symbol.tables.MapSymbolTableTest;
import control.arguments.*;
import io.serialize.text.CorrelationWriter;
import org.junit.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CorrelationWriterInstSymbolTableTest extends MapSymbolTableTest {


    @Override
    protected MapSymbolTable getQuery() {
        return new CorrelationWriterInstSymbolTable();
    }

    @Override
    protected Class getExpectedClass() {
        return CorrelationWriter.class;
    }

    @Test
    public void filename() throws Exception {
        verifyReturnSymbol("filename", StringArgument.class);
    }

    @Test
    public void time() throws Exception {
        verifyReturnSymbol("time", IntegerArgument.class);
    }
}