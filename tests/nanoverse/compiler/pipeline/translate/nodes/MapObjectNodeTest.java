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

import nanoverse.compiler.pipeline.translate.symbol.*;
import org.junit.*;

import java.util.stream.Stream;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class MapObjectNodeTest {

    private LocalContextMap local;
    private MapSymbolTable symbolTable;

    private MapObjectNode query;

    private int lineNumber = 1;

    @Before
    public void before() throws Exception {
        local = mock(LocalContextMap.class);
        symbolTable = mock(MapSymbolTable.class);
        query = new MapObjectNode(symbolTable, local, lineNumber);
    }

    @Test
    public void lineNumber() throws Exception {
        assertEquals(query.getLineNumber(), lineNumber);
    }

    @Test
    public void loadMember() throws Exception {
        ObjectNode node = mock(ObjectNode.class);
        query.loadMember("test", node);
        verify(local).loadMember("test", node);
    }

    @Test
    public void occlusionPermitted() throws Exception {
        ObjectNode node = mock(ObjectNode.class);
        when(local.hasMember("test")).thenReturn(true);
        query.loadMember("test", node);
        verify(local).loadMember("test", node);
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
        when(local.hasMember("test")).thenReturn(true);
        when(local.getMember("test")).thenReturn(expected);
        ObjectNode actual = query.getMember("test");
        assertSame(expected, actual);
    }

    @Test
    public void hasMember() throws Exception {
        when(local.hasMember("test")).thenReturn(true);
        assertTrue(query.hasMember("test"));
    }

    @Test
    public void getSymbolTableFor() throws Exception {
        ResolvingSymbolTable expected = mock(ResolvingSymbolTable.class);
        when(symbolTable.getSymbolTable("test", lineNumber)).thenReturn(expected);
        ResolvingSymbolTable actual = query.getSymbolTableFor("test");
        assertSame(expected, actual);
    }

    @Test
    public void getSymbolTable() throws Exception {
        assertSame(symbolTable, query.getSymbolTable());
    }
}