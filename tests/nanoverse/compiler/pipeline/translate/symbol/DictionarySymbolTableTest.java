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

package nanoverse.compiler.pipeline.translate.symbol;

import junit.framework.TestCase;
import nanoverse.compiler.pipeline.instantiate.loader.Loader;
import nanoverse.compiler.pipeline.translate.symbol.agent.action.ActionClassSymbolTable;
import org.junit.Test;
import test.TestBase;

import java.util.function.Supplier;

import static org.mockito.Mockito.mock;

/**
 * Created by lizzybradley on 12/7/15.
 */
public class DictionarySymbolTableTest extends TestBase {
    @Test
    public void getClassSymbolTableClass() {
        ActionClassSymbolTable acst = new ActionClassSymbolTable();
        Supplier<Loader> loaderSupplier = mock(Supplier.class);

        DictionarySymbolTable dst = new DictionarySymbolTable(acst, loaderSupplier);

        TestCase.assertEquals(dst.getResolvingSymbolTableClass(), acst.getClass());
    }

    @Test
    public void getResolvingSymbolTableDescription() {
        ActionClassSymbolTable acst = new ActionClassSymbolTable();
        Supplier<Loader> loaderSupplier = mock(Supplier.class);

        DictionarySymbolTable dst = new DictionarySymbolTable(acst, loaderSupplier);

        TestCase.assertEquals(dst.getResolvingSymbolTableDescription(), acst.getDescription());
    }
}
