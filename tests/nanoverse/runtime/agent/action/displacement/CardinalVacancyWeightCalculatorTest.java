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
import org.junit.*;
import test.LayerMocks;

import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class CardinalVacancyWeightCalculatorTest extends LayerMocks {

    private CardinalVacancyDistanceCalculator calculator;
    private CardinalVacancyWeightCalculator query;

    @Before
    public void before() throws Exception {
        super.before();
        calculator = mock(CardinalVacancyDistanceCalculator.class);
        query = new CardinalVacancyWeightCalculator(calculator, agentLayer);
    }

    @Test
    public void calcWeight() throws Exception {
        Coordinate origin = mock(Coordinate.class);
        Coordinate target = mock(Coordinate.class);
        Coordinate displacement = mock(Coordinate.class);
        when(geometry.getDisplacement(origin, target, Geometry.APPLY_BOUNDARIES))
            .thenReturn(displacement);

        doAnswer(invocation -> {
            HashSet<Coordinate> affectedSites =
                (HashSet<Coordinate>) invocation.getArguments()[2];

            affectedSites.add(origin);
            affectedSites.add(target);

            return null;

        }).when(calculator)
            .calculateDistToVacancy(eq(origin), eq(displacement), any(HashSet.class));

        double actual = query.calcWeight(origin, target);
        assertEquals(0.5, actual, epsilon);
    }
}