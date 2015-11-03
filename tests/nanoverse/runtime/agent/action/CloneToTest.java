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

import java.util.*;
import java.util.stream.*;

import static org.mockito.Mockito.*;

public class CloneToTest extends ActionTest {

    private CloneToChildPlacer childPlacer;
    private Random random;
    private CloneTo query;

    @Override
    @Before
    public void before() throws Exception {
        super.before();
        childPlacer = mock(CloneToChildPlacer.class);
        random = mock(Random.class);
        query = new CloneTo(identity, mapper, highlighter, targetRule,
            childPlacer, random);
    }

    @Test
    public void run() throws Exception {
        Coordinate a = mock(Coordinate.class);
        Coordinate b = mock(Coordinate.class);
        Agent aChild = mock(Agent.class);
        Agent bChild = mock(Agent.class);

        when(selfAgent.copy()).thenReturn(aChild, bChild);
        List<Coordinate> targets = Stream.of(a, b).collect(Collectors.toList());

        when(targetRule.report(callerAgent)).thenReturn(targets);
        query.run(caller);

        verify(childPlacer).place(ownLocation, a, aChild);
        verify(childPlacer).place(ownLocation, b, bChild);
    }
}