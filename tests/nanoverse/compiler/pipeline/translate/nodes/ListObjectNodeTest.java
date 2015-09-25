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

package nanoverse.compiler.pipeline.translate.nodes;

import nanoverse.compiler.pipeline.translate.symbol.ListSymbolTable;
import org.junit.*;

import java.util.stream.Stream;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ListObjectNodeTest {

    private ListSymbolTable symbolTable;
    private LocalContextList local;

    private ListObjectNode query;
    @Before
    public void before() throws Exception {
        symbolTable = mock(ListSymbolTable.class);
        local = mock(LocalContextList.class);
        query = new ListObjectNode(symbolTable, local);
    }

    @Test
    public void getMemberStream() throws Exception {
        Stream<ObjectNode> expected = mock(Stream.class);
        when(local.getMembers()).thenReturn(expected);
        Stream<ObjectNode> actual = query.getMemberStream();
        assertSame(expected, actual);
    }

    @Test
    public void getMember() throws Exception {
        ObjectNode expected = mock(ObjectNode.class);
        when(local.get(17)).thenReturn(expected);
        when(local.size()).thenReturn(25);
        ObjectNode actual = query.getMember(17);
        assertSame(expected, actual);
    }

    @Test(expected = IllegalStateException.class)
    public void getOutOfBoundsThrows() throws Exception {
        when(local.size()).thenReturn(25);
        query.getMember(25);
    }
    @Test
    public void loadMember() throws Exception {
        ObjectNode node = mock(ObjectNode.class);
        query.loadMember(node);
        verify(local).loadMember(node);
    }

    @Test
    public void size() throws Exception {
        when(local.size()).thenReturn(25);
        assertEquals(25, query.size());
    }

    @Test
    public void getSymbolTable() throws Exception {
        assertSame(symbolTable, query.getSymbolTable());
    }

    @Test
    public void getInstantiatingClass() throws Exception {
        Class expected = ListObjectNodeTest.class;
        when(symbolTable.getBroadClass()).thenReturn(expected);
        Class actual = query.getInstantiatingClass();
        assertSame(expected, actual);
    }
}