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
import test.TestBase;

import java.util.*;
import java.util.stream.*;

import static org.mockito.Mockito.*;

public class NotFilterTest extends TestBase {

    private Filter toInvert;
    private Coordinate c;
    private NotFilter query;
    private List<Coordinate> cList;

    @Before
    public void before() throws Exception {
        toInvert = mock(Filter.class);
        c = mock(Coordinate.class);
        cList = Stream.of(c).collect(Collectors.toList());
        query = new NotFilter(toInvert);
    }

    @Test
    public void negatesPositive() throws Exception {
        configureOutput(true);
        Stream<Coordinate> expected = Stream.empty();
        Stream<Coordinate> actual = query.apply(cList).stream();
        assertStreamsEqual(expected, actual);
    }

    private void configureOutput(boolean include) {
        List<Coordinate> output = include
            ? cList
            : new ArrayList<>();

        when(toInvert.apply(any())).thenReturn(output);
    }

    @Test
    public void negatesNegative() throws Exception {
        configureOutput(false);
        Stream<Coordinate> expected = Stream.of(c);
        Stream<Coordinate> actual = query.apply(cList).stream();
        assertStreamsEqual(expected, actual);
    }
}