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

package nanoverse.runtime.processes.continuum;

import nanoverse.runtime.layers.continuum.ContinuumLayerScheduler;
import nanoverse.runtime.processes.*;
import org.junit.*;
import org.mockito.InOrder;

import java.util.List;
import java.util.stream.*;

import static org.mockito.Mockito.*;

public class CompositeContinuumProcessTest {

    private CompositeContinuumProcess query;
    private List<ContinuumProcess> children;
    private ContinuumProcess child1, child2;
    private BaseProcessArguments arguments;
    private ContinuumLayerScheduler scheduler;

    @Before
    public void before() throws Exception {
        children = makeChildren();
        arguments = mock(BaseProcessArguments.class);
        scheduler = mock(ContinuumLayerScheduler.class);
        query = new CompositeContinuumProcess(arguments, children.stream(), scheduler);
    }

    private List<ContinuumProcess> makeChildren() {
        child1 = mock(ContinuumProcess.class);
        child2 = mock(ContinuumProcess.class);
        List<ContinuumProcess> ret = Stream.of(child1, child2)
            .collect(Collectors.toList());
        return ret;
    }

    @Test
    public void fire() throws Exception {
        StepState state = mock(StepState.class);
        query.fire(state);

        InOrder inOrder = inOrder(scheduler, child1, child2);
        inOrder.verify(scheduler).hold();
        inOrder.verify(child1).fire(state);
        inOrder.verify(child2).fire(state);
        inOrder.verify(scheduler).release();
        inOrder.verifyNoMoreInteractions();
    }
}