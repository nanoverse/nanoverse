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

package io.serialize.text;

import cells.MockCell;
import control.halt.ManualHaltEvent;
import control.identifiers.Coordinate;
import layers.cell.CellUpdateManager;
import processes.StepState;
import structural.MockGeneralParameters;
import test.*;

/**
 * Created by dbborens on 4/28/14.
 */
public class CensusWriterTest extends EslimeLatticeTestCase {
    private MockGeneralParameters p;
    private CensusWriter writer;
    private ManualHaltEvent haltEvent;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        p = makeMockGeneralParameters();
        writer = new CensusWriter(p, layerManager);
        haltEvent = new ManualHaltEvent("");
    }

    public void testLifeCycle() throws Exception {
        writer.init();
        // Create original configuration
        put(origin, 1);
        put(x, 1);
        put(y, 2);
        put(z, 3);

        // Flush original configuration
        StepState state = new StepState(0.0, 0);
        state.record(layerManager);
        writer.flush(state);

        // Create second configuration
        replace(origin, 3);
        state = new StepState(1.0, 1);
        state.record(layerManager);
        writer.flush(state);

        // Create third configuration
        put(yz, 4);
        state = new StepState(2.0, 2);
        state.record(layerManager);
        writer.flush(state);

        haltEvent.setGillespie(2.0);
        writer.dispatchHalt(haltEvent);
        writer.close();
        FileAssertions.assertOutputMatchesFixture("census.txt", true);
    }

    /**
     * Verifies that two cycles of the CensusWriter do not affect one another.
     * Regression test for bug 981770-71079096.
     */
    public void testCycleIndependence() throws Exception {
        haltEvent.setGillespie(0);
        p.setInstancePath(outputPath + "censusWriterTest/1/");
        writer.init();
        StepState state = new StepState(0.0, 0);
        put(origin, 1);
        state.record(layerManager);
        writer.flush(state);
        writer.dispatchHalt(haltEvent);

        p.setInstancePath(outputPath + "censusWriterTest/2/");
        writer.init();
        // Create original configuration
        replace(origin, 2);
        state = new StepState(0.0, 0);
        state.record(layerManager);
        writer.flush(state);
        writer.dispatchHalt(haltEvent);
        writer.close();

        FileAssertions.assertOutputMatchesFixture("censusWriterTest/1/census.txt", true);
        FileAssertions.assertOutputMatchesFixture("censusWriterTest/2/census.txt", true);
    }

    private void replace(Coordinate c, int state) throws Exception {
        CellUpdateManager u = cellLayer.getUpdateManager();
        u.banish(c);
        put(c, state);
    }

    private void put(Coordinate c, int state) throws Exception {
        MockCell cell = new MockCell(state);
        CellUpdateManager u = cellLayer.getUpdateManager();
        u.place(cell, c);
    }
}
