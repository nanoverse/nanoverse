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

import nanoverse.runtime.agent.Agent;
import nanoverse.runtime.agent.action.ActionTest;
import nanoverse.runtime.control.identifiers.Coordinate;
import org.junit.*;

import static org.mockito.Mockito.*;

public class CloneToVacancyHelperTest extends ActionTest {

    private CloneToVacancyHelper query;

    @Override
    @Before
    public void before() throws Exception {
        super.before();
        query = new CloneToVacancyHelper(identity, mapper);
    }

    @Test
    public void cloneToVacancy() throws Exception {
        Agent child = mock(Agent.class);
        when(selfAgent.copy()).thenReturn(child);

        Coordinate target = mock(Coordinate.class);
        query.cloneToVacancy(target);
        verify(update).place(child, target);
    }
}