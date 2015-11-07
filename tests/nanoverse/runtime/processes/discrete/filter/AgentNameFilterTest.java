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

package nanoverse.runtime.processes.discrete.filter;

import nanoverse.runtime.control.identifiers.Coordinate;
import org.junit.*;
import test.LayerMocks;

import java.util.List;
import java.util.stream.*;

import static org.mockito.Mockito.*;

public class AgentNameFilterTest extends LayerMocks {

    private String name;
    private AgentNameFilter query;
    private Coordinate c;
    private List<Coordinate> toFilter;

    @Override
    @Before
    public void before() throws Exception {
        super.before();
        name = "correct";
        query = new AgentNameFilter(agentLayer, name);
        c = mock(Coordinate.class);
        toFilter = Stream.of(c).collect(Collectors.toList());
    }

    @Test
    public void nullCoordinateExcluded() throws Exception {
        when(viewer.isOccupied(c)).thenReturn(false);
        checkResults(false);
    }

    private void checkResults(boolean retained) {
        Stream<Coordinate> expected = retained
            ? toFilter.stream()
            : Stream.empty();

        Stream actual = query.apply(toFilter).stream();
        assertStreamsEqual(expected, actual);
    }

    @Test
    public void wrongNameExcluded() throws Exception {
        when(viewer.isOccupied(c)).thenReturn(true);
        when(viewer.getName(c)).thenReturn("something unexpected");
        checkResults(false);
    }

    @Test
    public void rightNameIncluded() throws Exception {
        when(viewer.isOccupied(c)).thenReturn(true);
        when(viewer.getName(c)).thenReturn(name);
        checkResults(true);
    }
}