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

package nanoverse.compiler.pipeline.translate.symbol.geometry.set;

import nanoverse.compiler.pipeline.translate.symbol.ClassSymbolTable;
import nanoverse.compiler.pipeline.translate.symbol.tables.ClassSymbolTableTest;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.geometry.set.*;
import org.junit.Test;

public class CoordinateSetClassSymbolTableTest extends ClassSymbolTableTest {

    @Override
    protected ClassSymbolTable getQuery() {
        return new CoordinateSetClassSymbolTable();
    }

    @Override
    protected Class getExpectedClass() {
        return CoordinateSet.class;
    }

    @Test
    public void hline() throws Exception {
        verifyReturnSymbol("HLine", HorizontalLineSet.class);
    }

    @Test
    public void custom() throws Exception {
        verifyLSTBroadClass("Custom", Coordinate.class);
    }

    @Test
    public void disc() throws Exception {
        verifyReturnSymbol("Disc", DiscSet.class);
    }

    @Test
    public void all() throws Exception {
        verifyReturnSymbol("All", CompleteSet.class);
    }
}