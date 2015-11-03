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

package nanoverse.runtime.agent.action;

import nanoverse.runtime.agent.Agent;
import nanoverse.runtime.control.identifiers.Coordinate;
import org.junit.*;
import org.mockito.InOrder;

import java.util.Random;

import static org.mockito.Mockito.*;

/**
 * Created by dbborens on 3/6/14.
 */
public class StochasticChoiceTest extends ActionTest {

    private DynamicActionRangeMap chooser;
    private Random random;
    private Action action;
    private StochasticChoice query;
    private Coordinate coord;

    @Before
    public void init() throws Exception {
        coord = mock(Coordinate.class);
        action = mock(Action.class);

        random = mock(Random.class);
        when(random.nextDouble()).thenReturn(0.5);

        chooser = mock(DynamicActionRangeMap.class);
        when(chooser.getTotalWeight()).thenReturn(5.0);
        when(chooser.selectTarget(2.5)).thenReturn(action);
        when(chooser.clone(any())).thenReturn(chooser);
        query = new StochasticChoice(identity, mapper, highlighter, chooser, random);
    }

    @Test
    public void runCalculatesCorrectValue() throws Exception {
        query.run(coord);
        verify(chooser).selectTarget(2.5);
    }

    @Test
    public void runRefreshesChooserBeforeUsing() throws Exception {
        InOrder inOrder = inOrder(chooser);
        query.run(coord);
        inOrder.verify(chooser).refresh();
        inOrder.verify(chooser).getTotalWeight();
    }

    @Test
    public void runTriggersSelectionWithCaller() throws Exception {
        doTriggerTest(query);
    }

    private void doTriggerTest(Action target) throws Exception {
        target.run(coord);
        verify(action).run(coord);
    }

    @Test
    public void cloneBehavesAsExpected() throws Exception {
        Agent child = mock(Agent.class);
        Action clone = query.copy(child);
        verify(chooser).clone(child);
        doTriggerTest(clone);
    }
}
