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
import nanoverse.runtime.processes.*;
import org.junit.*;
import test.LayerMocks;

import java.util.stream.Stream;

import static org.mockito.Mockito.*;

public class TriggerProcessTest extends LayerMocks {

    private BaseProcessArguments arguments;
    private AgentProcessArguments cpArguments;
    private String behaviorName;
    private TriggerProcessTargetResolver targetResolver;
    private Agent target;
    private TriggerProcess query;
    private StepState state;

    @Override
    @Before
    public void before() throws Exception {
        super.before();
        arguments = mock(BaseProcessArguments.class);
        cpArguments = mock(AgentProcessArguments.class);
        behaviorName = "test";
        targetResolver = mock(TriggerProcessTargetResolver.class);

        when(arguments.getLayerManager()).thenReturn(layerManager);
        target = mock(Agent.class);
        Stream<Agent> stream = Stream.of(target);
        when(targetResolver.resolveTargets()).thenReturn(stream);

        state = mock(StepState.class);

        query = new TriggerProcess(arguments, cpArguments, behaviorName, targetResolver);
    }

    @Test
    public void testLifeCycle() throws Exception {
        when(viewer.exists(target)).thenReturn(true);

        query.target(null);
        query.fire(state);
        verify(target).trigger(behaviorName, null);
    }

    @Test
    public void removedAgentExcluded() throws Exception {
        when(viewer.exists(target)).thenReturn(false);

        query.target(null);
        query.fire(state);
        verify(target, never()).trigger(behaviorName, null);
    }
}