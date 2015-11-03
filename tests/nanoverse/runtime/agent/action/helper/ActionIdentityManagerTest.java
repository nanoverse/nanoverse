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
import org.junit.*;
import test.LayerMocks;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ActionIdentityManagerTest extends LayerMocks {

    private Agent self;
    private ActionIdentityManager query;

    @Before
    public void before() throws Exception {
        super.before();
        self = mock(Agent.class);
        query = new ActionIdentityManager(self, agentLayer);
    }

    @Test
    public void getOwnLocation() throws Exception {
        when(viewer.exists(self)).thenReturn(true);

        Coordinate expected = mock(Coordinate.class);
        when(lookup.getAgentLocation(self)).thenReturn(expected);

        Coordinate actual = query.getOwnLocation();
        assertSame(expected, actual);
    }

    @Test
    public void missingSelfReturnsNullCoordinate() throws Exception {
        when(viewer.exists(self)).thenReturn(true);
        Coordinate actual = query.getOwnLocation();
        assertNull(actual);
    }

    @Test
    public void getSelf() throws Exception {
        Agent actual = query.getSelf();
        assertSame(self, actual);
    }


    @Test
    public void selfExists() throws Exception {
        when(viewer.exists(self)).thenReturn(true);
        assertTrue(query.selfExists());
    }
}