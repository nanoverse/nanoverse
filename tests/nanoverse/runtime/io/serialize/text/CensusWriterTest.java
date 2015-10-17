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

package nanoverse.runtime.io.serialize.text;

import nanoverse.runtime.cells.MockAgent;
import nanoverse.runtime.control.halt.ManualHaltEvent;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.layers.cell.AgentUpdateManager;
import nanoverse.runtime.processes.StepState;
import nanoverse.runtime.structural.MockGeneralParameters;
import org.junit.*;
import test.*;

/**
 * Created by dbborens on 4/28/14.
 */
public class CensusWriterTest extends LegacyLatticeTest {
    private MockGeneralParameters p;
    private CensusWriter writer;
    private ManualHaltEvent haltEvent;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        p = makeMockGeneralParameters();
        writer = new CensusWriter(p, layerManager);
        haltEvent = new ManualHaltEvent("");
    }

    @Test
    public void testLifeCycle() throws Exception {
        writer.init();
        // Create original configuration
        put(origin, "1");
        put(x, "1");
        put(y, "2");
        put(z, "3");

        // Flush original configuration
        StepState name = new StepState(0.0, 0);
        name.record(layerManager);
        writer.flush(name);

        // Create second configuration
        replace(origin, "3");
        name = new StepState(1.0, 1);
        name.record(layerManager);
        writer.flush(name);

        // Create third configuration
        put(yz, "4");
        name = new StepState(2.0, 2);
        name.record(layerManager);
        writer.flush(name);

        haltEvent.setGillespie(2.0);
        writer.dispatchHalt(haltEvent);
        writer.close();
        FileAssertions.assertOutputMatchesFixture("census.txt", true);
    }

    private void replace(Coordinate c, String name) throws Exception {
        AgentUpdateManager u = cellLayer.getUpdateManager();
        u.banish(c);
        put(c, name);
    }

    private void put(Coordinate c, String name) throws Exception {
        MockAgent cell = new MockAgent(name);
        AgentUpdateManager u = cellLayer.getUpdateManager();
        u.place(cell, c);
    }

    /**
     * Verifies that two cycles of the CensusWriter do not affect one another.
     * Regression test for bug 981770-71079096.
     */
    @Test
    public void testCycleIndependence() throws Exception {
        haltEvent.setGillespie(0);
        p.setInstancePath(outputPath + "censusWriterTest/1/");
        writer.init();
        StepState name = new StepState(0.0, 0);
        put(origin, "1");
        name.record(layerManager);
        writer.flush(name);
        writer.dispatchHalt(haltEvent);

        p.setInstancePath(outputPath + "censusWriterTest/2/");
        writer.init();
        // Create original configuration
        replace(origin, "2");
        name = new StepState(0.0, 0);
        name.record(layerManager);
        writer.flush(name);
        writer.dispatchHalt(haltEvent);
        writer.close();

        FileAssertions.assertOutputMatchesFixture("censusWriterTest/1/census.txt", true);
        FileAssertions.assertOutputMatchesFixture("censusWriterTest/2/census.txt", true);
    }
}
