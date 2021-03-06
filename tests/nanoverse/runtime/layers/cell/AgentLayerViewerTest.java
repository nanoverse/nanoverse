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

package nanoverse.runtime.layers.cell;

import nanoverse.runtime.agent.Agent;
import nanoverse.runtime.control.identifiers.Coordinate;
import org.junit.*;
import test.TestBase;

import java.util.Set;
import java.util.stream.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class AgentLayerViewerTest extends TestBase {

    private AgentLayerContent content;
    private AgentLayerViewer query;

    @Before
    public void before() throws Exception {
        this.content = mock(AgentLayerContent.class);
        query = new AgentLayerViewer(content);
    }

    @Test
    public void getOccupiedSites() throws Exception {
        Coordinate c = mock(Coordinate.class);
        Set<Coordinate> cc = Stream.of(c).collect(Collectors.toSet());
        when(content.getOccupiedSites()).thenReturn(cc);

        Stream<Coordinate> expected = Stream.of(c);
        Stream<Coordinate> actual = query.getOccupiedSites();
        assertStreamsEqual(expected, actual);
    }

    @Test
    public void getNameMapViewer() throws Exception {
        NameMapViewer expected = mock(NameMapViewer.class);
        when(content.getNameMap()).thenReturn(expected);

        NameMapViewer actual = query.getNameMapViewer();
        assertSame(expected, actual);
    }

    @Test
    public void exists() throws Exception {
        Agent a = mock(Agent.class);
        when(content.isIndexed(a)).thenReturn(true);
        assertTrue(query.exists(a));
    }

    @Test
    public void getImaginarySites() throws Exception {
        Stream<Coordinate> expected = mock(Stream.class);
        when(content.getImaginarySites()).thenReturn(expected);
        Stream<Coordinate> actual = query.getImaginarySites();
        assertSame(expected, actual);
    }

    @Test
    public void getNameVacant() throws Exception {
        Coordinate c = mock(Coordinate.class);
        when(content.isOccupied(c)).thenReturn(false);
        assertNull(query.getName(c));
    }

    @Test
    public void getNameOccupied() throws Exception {
        Coordinate c = mock(Coordinate.class);
        when(content.isOccupied(c)).thenReturn(true);

        Agent a = mock(Agent.class);
        when(content.get(c)).thenReturn(a);

        String expected = "test";
        when(a.getName()).thenReturn(expected);

        String actual = query.getName(c);
        assertEquals(expected, actual);
    }

    @Test
    public void getAgent() throws Exception {
        Coordinate c = mock(Coordinate.class);
        Agent expected = mock(Agent.class);
        when(content.get(c)).thenReturn(expected);

        Agent actual = query.getAgent(c);
        assertSame(expected, actual);
    }

    @Test
    public void isOccupied() throws Exception {
        Coordinate c = mock(Coordinate.class);
        when(content.isOccupied(c)).thenReturn(true);
        assertTrue(query.isOccupied(c));
    }

    @Test
    public void getNames() throws Exception {
        String[] namesArr = new String[] {"a", "b"};
        when(content.getNames()).thenReturn(namesArr);

        Stream<String> expected = Stream.of("a", "b");
        Stream<String> actual = query.getNames();
        assertStreamsEqual(expected, actual);
    }
}