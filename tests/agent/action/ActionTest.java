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

import cells.BehaviorCell;
import cells.Cell;
import control.arguments.Argument;
import control.arguments.ConstantInteger;
import control.halt.HaltCondition;
import control.identifiers.Coordinate;
import geometry.MockGeometry;
import layers.LayerManager;
import layers.MockLayerManager;
import layers.cell.CellLayer;
import processes.StepState;
import test.EslimeTestCase;

/**
 * Created by David B Borenstein on 1/23/14.
 */
public class ActionTest extends EslimeTestCase {

    MockLayerManager layerManager;
    Coordinate caller;
    BehaviorCell callback;
    ExposedAction query;

    @Override
    protected void setUp() throws Exception {
        layerManager = new MockLayerManager();
        callback = new BehaviorCell();
        caller = new Coordinate(0, 0, 0);

        query = new ExposedAction(callback, layerManager);
    }

    public void testGetLayerManager() throws Exception {
        LayerManager expected = layerManager;
        LayerManager actual = query.getLayerManager();
        assertEquals(expected, actual);
    }

    public void testGetCallback() throws Exception {
        Cell expected = callback;
        Cell actual = query.getCallback();
        assertEquals(expected, actual);
        assertTrue(expected == actual);
    }

    public void testRunNullCaller() throws Exception {
        query.run(null);
        assertTrue(query.isRun());
    }

    public void testRunWithCaller() throws Exception {
        query.run(caller);
        assertTrue(query.isRun());
        assertEquals(caller, query.getLastCaller());
    }

    public void testDoHighlight() throws Exception {
        Coordinate[] cc = new Coordinate[]{caller};
        MockGeometry geom = new MockGeometry();
        geom.setCanonicalSites(cc);
        CellLayer layer = new CellLayer(geom);
        layerManager.setCellLayer(layer);
        StepState stepState = new StepState(0.0, 0);
        layerManager.setStepState(stepState);
        query.doHighlight(new ConstantInteger(1), caller);
        Coordinate[] actual = stepState.getHighlights(1);
        Coordinate[] expected = new Coordinate[]{caller};
        assertArraysEqual(expected, actual, true);
    }

    private class ExposedAction extends Action {

        private boolean isRun = false;
        private Coordinate lastCaller = null;

        public ExposedAction(BehaviorCell callback, LayerManager layerManager) {
            super(callback, layerManager);
        }

        public boolean isRun() {
            return isRun;
        }

        public Coordinate getLastCaller() {
            return lastCaller;
        }

        @Override
        public void run(Coordinate caller) {
            isRun = true;
            lastCaller = caller;
        }

        @Override
        public BehaviorCell getCallback() {
            return super.getCallback();
        }

        @Override
        public boolean equals(Object obj) {
            return (obj instanceof ExposedAction);
        }

        @Override
        public Action clone(BehaviorCell child) {
            return new ExposedAction(child, layerManager);
        }

        @Override
        public void doHighlight(Argument<Integer> channelArg, Coordinate toHighlight) throws HaltCondition {
            super.doHighlight(channelArg, toHighlight);
        }
    }
}
