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

import nanoverse.compiler.pipeline.instantiate.loader.Loader;
import nanoverse.compiler.pipeline.translate.symbol.InstantiableSymbolTable;
import org.junit.*;
import test.TestBase;

import static org.junit.Assert.*;

/**
 * Created by dbborens on 9/21/2015.
 */
public abstract class InstantiableSymbolTableTest extends TestBase {

    protected InstantiableSymbolTable query;

    @Before
    public void before() throws Exception {
        query = getQuery();
    }

    protected abstract InstantiableSymbolTable getQuery();

    @Test
    public void instanceClassAsExpected() {
        Class expected = getExpectedClass();
        Class actual = query.getInstanceClass();
        assertEquals(expected, actual);
    }

    protected abstract Class getExpectedClass();

    @Test
    public void descriptionIsNotNull() {
        assertNotNull(query.getDescription());
    }

    @Test
    public void verifyLoaderClass() {
        Loader loader = query.getLoader();
        Class expected = getExpectedClass();
        Class actual = loader.getInstanceClass();
        assertEquals(expected, actual);
    }
}
