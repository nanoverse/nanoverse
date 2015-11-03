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

import java.util.*;
import java.util.stream.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class HasNeighborsFilterTest extends LayerMocks {

    private HasNeighborsFilter query;
    private Coordinate c;
    private List<Coordinate> cList;

    @Override
    @Before
    public void before() throws Exception {
        super.before();
        query = new HasNeighborsFilter(agentLayer);

        c = mock(Coordinate.class);
        cList = Stream.of(c).collect(Collectors.toList());
    }

    @Test
    public void hasNeighborsIncludes() throws Exception {
        // Filter works by counting non-empty neighbor names
        Stream<String> names = Stream.of("a");
        when(lookup.getNeighborNames(c, true)).thenReturn(names);

        List<Coordinate> actual = query.apply(cList);
        assertEquals(cList, actual);
    }

    @Test
    public void noNeighborsExcludes() throws Exception {
        Stream<String> names = Stream.empty();
        when(lookup.getNeighborNames(c, true)).thenReturn(names);

        List<Coordinate> expected = new ArrayList<>();
        List<Coordinate> actual = query.apply(cList);
        assertEquals(expected, actual);
    }
}