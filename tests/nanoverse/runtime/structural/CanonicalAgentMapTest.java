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

package nanoverse.runtime.structural;

import nanoverse.runtime.cells.MockAgent;
import nanoverse.runtime.control.identifiers.*;
import org.junit.*;
import test.LegacyTest;

import static org.junit.Assert.*;

/**
 * Created by David B Borenstein on 4/11/14.
 */
public class CanonicalAgentMapTest extends LegacyTest {
    private CanonicalAgentMap query;
    private Coordinate c, nc, d;
    private MockAgent cell;

    @Before
    public void setUp() throws Exception {
        query = new CanonicalAgentMap();
        c = new Coordinate2D(0, 0, 0);
        nc = new Coordinate2D(0, 0, Flags.BOUNDARY_APPLIED);
        d = new Coordinate3D(0, 0, 0, 0);

        cell = new MockAgent();
    }

    @Test
    public void testPutGet() throws Exception {
        query.put(c, cell);
        assertEquals(cell, query.get(c));
        assertEquals(cell, query.get(nc));
        assertEquals(null, query.get(d));
    }

    @Test
    public void testContainsKey() throws Exception {
        query.put(c, cell);
        assertTrue(query.containsKey(c));
        assertTrue(query.containsKey(nc));
        assertFalse(query.containsKey(d));
    }
}
