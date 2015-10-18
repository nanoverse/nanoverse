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

import nanoverse.runtime.agent.Agent;
import nanoverse.runtime.cells.*;
import nanoverse.runtime.control.identifiers.*;
import nanoverse.runtime.geometry.MockGeometry;
import nanoverse.runtime.layers.cell.*;
import org.junit.*;
import test.LegacyTest;

import static org.junit.Assert.*;

public class AgentLayerViewerTest extends LegacyTest {

    AgentLayerViewer query;
    MockAgentLayerContent content;
    MockAgentLayerIndices indices;
    MockGeometry geom;
    Coordinate c1, c2;

    @Before
    public void setUp() throws Exception {
        fail("This class actually isn't that bad--most tests can be preserved with new mocks");
//        indices = new MockAgentLayerIndices();
//
//        c1 = new Coordinate2D(1, 0, 0);
//        c2 = new Coordinate2D(5, 0, 0);
//
//        geom = new MockGeometry();
//        Coordinate[] cc = new Coordinate[]{c1, c2};
//        geom.setCanonicalSites(cc);
//        content = new MockAgentLayerContent(geom, indices);
//
//        query = new AgentLayerViewer(content);
    }

    @Test
    public void testGetOccupiedSites() {
        AgentIndex expected = new AgentIndex();

        expected.add(c1);
        expected.add(c2);

        indices.setOccupiedSites(expected);

        assertEquals(query.getOccupiedSites().size(), expected.size());
        assertTrue(query.getOccupiedSites().contains(c1));
        assertTrue(query.getOccupiedSites().contains(c2));
    }

    @Test
    public void testGetAgent() throws Exception {

        Agent agent = new MockAgent();
        content.put(c1, agent);
        assertEquals(agent, query.getAgent(c1));
    }

    @Test
    public void testGetNames() {
        fail("This class actually isn't that bad--most tests can be preserved with new mocks");
//        String[] states = new String[]{"a", "b"};
//        content.setStateVector(states);
//        assertArraysEqual(states, query.getNames(), false);
    }

    @Test
    public void testIsOccupied() {
        AgentIndex occupiedSites = new AgentIndex();
        Coordinate c1 = new Coordinate2D(1, 0, 0);
        Coordinate c2 = new Coordinate2D(5, 0, 0);

        occupiedSites.add(c1);

        indices.setOccupiedSites(occupiedSites);
        assertTrue(query.isOccupied(c1));
        assertFalse(query.isOccupied(c2));
    }
}
