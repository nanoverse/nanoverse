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

package cells;

import agent.control.MockBehaviorDispatcher;
import control.identifiers.Coordinate;
import test.EslimeLatticeTestCase;

/**
 * Created by David B Borenstein on 1/25/14.
 */
public class BehaviorCellTest extends EslimeLatticeTestCase {

    private BehaviorCell query;
    private MockBehaviorDispatcher dispatcher;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        dispatcher = new MockBehaviorDispatcher();
        query = new BehaviorCell(layerManager, 1, 1.0, 0.5, null);
        query.setDispatcher(dispatcher);
        cellLayer.getUpdateManager().place(query, origin);
    }

    public void testConsiderAndApply() {
        int result = query.consider();
        assertEquals(1, result);

        result = query.consider();
        assertEquals(2, result);

        query.apply();

        result = query.consider();

        assertEquals(1, result);
    }

    public void testDivide() throws Exception {
        Cell child = query.divide();
        assertEquals(child, query);

        // Health should be half for each
        assertEquals(0.5, query.getHealth(), epsilon);
        assertEquals(0.5, child.getHealth(), epsilon);
    }

    public void testCloneNoArgument() throws Exception {
        Cell clone = query.replicate();
        assertEquals(clone, query);

        // Since no division took place, health should be original for each
        assertEquals(1.0, query.getHealth(), epsilon);
        assertEquals(1.0, clone.getHealth(), epsilon);
    }

    public void testClone() throws Exception {
        Cell clone = query.clone(6);
        assertNotEquals(clone, query);
        Cell cloneOfClone = clone.clone(query.getState());
        assertEquals(query, cloneOfClone);
    }

    public void testTrigger() throws Exception {
        String triggerName = "TEST";
        Coordinate caller = new Coordinate(0, 0, 0);
        query.trigger(triggerName, caller);

        assertEquals(triggerName, dispatcher.getLastTriggeredName());
        assertEquals(caller, dispatcher.getLastTriggeredCaller());
    }

    public void testDie() throws Exception {
        assertTrue(cellLayer.getViewer().isOccupied(origin));
        query.die();
        assertFalse(cellLayer.getViewer().isOccupied(origin));
    }

    public void testEquals() throws Exception {
        // Difference based on dispatcher (in)equality.
        BehaviorCell other = new BehaviorCell(layerManager, 1, 1.0, 0.5, null);
        MockBehaviorDispatcher d2 = new MockBehaviorDispatcher();
        other.setDispatcher(d2);
        d2.setOverrideEquals(true);

        // ...unequal dispatcher.
        d2.setReportEquals(false);
        assertNotEquals(query, other);

        // ...equal dispatcher.
        d2.setReportEquals(true);
        assertEquals(query, other);

        // Test a cell that differs in division threshold.
        other = new BehaviorCell(layerManager, 1, 1.0, 1.0, null);
        other.setDispatcher(d2);
        assertNotEquals(query, other);

        // Test a cell that differs in state.
        other = new BehaviorCell(layerManager, 2, 1.0, 0.5, null);
        other.setDispatcher(d2);
        assertNotEquals(query, other);
    }

    /**
     * The base BehaviorCell class automatically marks itself
     * as divisible according to its health. So if a call
     * to setHealth() puts it above or below the threshold,
     * that should be noted.
     */
    public void testDivisibilityThresholding() throws Exception {
        double threshold = query.getThreshold();

        // Start off below threshold.
        query.setHealth(threshold / 2);
        assertDivisibilityStatus(false);

        // Adjust above threshold.
        query.setHealth(threshold * 2);
        assertDivisibilityStatus(true);

        // Adjust below threshold again.
        query.setHealth(threshold / 2);
        assertDivisibilityStatus(false);
    }

    private void assertDivisibilityStatus(boolean expected) {
        boolean layerActual = cellLayer.getViewer().isDivisible(origin);
        boolean cellActual = query.isDivisible();
        assertEquals(expected, layerActual);
        assertEquals(expected, cellActual);
    }

}
