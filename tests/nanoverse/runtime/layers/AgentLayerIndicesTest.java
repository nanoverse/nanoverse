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

package nanoverse.runtime.layers;

import nanoverse.runtime.cells.MockAgent;
import nanoverse.runtime.control.identifiers.*;
import nanoverse.runtime.layers.cell.AgentLayerIndices;
import nanoverse.runtime.structural.CanonicalAgentMap;
import org.junit.*;
import test.LegacyTest;

import static org.junit.Assert.*;

public class AgentLayerIndicesTest extends LegacyTest {

    private Coordinate c;
    private AgentLayerIndices query;

    @Before
    public void setUp() throws Exception {
        c = new Coordinate2D(0, 0, 0);
        query = new AgentLayerIndices();
    }

    @Test
    public void testNulltoNull() {
        query.refresh(c, null, null);
        assertFalse(query.isOccupied(c));
    }

    @Test
    public void testNonNullToNull() {
        MockAgent cell = new MockAgent();
        query.refresh(c, null, cell);

        query.refresh(c, cell, null);
        assertFalse(query.isIndexed(cell));
        assertFalse(query.isOccupied(c));
    }

    @Test
    public void testNullToNonDivisible() {
        MockAgent cell = new MockAgent();
        cell.setName("test");

        query.refresh(c, null, cell);

        assertTrue(query.isIndexed(cell));
        assertEquals(c, query.locate(cell));
        assertTrue(query.isOccupied(c));
        assertEquals((Integer) 1, query.getNameMap().getCount("test"));
    }

    @Test
    public void testNonNullTransition() {
        MockAgent dAgent = new MockAgent();
        dAgent.setName("5");

        MockAgent nAgent = new MockAgent();
        nAgent.setName("2");

        query.refresh(c, null, nAgent);
        assertTrue(query.isIndexed(nAgent));
        assertFalse(query.isIndexed(dAgent));
        assertEquals((Integer) 0, query.getNameMap().getCount("5"));
        assertEquals((Integer) 1, query.getNameMap().getCount("2"));

        query.refresh(c, nAgent, dAgent);
        assertFalse(query.isIndexed(nAgent));
        assertTrue(query.isIndexed(dAgent));
        assertEquals((Integer) 1, query.getNameMap().getCount("5"));
        assertEquals((Integer) 0, query.getNameMap().getCount("2"));
    }

    @Test
    public void testClone() {
        CanonicalAgentMap map = new CanonicalAgentMap(1);
        map.put(c, null);
        Object clone = query.clone(map);
        assertEquals(query, clone);
        assertFalse(query == clone);
    }
}
