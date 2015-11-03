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

import nanoverse.runtime.control.identifiers.*;
import org.junit.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class TrajectoryLegalityHelperTest {

    private TrajectoryLegalityHelper query;
    private Coordinate c, d;

    @Before
    public void before() throws Exception {
        query = new TrajectoryLegalityHelper();
        c = mock(Coordinate.class);
        d = mock(Coordinate.class);
    }

    @Test
    public void isLegalNull() throws Exception {
        boolean actual = query.isDefinedAndInBounds(null);
        assertFalse(actual);
    }

    @Test
    public void isLegalBeyondBounds() throws Exception {
        when(c.hasFlag(Flags.BEYOND_BOUNDS)).thenReturn(true);
        boolean actual = query.isDefinedAndInBounds(c);
        assertFalse(actual);
    }

    @Test
    public void isLegalInBounds() throws Exception {
        boolean actual = query.isDefinedAndInBounds(c);
        assertTrue(actual);
    }

    @Test(expected = IllegalStateException.class)
    public void handleIllegalPushToBoundaryThrows() throws Exception {
        when(c.hasFlag(Flags.BEYOND_BOUNDS)).thenReturn(true);
        when(d.norm()).thenReturn(1);
        query.doSanityCheck(c, d);
    }

}