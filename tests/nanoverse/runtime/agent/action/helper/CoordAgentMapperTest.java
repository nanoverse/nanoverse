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

package nanoverse.runtime.agent.action.helper;

import nanoverse.runtime.agent.Agent;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.layers.LayerManager;
import org.junit.*;

import java.util.function.Supplier;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CoordAgentMapperTest {

    private AgentResolver resolver;
    private CoordAgentMapper query;

    @Before
    public void before() throws Exception {
        resolver = mock(AgentResolver.class);
        query = new CoordAgentMapper(resolver);
    }

    @Test
    public void resolveCaller() throws Exception {
        Coordinate c = mock(Coordinate.class);
        Supplier<Agent> trigger = () -> query.resolveCaller(c);
        doResolveTest(c, trigger);
    }

    private void doResolveTest(Coordinate c, Supplier<Agent> trigger) {
        Agent expected = mock(Agent.class);
        when(resolver.resolveAgent(c)).thenReturn(expected);

        Agent actual = trigger.get();
        assertSame(expected, actual);
    }

    @Test
    public void nullCallerReturnsNullAgent() throws Exception {
        Agent actual = query.resolveAgent(null);
        assertNull(actual);
    }

    @Test
    public void resolveAgent() throws Exception {
        Coordinate c = mock(Coordinate.class);
        Supplier<Agent> trigger = () -> query.resolveAgent(c);
        doResolveTest(c, trigger);
    }

    @Test
    public void getLayerManager() throws Exception {
        LayerManager expected = mock(LayerManager.class);
        when(resolver.getLayerManager()).thenReturn(expected);

        LayerManager actual = query.getLayerManager();
        assertSame(expected, actual);
    }
}