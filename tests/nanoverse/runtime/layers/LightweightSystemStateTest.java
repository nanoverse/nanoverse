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
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.geometry.Geometry;
import nanoverse.runtime.io.deserialize.MockCoordinateDeindexer;
import nanoverse.runtime.layers.cell.AgentLayer;
import org.junit.*;

import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by dbborens on 3/26/14.
 * <p>
 * TODO This class should be totally rewritten now that LSS has been refactored
 */
public class LightweightSystemStateTest extends SystemStateTest {

    private LightweightSystemState query;
    private Coordinate[] canonicals;
    private String[] nameVector = new String[]{"1", "0", "2", "3", "2"};
    private Geometry g;

    @Before
    public void setUp() throws Exception {
        MockCoordinateDeindexer deindexer = new MockCoordinateDeindexer();
        g = buildMockGeometry();
        canonicals = g.getCanonicalSites();
        deindexer.setUnderlying(canonicals);
        query = new LightweightSystemState(g);
        query.initAgentLayer(nameVector);

    }

    @Override
    @Test
    public void testGetState() throws Exception {
        for (int i = 0; i < 4; i++) {
            Coordinate coord = canonicals[i];
            String expected = nameVector[i];
            String actual = query.getLayerManager().getAgentLayer().getViewer().getName(coord);
            assertEquals(expected, actual);
        }
    }

    @Override
    @Test
    public void testGetTime() throws Exception {
        double expected = 0.7;
        query.setTime(expected);
        double actual = query.getTime();
        assertEquals(expected, actual, epsilon);
    }

    @Override
    @Test
    public void testGetFrame() throws Exception {
        int expected = 7;
        query.setFrame(expected);
        int actual = query.getFrame();
        assertEquals(expected, actual);
    }

    @Override
    @Test
    public void testIsHighlighted() throws Exception {
        int channelId = 0;
        Set<Coordinate> expected = new HashSet<>(1);
        expected.add(canonicals[2]);
        query.setHighlights(channelId, expected);

        assertTrue(query.isHighlighted(channelId, canonicals[2]));
        assertFalse(query.isHighlighted(channelId, canonicals[0]));
    }

    @Test
    public void testGetLayerManager() throws Exception {
        MockLayerManager expected = new MockLayerManager();

        AgentLayer cellLayer = new AgentLayer(g);
//        LightweightSoluteLayer soluteLayer = new LightweightSoluteLayer(g, expected, id);
        expected.setAgentLayer(cellLayer);
//        expected.addSoluteLayer(id, soluteLayer);

        for (int i = 0; i < g.getCanonicalSites().length; i++) {
            Coordinate c = g.getCanonicalSites()[i];
            String name = nameVector[i];

            if (name != null) {
                Agent cell = new Agent(expected, name, null);
                cellLayer.getUpdateManager().place(cell, c);
            }
        }

        LayerManager actual = query.getLayerManager();
        assertEquals(expected, actual);
    }
}
