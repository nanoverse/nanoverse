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

package nanoverse.compiler.pipeline.translate.symbol.tables;

import nanoverse.compiler.pipeline.translate.symbol.*;
import org.junit.*;
import test.TestBase;

import static org.junit.Assert.*;

/**
 * Created by dbborens on 7/21/2015.
 */
public abstract class ClassSymbolTableTest extends TestBase {

    protected ClassSymbolTable query;
    private int LINE = 0;

    @Before
    public void before() throws Exception {
        query = getQuery();
    }

    protected abstract ClassSymbolTable getQuery();

    @Test
    public void broadClassAsExpected() {
        Class expected = getExpectedClass();
        Class actual = query.getBroadClass();
        assertEquals(expected, actual);
    }

    protected abstract Class getExpectedClass();

    protected void verifyReturnSymbol(String identifier, Class expected) {
        InstantiableSymbolTable ist = query.getSymbolTable(identifier, LINE);
        Class actual = ist.getInstanceClass();
        assertEquals(expected, actual);
    }

    protected void verifyLSTBroadClass(String identifier, Class expected) {
        ListSymbolTable ist = (ListSymbolTable) query.getSymbolTable(identifier, LINE);
        Class actual = ist.getBroadClass();
        assertEquals(expected, actual);
    }

    @Test
    public void descriptionIsNotNull() {
        assertNotNull(query.getDescription());
    }
}
