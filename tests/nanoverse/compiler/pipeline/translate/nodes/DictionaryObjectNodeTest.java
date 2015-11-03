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

package nanoverse.compiler.pipeline.translate.nodes;

import nanoverse.compiler.pipeline.translate.symbol.DictionarySymbolTable;
import org.junit.*;

import java.util.stream.Stream;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class DictionaryObjectNodeTest {

    private DictionarySymbolTable symbolTable;
    private LocalContextMap local;

    private DictionaryObjectNode query;

    @Before
    public void before() throws Exception {
        symbolTable = mock(DictionarySymbolTable.class);
        local = mock(LocalContextMap.class);
        query = new DictionaryObjectNode(symbolTable, local);
    }

    @Test
    public void getMemberIdentifiers() throws Exception {
        Stream<String> expected = mock(Stream.class);
        when(local.getMemberIdentifiers()).thenReturn(expected);
        Stream<String> actual = query.getMemberIdentifiers();
        assertSame(expected, actual);
    }

    @Test
    public void getMember() throws Exception {
        ObjectNode expected = mock(ObjectNode.class);
        when(local.getMember("test")).thenReturn(expected);
        ObjectNode actual = query.getMember("test");
        assertSame(expected, actual);
    }

    @Test
    public void loadMember() throws Exception {
        ObjectNode value = mock(ObjectNode.class);
        query.loadMember("test", value);
        verify(local).loadMember("test", value);
    }

    @Test
    public void size() throws Exception {
        when(local.size()).thenReturn(7);
        assertEquals(7, query.size());
    }

    @Test
    public void getSymbolTable() throws Exception {
        assertSame(symbolTable, query.getSymbolTable());
    }

    @Test
    public void getInstantiatingClass() throws Exception {
        Class expected = DictionaryObjectNodeTest.class;
        when(symbolTable.getBroadClass()).thenReturn(expected);
        Class actual = query.getInstantiatingClass();
        assertSame(expected, actual);
    }
}