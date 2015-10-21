/*
 * Copyright (c) 2014, 2015 David Bruce Borenstein and the
 * Trustees of Princeton University.
 *
 * This file is part of the Nanoverse simulation framework
 * (patent pending).
 *
 * This program is free software: you can redistribute it
 * and/or modify it under the terms of the GNU Affero General
 * Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * This program is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 * PURPOSE.  See the GNU Affero General Public License for
 * more details.
 *
 * You should have received a copy of the GNU Affero General
 * Public License along with this program.  If not, see
 * <http://www.gnu.org/licenses/>.
 */

package nanoverse.runtime.agent.action;

import nanoverse.runtime.agent.Agent;
import nanoverse.runtime.agent.action.displacement.DisplacementManager;
import nanoverse.runtime.agent.action.helper.SelfTargetHighlighter;
import nanoverse.runtime.control.identifiers.Coordinate;
import org.junit.*;
import org.mockito.InOrder;

import java.util.Random;

import static org.mockito.Mockito.*;

public class ExpandTest extends ActionTest {
    private DisplacementManager displacementManager;
    private Random random;
    private Expand query;
    private SelfTargetHighlighter stHighlighter;

    @Override
    @Before
    public void before() throws Exception {
        super.before();
        stHighlighter = mock(SelfTargetHighlighter.class);
        displacementManager = mock(DisplacementManager.class);
        random = mock(Random.class);

        query = new Expand(identity, mapper, highlighter, displacementManager, stHighlighter, random);
    }

    @Test
    public void lifeCycle() throws Exception {
        Coordinate target = mock(Coordinate.class);
        when(displacementManager.chooseVacancy(ownLocation)).thenReturn(target);

        Agent child = mock(Agent.class);
        when(selfAgent.copy()).thenReturn(child);
        query.run(caller);

        InOrder inOrder = inOrder(update, displacementManager, stHighlighter);
        inOrder.verify(displacementManager).shove(ownLocation, target);
        inOrder.verify(update).place(child, ownLocation);
        inOrder.verify(displacementManager).removeImaginary();
        inOrder.verify(stHighlighter).highlight(target, ownLocation);
        inOrder.verifyNoMoreInteractions();
    }
}