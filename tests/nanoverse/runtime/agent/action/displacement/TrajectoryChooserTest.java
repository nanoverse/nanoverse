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

import java.util.Random;
import java.util.function.BiFunction;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

public class TrajectoryChooserTest {
    private Random random;
    private BiFunction<Coordinate, Coordinate, CoordinateTupleOptionMap> mapMaker;
    private TrajectoryChooser query;

    public TrajectoryChooserTest() {
        random = mock(Random.class);
        mapMaker = mock(BiFunction.class);
        query = new TrajectoryChooser(random, mapMaker);
    }
    @Test
    public void testLifeCycle() throws Exception {
        Coordinate currentLocation = mock(Coordinate.class);
        Coordinate currentDisplacement = mock(Coordinate.class);
        CoordinateTupleOptionMap map = mock(CoordinateTupleOptionMap.class);
        when(mapMaker.apply(currentLocation, currentDisplacement))
            .thenReturn(map);

        when(map.getTotalWeight()).thenReturn(10.0);
        when(random.nextDouble()).thenReturn(2.0);

        CoordinateTuple expected = mock(CoordinateTuple.class);
        when(map.selectTarget(20.0)).thenReturn(expected);

        CoordinateTuple actual = query.getNextTuple(currentLocation, currentDisplacement);

        assertSame(expected, actual);
    }
}