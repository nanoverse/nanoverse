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
import test.LayerMocks;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class AgentResolverTest extends LayerMocks {

    private AgentResolver query;
    private Coordinate c;

    @Override
    @Before
    public void before() throws Exception {
        super.before();
        c = mock(Coordinate.class);
        query = new AgentResolver(layerManager);
    }

    @Test
    public void resolveAgent() throws Exception {
        when(viewer.isOccupied(c)).thenReturn(true);

        Agent expected = mock(Agent.class);
        when(viewer.getAgent(c)).thenReturn(expected);

        Agent actual = query.resolveAgent(c);
        assertSame(expected, actual);
    }

    @Test(expected = IllegalStateException.class)
    public void resolveNullThrows() throws Exception {
        query.resolveAgent(null);
    }

    @Test
    public void resolveVacantReturnsNull() throws Exception {
        when(viewer.isOccupied(c)).thenReturn(true);
        Agent actual = query.resolveAgent(c);
        assertNull(actual);
    }

    @Test
    public void getLayerManager() throws Exception {
        LayerManager actual = query.getLayerManager();
        assertSame(layerManager, actual);
    }
}