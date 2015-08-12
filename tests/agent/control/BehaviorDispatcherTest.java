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

package agent.control;

import agent.Behavior;
import agent.MockBehavior;
import cells.BehaviorCell;
import control.identifiers.Coordinate;
import control.identifiers.Coordinate2D;
import junit.framework.TestCase;

/**
 * Created by David B Borenstein on 1/21/14.
 */
public class BehaviorDispatcherTest extends TestCase {

    private BehaviorDispatcher query;
    private Coordinate caller1, caller2;
    private MockBehavior behavior1, behavior2;

    @Override
    protected void setUp() throws Exception {
        query = new BehaviorDispatcher();
        caller1 = new Coordinate2D(0, 0, 0);
        caller2 = new Coordinate2D(1, 0, 0);
        behavior1 = new MockBehavior();
        behavior2 = new MockBehavior();
    }

    public void testLifeCycle() throws Exception {
        String name = "testBehavior";

        // Neither behavior has occurred.
        assertEquals(0, behavior1.getTimesRun());
        assertEquals(0, behavior2.getTimesRun());

        // Map first behavior.
        query.map(name, behavior1);

        // Trigger behavior.
        query.trigger(name, null);

        // Only the first behavior should have occured.
        assertEquals(1, behavior1.getTimesRun());
        assertEquals(0, behavior2.getTimesRun());

        // Remap to second behavior.
        query.map(name, behavior2);

        // Trigger behavior.
        query.trigger(name, null);

        // Each behavior should have happened once.
        assertEquals(1, behavior1.getTimesRun());
        assertEquals(1, behavior2.getTimesRun());
    }

    public void testTriggerWithCallback() throws Exception {
        // Set up
        String name = "testBehavior";
        query.map(name, behavior1);

        // Nobody's been the caller yet
        assertEquals(0, behavior1.timesCaller(caller1));
        assertEquals(0, behavior1.timesCaller(caller2));

        // Trigger with first caller
        query.trigger(name, caller1);

        // First caller has been caller once
        assertEquals(1, behavior1.timesCaller(caller1));
        assertEquals(0, behavior1.timesCaller(caller2));

        // Trigger with second caller
        query.trigger(name, caller2);

        // Each caller has called once
        assertEquals(1, behavior1.timesCaller(caller1));
        assertEquals(1, behavior1.timesCaller(caller2));
    }

    public void testClone() throws Exception {
        String name = "testBehavior";
        query.map(name, behavior1);

        BehaviorCell alternate = new BehaviorCell();
        BehaviorDispatcher clone = query.clone(alternate);

        // The objects should be equal in that their behavior lists are equal.
        assertEquals(query, clone);

        // The objects should not be the same object.
        assertFalse(query == clone);

        // The new object should have the alternate as a callback.
        Behavior clonedBehavior = clone.getMappedBehavior(name);
        assertEquals(alternate, clonedBehavior.getCallback());
    }

}
