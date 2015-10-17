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

package nanoverse.runtime.agent.action;

import nanoverse.runtime.agent.Agent;
import nanoverse.runtime.agent.control.BehaviorDispatcher;
import nanoverse.runtime.cells.*;
import org.junit.*;
import test.LegacyLatticeTest;

import static org.junit.Assert.*;

/**
 * Created by dbborens on 2/10/14.
 */
public class DieTest extends LegacyLatticeTest {
    private Action query, identical, different;
    private Agent cell;
    private BehaviorDispatcher dispatcher;
    private Action behavior;
    private String eventName;

    @Before
    @Override
    public void setUp() throws Exception {
        fail("Rewrite as a modern test");
//        super.setUp();
//        // Set up test objects
//        cell = new Agent(layerManager, "test", null);
//        query = new Die(cell, layerManager, null);
//        identical = new Die(cell, layerManager, null);
//        different = new CloneTo(cell, layerManager, 0.7);
//
//        // Configure behavior dispatcher
//        eventName = "TEST";
//        Action[] actionSequence = new Action[]{query};
//        behavior = new CompoundAction(cell, layerManager, actionSequence);
//        dispatcher = new BehaviorDispatcher();
//        cell.setDispatcher(dispatcher);
//        dispatcher.map(eventName, behavior);
//
//        cellLayer.getUpdateManager().place(cell, origin);
    }

    @Test
    public void testRun() throws Exception {
//        fail("Rewrite as a modern test");
//        assertTrue(cellLayer.getViewer().isOccupied(origin));
//        cell.trigger("TEST", null);
//        assertFalse(cellLayer.getViewer().isOccupied(origin));
    }

    @Test
    public void testEquals() throws Exception {
        fail("Rewrite as a modern test");
        // Create two equivalent Die objects.
        // Should be equal.
//        assertEquals(query, identical);
//
//        // Create a third, different Die object.
//        // Should not be equal.
//        assertNotEquals(query, different);
    }


    @Test
    public void testClone() throws Exception {
        fail("Rewrite as a modern test");
//        MockAgent cloneAgent = new MockAgent();
//
//        // Clone it.
//        Action clone = query.clone(cloneAgent);
//
//        // Clone should not be the same object.
//        assertFalse(clone == query);
//
//        // Clone should be equal.
//        assertTrue(clone.equals(query));
    }
}
