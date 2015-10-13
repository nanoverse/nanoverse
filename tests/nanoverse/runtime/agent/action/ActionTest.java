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

import nanoverse.runtime.agent.BehaviorCell;
import nanoverse.runtime.agent.AbstractAgent;
import nanoverse.runtime.control.arguments.*;
import nanoverse.runtime.control.halt.HaltCondition;
import nanoverse.runtime.control.identifiers.*;
import nanoverse.runtime.geometry.MockGeometry;
import nanoverse.runtime.layers.*;
import nanoverse.runtime.layers.cell.CellLayer;
import nanoverse.runtime.processes.StepState;
import org.junit.*;
import test.TestBase;

import java.util.stream.Stream;

import static org.junit.Assert.*;

/**
 * Created by David B Borenstein on 1/23/14.
 */
public class ActionTest extends TestBase {

    MockLayerManager layerManager;
    Coordinate caller;
    BehaviorCell callback;
    ExposedAction query;

    @Before
    public void setUp() throws Exception {
        layerManager = new MockLayerManager();
        callback = new BehaviorCell();
        caller = new Coordinate2D(0, 0, 0);

        query = new ExposedAction(callback, layerManager);
    }

    @Test
    public void getLayerManager() throws Exception {
        LayerManager expected = layerManager;
        LayerManager actual = query.getLayerManager();
        assertEquals(expected, actual);
    }

    @Test
    public void getCallback() throws Exception {
        AbstractAgent expected = callback;
        AbstractAgent actual = query.getCallback();
        assertEquals(expected, actual);
        assertTrue(expected == actual);
    }

    @Test
    public void runNullCaller() throws Exception {
        query.run(null);
        assertTrue(query.isRun());
    }

    @Test
    public void runWithCaller() throws Exception {
        query.run(caller);
        assertTrue(query.isRun());
        assertEquals(caller, query.getLastCaller());
    }

    @Test
    public void doHighlight() throws Exception {
        Coordinate[] cc = new Coordinate[]{caller};
        MockGeometry geom = new MockGeometry();
        geom.setCanonicalSites(cc);
        CellLayer layer = new CellLayer(geom);
        layerManager.setCellLayer(layer);
        StepState stepState = new StepState(0.0, 0);
        layerManager.setStepState(stepState);
        query.doHighlight(new ConstantInteger(1), caller);
//        Coordinate[] actual = stepState.getHighlights(1);
        Stream<Coordinate> actual = stepState.getHighlights(1);
        Stream<Coordinate> expected = Stream.of(caller);
        assertStreamsEqual(expected, actual);
//        Coordinate[] expected = new Coordinate[]{caller};
//        assertArraysEqual(expected, actual, true);
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
        public void doHighlight(IntegerArgument channelArg, Coordinate toHighlight) throws HaltCondition {
            super.doHighlight(channelArg, toHighlight);
        }
    }
}
