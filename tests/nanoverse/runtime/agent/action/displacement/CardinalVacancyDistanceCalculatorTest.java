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

import java.util.HashSet;
import java.util.function.BiFunction;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

public class CardinalVacancyDistanceCalculatorTest {

    private ShoveHelper shoveHelper;
    private BiFunction<Coordinate, Coordinate, Boolean> baseCaseFunction;
    private CardinalVacancyDistanceCalculator query;
    private Coordinate currentLocation, d;
    private HashSet<Coordinate> sites;

    @Before
    public void before() throws Exception {
        shoveHelper = mock(ShoveHelper.class);
        baseCaseFunction = mock(BiFunction.class);
        query = new CardinalVacancyDistanceCalculator(shoveHelper, baseCaseFunction);
        currentLocation = mock(Coordinate.class);
        d = mock(Coordinate.class);
        sites = new HashSet<>();
    }

    @Test
    public void baseCase() throws Exception {
        when(baseCaseFunction.apply(any(), any())).thenReturn(true);
        query.calculateDistToVacancy(currentLocation, d, sites);
        verifyZeroInteractions(shoveHelper);
    }

}