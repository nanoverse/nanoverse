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
import nanoverse.runtime.agent.action.helper.SelfTargetHighlighter;
import nanoverse.runtime.control.identifiers.Coordinate;
import org.junit.*;
import org.mockito.InOrder;

import static org.mockito.Mockito.*;

public class PreferentialExpansionManagerTest extends ActionTest {

    private DisplacementManager displacementManager;
    private ExpandToTargetManager expansionManager;
    private CloneToVacancyHelper vacancyHelper;
    private PreferentialExpansionManager query;
    private SelfTargetHighlighter stHighlighter;

    @Override
    @Before
    public void before() throws Exception {
        super.before();
        stHighlighter = mock(SelfTargetHighlighter.class);
        displacementManager = mock(DisplacementManager.class);
        expansionManager = mock(ExpandToTargetManager.class);
        vacancyHelper = mock(CloneToVacancyHelper.class);
        query = new PreferentialExpansionManager(displacementManager, stHighlighter, expansionManager, identity, vacancyHelper);
    }

    @Test
    public void lifeCycle() throws Exception {
        // The parent agent will get moved in the course of the shoving process
        DisplacementOption option = mock(DisplacementOption.class);
        Coordinate target = mock(Coordinate.class);
        when(expansionManager.getShortestOption(target)).thenReturn(option);

        Coordinate newlyVacant = mock(Coordinate.class);
        when(option.getOccupied()).thenReturn(newlyVacant);

        Agent child = mock(Agent.class);
        when(selfAgent.copy()).thenReturn(child);

        query.preferentialExpand(target);

        InOrder inOrder = inOrder(expansionManager, vacancyHelper, displacementManager, stHighlighter);
        inOrder.verify(expansionManager).doShove(option);
        inOrder.verify(vacancyHelper).cloneToVacancy(newlyVacant);
        inOrder.verify(displacementManager).removeImaginary();
        inOrder.verify(stHighlighter).highlight(target, ownLocation);
        inOrder.verifyNoMoreInteractions();
    }
}