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
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AgentLookupManagerTest {

    @Test
    public void testGetNeighborNames() throws Exception {
        MockGeometry geometry = new MockGeometry();
        MockAgentLayerIndices indices = new MockAgentLayerIndices();
        Coordinate[] c = new Coordinate[3];
        c[0] = new Coordinate2D(0, 0, 0);
        c[1] = new Coordinate2D(1, 0, 0);
        c[2] = new Coordinate2D(2, 0, 0);
        geometry.setCanonicalSites(c);

        MockAgentLayerContent content = new MockAgentLayerContent(geometry, indices);

        Coordinate[] neighborhood = new Coordinate[]{c[0], c[2]};
        geometry.setAgentNeighbors(c[1], neighborhood);

        Agent f0 = new MockAgent("4");
        Agent f2 = new MockAgent("6");

        content.put(c[0], f0);
        content.put(c[2], f2);

        AgentLookupManager lookup = new AgentLookupManager(geometry, content);
        assertEquals(lookup.getNeighborNames(c[1], true).length, 2);
        assertEquals(lookup.getNeighborNames(c[1], true)[0], "4");
        assertEquals(lookup.getNeighborNames(c[1], true)[1], "6");
    }
}
