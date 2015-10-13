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

package nanoverse.runtime.agent;

import nanoverse.runtime.cells.MockAgent;
import nanoverse.runtime.control.identifiers.*;
import nanoverse.runtime.geometry.MockGeometry;
import nanoverse.runtime.layers.MockLayerManager;
import nanoverse.runtime.layers.cell.*;
import org.junit.*;

import static org.junit.Assert.*;

/**
 * Created by dbborens on 2/21/14.
 */
public class CallbackManagerTest {
    private CallbackManager query;
    private CellLayer layer;
    private Coordinate c;
    private BehaviorCell cell;

    @Before
    public void setUp() throws Exception {
        MockGeometry geom = new MockGeometry();
        c = new Coordinate2D(0, 0, 0);
        Coordinate[] cc = new Coordinate[]{c};
        geom.setCanonicalSites(cc);
        layer = new CellLayer(geom);
        MockLayerManager layerManager = new MockLayerManager();
        layerManager.setCellLayer(layer);

        cell = new MockAgent();
        layer.getUpdateManager().place(cell, c);
        query = new CallbackManager(cell, layerManager);
    }


    @Test
    public void refreshDivisibility() throws Exception {
        /*
          On the face of it, this looks nearly identical to a test
          in BehaviorCellTest. However, since MockAgent does automatically
          update the layer indices as BehaviorCell does, this actually
          verifies the functionality of refreshDivisibility directly.
          It would be better to have a true mock cell layer that could
          confirm the appropriate calls were made and nothing more.
         */

        cell.setDivisible(false);
        query.refreshDivisibility();
        assertDivisibilityStatus(false);

        // Adjust above threshold.
        cell.setDivisible(true);
        query.refreshDivisibility();
        assertDivisibilityStatus(true);

        // Adjust below threshold again.
        cell.setDivisible(false);
        query.refreshDivisibility();
        assertDivisibilityStatus(false);
    }

    private void assertDivisibilityStatus(boolean expected) {
        boolean actual = layer.getViewer().isDivisible(c);
        assertEquals(expected, actual);
    }

    @Test
    public void die() {
        // Perform the test
        CellLayerViewer viewer = layer.getViewer();
        boolean isOccupied = viewer.isOccupied(c);
        assertTrue(isOccupied);
        query.die();
        assertFalse(layer.getViewer().isOccupied(c));
    }
}
