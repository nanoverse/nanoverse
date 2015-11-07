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
import nanoverse.runtime.structural.RangeMap;
import org.junit.*;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

public class WeightedShoveTargetChooserTest {

    private WeightedShoveTargetBuilder builder;
    private WeightedShoveTargetChooser query;

    @Before
    public void before() throws Exception {
        builder = mock(WeightedShoveTargetBuilder.class);
        query = new WeightedShoveTargetChooser(builder);
    }

    @Test
    public void choose() throws Exception {
        Coordinate origin = mock(Coordinate.class);
        RangeMap<Coordinate> rangeMap = mock(RangeMap.class);
        when(builder.buildRangeMap(origin)).thenReturn(rangeMap);

        Coordinate expected = mock(Coordinate.class);
        when(builder.chooseTarget(rangeMap)).thenReturn(expected);

        Coordinate actual = query.choose(origin);
        assertSame(expected, actual);
    }
}