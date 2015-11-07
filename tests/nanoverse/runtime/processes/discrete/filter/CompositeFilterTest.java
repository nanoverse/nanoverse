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
import org.junit.Test;

import java.util.List;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;

public class CompositeFilterTest {

    @Test
    public void testApply() throws Exception {
        Filter child = mock(Filter.class);
        Stream<Filter> children = Stream.of(child);
        CompositeFilter query = new CompositeFilter(children);

        List<Coordinate> toFilter = mock(List.class);
        query.apply(toFilter);
        verify(child).apply(toFilter);
    }
}