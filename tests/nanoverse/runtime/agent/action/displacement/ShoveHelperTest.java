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

package nanoverse.runtime.agent.action.displacement;

import nanoverse.runtime.control.identifiers.Coordinate;
import org.junit.*;
import test.LayerMocks;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ShoveHelperTest extends LayerMocks {

    private TrajectoryChooser chooser;
    private ShoveHelper query;

    @Override
    @Before
    public void before() throws Exception {
        super.before();
        chooser = mock(TrajectoryChooser.class);
        query = new ShoveHelper(agentLayer, chooser);
    }

    @Test
    public void isOccupied() throws Exception {
        Coordinate c = mock(Coordinate.class);
        when(viewer.isOccupied(c)).thenReturn(true);
        assertTrue(query.isOccupied(c));
    }

    @Test
    public void swap() throws Exception {
        Coordinate p = mock(Coordinate.class);
        Coordinate q = mock(Coordinate.class);
        query.swap(p, q);
        verify(update).swap(p, q);
    }

    @Test
    public void getNextLocation() throws Exception {
        Coordinate currentLocation = mock(Coordinate.class);
        Coordinate currentDisplacement = mock(Coordinate.class);
        CoordinateTuple expected = mock(CoordinateTuple.class);
        when(chooser.getNextTuple(currentLocation, currentDisplacement)).thenReturn(expected);

        CoordinateTuple actual = query.getNextTuple(currentLocation, currentDisplacement);
        assertSame(expected, actual);
    }
}