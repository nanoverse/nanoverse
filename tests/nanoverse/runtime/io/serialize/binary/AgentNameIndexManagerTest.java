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

package nanoverse.runtime.io.serialize.binary;

import nanoverse.runtime.layers.cell.AgentLayerViewer;
import org.junit.*;
import test.TestBase;

import java.util.*;
import java.util.stream.Stream;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class AgentNameIndexManagerTest extends TestBase {
    private HashMap<String, Integer> nameToIndexMap;
    private AgentNameIndexManager query;

    @Before
    public void before() throws Exception {
        nameToIndexMap = mock(HashMap.class);
        query = new AgentNameIndexManager(nameToIndexMap);
    }

    @Test
    public void init() throws Exception {
        query.init();
        verify(nameToIndexMap).clear();
    }

    @Test
    public void getNameStream() throws Exception {
        Set<String> keySet = mock(Set.class);
        when(nameToIndexMap.keySet()).thenReturn(keySet);

        Stream<String> expected = mock(Stream.class);
        when(keySet.stream()).thenReturn(expected);

        Stream<String> actual = query.getNameStream();
        assertSame(expected, actual);
    }

    @Test
    public void getIndexNull() throws Exception {
        doGetIndexTest(0, null);
    }

    private void doGetIndexTest(Integer expected, String name) {
        assertEquals(expected, query.getIndex(name));
    }

    @Test
    public void getIndexNew() throws Exception {
        String name = "a";
        when(nameToIndexMap.containsKey(name)).thenReturn(false);
        when(nameToIndexMap.size()).thenReturn(7);
        when(nameToIndexMap.get(name)).thenReturn(8);
        doGetIndexTest(8, name);
    }

    @Test
    public void getIndexExisting() throws Exception {
        String name = "a";
        when(nameToIndexMap.containsKey(name)).thenReturn(true);
        when(nameToIndexMap.get(name)).thenReturn(3);
        doGetIndexTest(3, name);
    }

    @Test
    public void getIndexStream() throws Exception {
        AgentLayerViewer viewer = mock(AgentLayerViewer.class);

        Stream<String> names = Stream.of(null, "a");
        when(viewer.getNames()).thenReturn(names);
        when(nameToIndexMap.containsKey("a")).thenReturn(true);
        when(nameToIndexMap.get("a")).thenReturn(7);

        Stream<Integer> expected = Stream.of(0, 7);
        Stream<Integer> actual = query.getIndexStream(viewer);
        assertStreamsEqual(expected, actual);
    }
}