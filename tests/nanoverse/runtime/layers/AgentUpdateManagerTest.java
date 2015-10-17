/*
 * Copyright (c) 2014, 2015 David Bruce Borenstein and the
 * Trustees of Princeton University.
 *
 * This file is part of the Nanoverse simulation framework
 * (patent pending).
 *
 * This program is free software: you can redistribute it
 * and/or modify it under the terms of the GNU Affero General
 * Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * This program is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 * PURPOSE.  See the GNU Affero General Public License for
 * more details.
 *
 * You should have received a copy of the GNU Affero General
 * Public License along with this program.  If not, see
 * <http://www.gnu.org/licenses/>.
 */

package nanoverse.runtime.layers;

import nanoverse.runtime.agent.AbstractAgent;
import nanoverse.runtime.cells.*;
import nanoverse.runtime.control.identifiers.*;
import nanoverse.runtime.geometry.MockGeometry;
import nanoverse.runtime.layers.cell.*;
import org.junit.*;
import test.LegacyTest;

import static org.junit.Assert.*;

public class AgentUpdateManagerTest extends LegacyTest {
    private MockGeometry geom;
    private MockAgentLayerIndices indices;
    private MockAgentLayerContent content;
    private Coordinate o, t;
    private AgentUpdateManager query;

    @Before
    public void setUp() throws Exception {
        geom = new MockGeometry();
        o = new Coordinate2D(0, 0, 0);
        t = new Coordinate2D(1, 0, 0);
        Coordinate[] cc = new Coordinate[]{o, t};
        geom.setCanonicalSites(cc);

        indices = new MockAgentLayerIndices();
        content = new MockAgentLayerContent(geom, indices);
        query = new AgentUpdateManager(content);
    }

    @Test
    public void testDivideTo() throws Exception {
        MockAgent cell = new MockAgent();
        query.place(cell, o);
        MockAgent child = new MockAgent();
        cell.setChild(child);
        assertNull(indices.getLastPrevious());
        assertEquals(cell, indices.getLastCurrent());
        assertEquals(o, indices.getLastCoord());
        assertTrue(content.has(o));

        query.divideTo(o, t);
        assertNull(indices.getLastPrevious());
        assertEquals(t, indices.getLastCoord());
        assertTrue(content.has(t));

        assertEquals(t, content.locate(child));
    }

    @Test
    public void testDivide() throws Exception {
        MockAgent cell = new MockAgent();
        MockAgent child = new MockAgent();
        cell.setChild(child);
        query.place(cell, o);

        AbstractAgent actual = query.divide(o);
        AbstractAgent expected = child;
        assertEquals(expected, actual);
    }


    @Test
    public void testBanish() throws Exception {
        AbstractAgent agent = new MockAgent();
        content.put(o, agent);

        assertTrue(content.has(o));
        query.banish(o);
        assertFalse(content.has(o));
    }

    @Test
    public void testMove() throws Exception {
        AbstractAgent agent = new MockAgent();
        query.place(agent, o);
        assertNull(indices.getLastPrevious());
        assertEquals(agent, indices.getLastCurrent());
        assertEquals(o, indices.getLastCoord());
        assertTrue(content.has(o));

        query.move(o, t);

        assertFalse(content.has(o));
        assertEquals(agent, indices.getLastCurrent());
        assertEquals(t, indices.getLastCoord());
        assertTrue(content.has(t));
    }

    @Test
    public void testSwap() throws Exception {
        AbstractAgent abstractAgent1 = new MockAgent("1");
        AbstractAgent abstractAgent2 = new MockAgent("2");
        query.place(abstractAgent1, o);
        query.place(abstractAgent2, t);

        assertEquals(o, content.locate(abstractAgent1));
        assertEquals(t, content.locate(abstractAgent2));

        assertEquals(content.get(o).getName(), "1");
        assertEquals(content.get(t).getName(), "2");

        query.swap(o, t);

        assertEquals(o, content.locate(abstractAgent2));
        assertEquals(t, content.locate(abstractAgent1));

        assertEquals(content.get(o).getName(), "2");
        assertEquals(content.get(t).getName(), "1");

    }

    @Test
    public void testPlace() throws Exception {
        AbstractAgent agent = new MockAgent();

        assertFalse(content.has(o));
        query.place(agent, o);
        assertTrue(content.has(o));
    }

}
