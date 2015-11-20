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

package nanoverse.compiler.pipeline.translate.symbol;

import junit.framework.TestCase;
import nanoverse.compiler.pipeline.instantiate.loader.Loader;
import org.junit.Test;
import test.TestBase;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ListSymbolTableTest extends TestBase {
    @Test
    public void getMemberNames() throws Exception {
        HashMap<String, Supplier<InstantiableSymbolTable>> members = new HashMap<>();
        members.put("a", null);
        members.put("b", null);
        members.put("c", null);

        Set<String> testSet = new HashSet<>();
        testSet.add("a");
        testSet.add("b");
        testSet.add("c");

        ClassSymbolTable cst = mock(ClassSymbolTable.class);
        when(cst.resolveSubclasses()).thenReturn(members);

        Supplier<Loader> loaderSupplier = mock(Supplier.class);
        ListSymbolTable lst = new ListSymbolTable(cst, loaderSupplier);

        assertSetsEqual(testSet, lst.getMemberNames());
        TestCase.assertEquals(3, lst.getMemberNames().size());
    }
}
