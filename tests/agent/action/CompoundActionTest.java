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

import cells.*;
import control.halt.HaltCondition;
import control.identifiers.*;
import layers.*;
import org.junit.*;
import test.LegacyTest;

import static org.junit.Assert.*;
/**
 * Created by dbborens on 3/6/14.
 */
public class CompoundActionTest extends LegacyTest {

    ExposedCompoundAction query;
    MockLayerManager layerManager;
    MockCell callBack;
    MockAction a, b;
    Coordinate caller;

    Action[] actionSequence;

    @Before
    public void setUp() throws Exception {
        layerManager = new MockLayerManager();
        callBack = new MockCell();
        caller = new Coordinate2D(0, 0, 0);
        initActionSequence();
        query = new ExposedCompoundAction(callBack, layerManager, actionSequence);
    }

    private void initActionSequence() {
        a = new MockAction();
        b = new MockAction();

        actionSequence = new Action[]{a, b};
    }

    @Test
    public void testGetLayerManager() throws Exception {
        LayerManager expected = layerManager;
        LayerManager actual = query.getLayerManager();
        assertEquals(expected, actual);
    }

    @Test
    public void testGetCallback() throws Exception {
        Cell expected = callBack;
        Cell actual = query.getCallback();
        assertEquals(expected, actual);
    }

    @Test
    public void testGetActionSequence() throws Exception {
        Action[] expected = actionSequence;
        Action[] actual = query.getActionSequence();

        assertEquals(2, actual.length);
        for (int i = 0; i < 2; i++) {
            // These should just be assigned
            assertEquals(expected[i], actual[i]);
        }
    }

    @Test
    public void testRunNullCaller() throws Exception {
        query.run(null);
        assertEquals(1, a.getTimesRun());
        assertEquals(1, b.getTimesRun());
        assertNull(a.getLastCaller());
        assertNull(b.getLastCaller());
    }

    @Test
    public void testRunWithCaller() throws Exception {
        query.run(caller);
        assertEquals(1, a.getTimesRun());
        assertEquals(1, b.getTimesRun());
        assertEquals(caller, a.getLastCaller());
        assertEquals(caller, b.getLastCaller());
    }

    @Test
    public void testClone() throws Exception {
        BehaviorCell cloneCell = new BehaviorCell();
        CompoundAction clone = query.clone(cloneCell);
        assertEquals(cloneCell, clone.getCallback());
        assertEquals(clone, query);
    }

    private class ExposedCompoundAction extends CompoundAction {
        public ExposedCompoundAction(BehaviorCell callback, LayerManager layerManager, Action[] actionSequence) {
            super(callback, layerManager, actionSequence);
        }

        @Override
        public LayerManager getLayerManager() {
            return super.getLayerManager();
        }

        @Override
        public void run(Coordinate caller) throws HaltCondition {
            super.run(caller);
        }

        @Override
        public Action[] getActionSequence() {
            return super.getActionSequence();
        }
    }
}
