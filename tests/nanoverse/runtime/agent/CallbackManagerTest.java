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

package nanoverse.runtime.agent;

import nanoverse.runtime.cells.MockAgent;
import nanoverse.runtime.control.identifiers.*;
import nanoverse.runtime.geometry.MockGeometry;
import nanoverse.runtime.layers.*;
import nanoverse.runtime.layers.cell.*;
import org.junit.*;
import test.LayerMocks;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by dbborens on 2/21/14.
 */
public class CallbackManagerTest extends LayerMocks {

    private Agent agent;
    private CallbackManager query;

    @Before
    @Override
    public void before() throws Exception {
        super.before();
        agent = mock(Agent.class);
        query = new CallbackManager(agent, layerManager);
    }

    @Test
    public void die() throws Exception {
        Coordinate coord = mock(Coordinate.class);
        when(lookup.getAgentLocation(agent)).thenReturn(coord);
        query.die();
        verify(update).banish(coord);
    }

    @Test
    public void getLayerManager() throws Exception {
        LayerManager actual = query.getLayerManager();
        assertSame(layerManager, actual);
    }

    @Test
    public void getMyLocation() throws Exception {
        Coordinate expected = mock(Coordinate.class);
        when(lookup.getAgentLocation(agent)).thenReturn(expected);

        Coordinate actual = query.getMyLocation();
        assertSame(expected, actual);
    }
}
