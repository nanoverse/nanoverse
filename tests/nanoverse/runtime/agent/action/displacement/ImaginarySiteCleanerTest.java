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

package nanoverse.runtime.agent.action.displacement;

import nanoverse.runtime.control.identifiers.Coordinate;
import org.junit.*;
import test.LayerMocks;

import java.util.stream.Stream;

import static org.mockito.Mockito.*;

public class ImaginarySiteCleanerTest extends LayerMocks {

    private ImaginarySiteCleaner query;

    @Override
    @Before
    public void before() throws Exception {
        super.before();
        query = new ImaginarySiteCleaner(agentLayer);
    }

    @Test
    public void removeImaginary() throws Exception {
        Coordinate c = mock(Coordinate.class);
        Stream<Coordinate> imaginarySites = Stream.of(c);
        when(viewer.getImaginarySites()).thenReturn(imaginarySites);
        query.removeImaginary();
        verify(update).banish(c);
    }
}