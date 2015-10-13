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

import junit.framework.TestCase;
import nanoverse.runtime.agent.AbstractAgent;
import nanoverse.runtime.cells.*;
import nanoverse.runtime.control.identifiers.*;
import nanoverse.runtime.geometry.MockGeometry;
import nanoverse.runtime.layers.cell.*;
import org.junit.Test;

public class CellLookupManagerTest extends TestCase {

    @Test
    public void testGetNeighborStates() throws Exception {
        MockGeometry geometry = new MockGeometry();
        MockCellLayerIndices indices = new MockCellLayerIndices();
        Coordinate[] c = new Coordinate[3];
        c[0] = new Coordinate2D(0, 0, 0);
        c[1] = new Coordinate2D(1, 0, 0);
        c[2] = new Coordinate2D(2, 0, 0);
        geometry.setCanonicalSites(c);

        MockCellLayerContent content = new MockCellLayerContent(geometry, indices);

        Coordinate[] neighborhood = new Coordinate[]{c[0], c[2]};
        geometry.setCellNeighbors(c[1], neighborhood);

        AbstractAgent f0 = new MockAgent(4);
        AbstractAgent f2 = new MockAgent(6);

        content.put(c[0], f0);
        content.put(c[2], f2);

        CellLookupManager lookup = new CellLookupManager(geometry, content);
        assertEquals(lookup.getNeighborStates(c[1], true).length, 2);
        assertEquals(lookup.getNeighborStates(c[1], true)[0], 4);
        assertEquals(lookup.getNeighborStates(c[1], true)[1], 6);
    }
}
