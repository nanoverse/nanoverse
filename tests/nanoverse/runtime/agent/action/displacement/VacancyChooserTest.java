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

import nanoverse.runtime.control.halt.LatticeFullEvent;
import nanoverse.runtime.control.identifiers.Coordinate;
import org.junit.*;
import test.LayerMocks;

import java.util.Random;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

public class VacancyChooserTest extends LayerMocks {

    private Random random;
    private VacancyChooser query;
    private Coordinate origin;

    @Override
    @Before
    public void before() throws Exception {
        super.before();
        random = mock(Random.class);
        query = new VacancyChooser(agentLayer, random);
        origin = mock(Coordinate.class);
    }

    @Test
    public void chooseVacancy() throws Exception {
        Coordinate expected = mock(Coordinate.class);
        Coordinate[] targets = new Coordinate[]{null, expected};
        when(random.nextInt(2)).thenReturn(1);
        when(lookup.getNearestVacancies(origin, -1)).thenReturn(targets);
        Coordinate actual = query.chooseVacancy(origin);
        assertSame(expected, actual);
    }

    @Test(expected = LatticeFullEvent.class)
    public void noTargetsHalts() throws Exception {
        when(lookup.getNearestVacancies(origin, -1)).thenReturn(new Coordinate[0]);
        query.chooseVacancy(origin);
    }
}