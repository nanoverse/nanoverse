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

import static org.mockito.Mockito.*;

public class DisplacementManagerTest {

    private ImaginarySiteCleaner cleaner;
    private ShoveManager shoveManager;
    private VacancyChooser vacancyChooser;
    private DisplacementManager query;
    private Coordinate origin, target;

    @Before
    public void before() throws Exception {
        cleaner = mock(ImaginarySiteCleaner.class);
        shoveManager = mock(ShoveManager.class);
        vacancyChooser = mock(VacancyChooser.class);
        query = new DisplacementManager(cleaner, shoveManager, vacancyChooser);

        origin = mock(Coordinate.class);
        target = mock(Coordinate.class);
    }

    @Test
    public void shove() throws Exception {
        query.shove(origin, target);
        verify(shoveManager).shove(origin, target);
    }

    @Test
    public void removeImaginary() throws Exception {
        query.removeImaginary();
        verify(cleaner).removeImaginary();
    }

    @Test
    public void chooseVacancy() throws Exception {
        query.chooseVacancy(origin);
        verify(vacancyChooser).chooseVacancy(origin);
    }

    @Test
    public void shoveRandom() throws Exception {
        query.shoveRandom(origin);
        verify(shoveManager).shoveRandom(origin);
    }

    @Test
    public void shoveWeighted() throws Exception {
        query.shoveWeighted(origin);
        verify(shoveManager).shoveWeighted(origin);
    }
}