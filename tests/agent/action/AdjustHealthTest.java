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

package agent.action;

import agent.control.BehaviorDispatcher;
import cells.*;
import geometry.MockGeometry;
import layers.MockLayerManager;
import layers.cell.CellLayer;
import org.junit.*;
import test.EslimeLatticeTestCase;

import static org.junit.Assert.*;
/**
 * Created by David B Borenstein on 2/5/14.
 */
public class AdjustHealthTest extends EslimeLatticeTestCase {
    private AdjustHealth query, identical, different;
    private BehaviorCell cell;
    private BehaviorDispatcher dispatcher;
    private Action behavior;
    private String eventName;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        // Set up test objects
        layerManager = new MockLayerManager();
        MockGeometry geom = buildMockGeometry();
        cellLayer = new CellLayer(geom);
        layerManager.setCellLayer(cellLayer);
        cell = new BehaviorCell(layerManager, 1, 0.5, 1.0, null);
        cellLayer.getUpdateManager().place(cell, origin);
        query = new AdjustHealth(cell, layerManager, 0.5);
        identical = new AdjustHealth(cell, layerManager, 0.5);
        different = new AdjustHealth(cell, layerManager, 0.7);

        // Configure behavior dispatcher
        eventName = "TEST";
        Action[] actionSequence = new Action[]{query};
        behavior = new CompoundAction(cell, layerManager, actionSequence);
        dispatcher = new BehaviorDispatcher();
        cell.setDispatcher(dispatcher);
        dispatcher.map(eventName, behavior);
    }

    @Test
    public void testRun() throws Exception {
        assertEquals(0.5, cell.getHealth(), epsilon);
        cell.trigger("TEST", null);
        assertEquals(1.0, cell.getHealth(), epsilon);
    }

    @Test
    public void testEquals() throws Exception {
        // Create two equivalent AdjustHealth objects.
        // Should be equal.
        assertEquals(query, identical);

        // Create a third, different AdjustHealth object.
        // Should not be equal.
        assertNotEquals(query, different);
    }

    @Test
    public void testClone() throws Exception {
        MockCell cloneCell = new MockCell();

        // Clone it.
        Action clone = query.clone(cloneCell);

        // Clone should not be the same object.
        assertFalse(clone == query);

        // Clone should be equal.
        assertTrue(clone.equals(query));
    }
}
