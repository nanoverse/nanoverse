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

package compiler.pipeline.translate.symbol.tables;

import org.junit.*;
import test.TestBase;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertNotNull;

/**
 * Created by dbborens on 7/21/2015.
 */
public abstract class ClassSymbolTableTest extends TestBase {

    protected ClassSymbolTable query;

    @Before
    public void before() throws Exception {
        query = getQuery();
    }

    protected abstract ClassSymbolTable getQuery();
    protected abstract Class getExpectedClass();

    @Test
    public void broadClassAsExpected() {
        Class expected = getExpectedClass();
        Class actual = query.getBroadClass();
        assertEquals(expected, actual);
    }

    protected void verifyReturnSymbol(String identifier, Class expected) {
        InstantiableSymbolTable ist = query.getSymbolTable(identifier);
        Class actual = ist.getInstanceClass();
        assertEquals(expected, actual);
    }

    @Test
    public void descriptionIsNotNull() {
        assertNotNull(query.getDescription());
    }
}
