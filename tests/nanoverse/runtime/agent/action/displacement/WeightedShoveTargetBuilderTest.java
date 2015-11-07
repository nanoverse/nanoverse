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
import nanoverse.runtime.geometry.Geometry;
import nanoverse.runtime.structural.RangeMap;
import org.junit.*;
import test.LayerMocks;

import java.util.Random;
import java.util.stream.Stream;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class WeightedShoveTargetBuilderTest extends LayerMocks {

    private Random random;
    private CardinalVacancyWeightCalculator calculator;
    private WeightedShoveTargetBuilder query;

    @Override
    @Before
    public void before() throws Exception {
        super.before();
        random = mock(Random.class);
        calculator = mock(CardinalVacancyWeightCalculator.class);
        query = new WeightedShoveTargetBuilder(agentLayer, random, calculator);
    }

    @Test
    public void buildRangeMap() throws Exception {
        Coordinate neighbor = mock(Coordinate.class);
        Coordinate origin = mock(Coordinate.class);
        when(calculator.calcWeight(origin, neighbor)).thenReturn(2.0);

        Coordinate[] cc = new Coordinate[]{neighbor};
        when(geometry.getNeighbors(origin, Geometry.APPLY_BOUNDARIES)).thenReturn(cc);

        // I don't like this--I should be able to mock this out
        RangeMap<Coordinate> result = query.buildRangeMap(origin);
        assertEquals(2.0, result.getTotalWeight(), epsilon);

        Stream<Coordinate> actual = result.getKeys().stream();
        Stream<Coordinate> expected = Stream.of(neighbor);
        assertStreamsEqual(expected, actual);
    }

    @Test
    public void chooseTarget() throws Exception {
        RangeMap<Coordinate> rangeMap = mock(RangeMap.class);
        when(rangeMap.getTotalWeight()).thenReturn(1.0);
        when(random.nextDouble()).thenReturn(1.0);

        Coordinate expected = mock(Coordinate.class);
        when(rangeMap.selectTarget(1.0)).thenReturn(expected);

        Coordinate actual = query.chooseTarget(rangeMap);
        assertSame(expected, actual);
    }
}