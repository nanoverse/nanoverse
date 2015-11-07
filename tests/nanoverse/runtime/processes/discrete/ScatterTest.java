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

package nanoverse.runtime.processes.discrete;

import nanoverse.runtime.agent.Agent;
import nanoverse.runtime.control.arguments.*;
import nanoverse.runtime.control.halt.LatticeFullEvent;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.processes.BaseProcessArguments;
import org.junit.*;
import test.LayerMocks;

import java.util.*;
import java.util.stream.*;

import static org.mockito.Mockito.*;

public class ScatterTest extends LayerMocks {

    private static final int MAX_TARGETS = 1;

    private AgentDescriptor cellDescriptor;
    private ScatterTargetManager targetManager;
    private BaseProcessArguments arguments;
    private AgentProcessArguments cpArguments;
    private Scatter query;
    private Agent agent;

    @Override
    @Before
    public void before() throws Exception {
        super.before();
        cellDescriptor = mock(AgentDescriptor.class);
        targetManager = mock(ScatterTargetManager.class);

        arguments = mock(BaseProcessArguments.class);
        when(arguments.getLayerManager()).thenReturn(layerManager);

        cpArguments = mock(AgentProcessArguments.class);

        query = new Scatter(arguments, cpArguments, cellDescriptor, targetManager);

        IntegerArgument maxTargets = mock(IntegerArgument.class);
        when(maxTargets.next()).thenReturn(MAX_TARGETS);
        when(cpArguments.getMaxTargets()).thenReturn(maxTargets);

        agent = mock(Agent.class);
        when(cellDescriptor.next()).thenReturn(agent);
    }

    @Test
    public void lifeCycle() throws Exception {
        Coordinate c = mock(Coordinate.class);
        List<Coordinate> targets = Stream.of(c).collect(Collectors.toList());
        when(targetManager.getTargets(MAX_TARGETS)).thenReturn(targets);
        query.target(null);
        query.fire(null);
        verify(update).place(agent, c);
    }

    @Test(expected = LatticeFullEvent.class)
    public void emptyListThrowsHalt() throws Exception {
        List<Coordinate> empty = new ArrayList<>(0);
        when(targetManager.getTargets(MAX_TARGETS)).thenReturn(empty);
        query.target(null);
        query.fire(null);
    }


}