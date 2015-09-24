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

package compiler.pipeline.translate.symbol.primitive.strings;

import compiler.pipeline.translate.symbol.InstantiableSymbolTable;
import compiler.pipeline.translate.symbol.MapSymbolTable;
import compiler.pipeline.translate.symbol.tables.InstantiableSymbolTableTest;
import compiler.pipeline.translate.symbol.tables.MapSymbolTableTest;
import control.arguments.StringArgument;
import org.junit.*;

import static org.junit.Assert.*;

public class StringInstSymbolTableTest extends InstantiableSymbolTableTest {

    @Override
    protected InstantiableSymbolTable getQuery() {
        return new StringInstSymbolTable();
    }

    @Override
    protected Class getExpectedClass() {
        return StringArgument.class;
    }
}