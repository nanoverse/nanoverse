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

package nanoverse.runtime.processes.discrete;

import nanoverse.runtime.agent.Agent;
import nanoverse.runtime.control.arguments.*;
import nanoverse.runtime.control.halt.*;
import nanoverse.runtime.control.identifiers.*;
import nanoverse.runtime.geometry.Geometry;
import nanoverse.runtime.geometry.boundaries.*;
import nanoverse.runtime.geometry.lattice.*;
import nanoverse.runtime.geometry.shape.*;
import nanoverse.runtime.layers.MockLayerManager;
import nanoverse.runtime.layers.cell.AgentLayer;
import nanoverse.runtime.processes.*;
import nanoverse.runtime.processes.discrete.check.CheckForThresholdOccupancy;
import nanoverse.runtime.structural.MockGeneralParameters;
import org.junit.*;
import test.LegacyTest;

import static org.junit.Assert.assertEquals;

public class CheckForThresholdOccupancyTest extends LegacyTest {

    private MockLayerManager layerManager;
    private AgentLayer layer;
    private CheckForThresholdOccupancy query;

    @Before
    public void setUp() throws Exception {
        Lattice lattice = new RectangularLattice();
        layerManager = new MockLayerManager();
        Shape shape = new Rectangle(lattice, 10, 1);
        Boundary boundary = new Periodic(shape, lattice);
        Geometry geom = new Geometry(lattice, shape, boundary);
        layer = new AgentLayer(geom);
        layerManager.setAgentLayer(layer);
        MockGeneralParameters p = makeMockGeneralParameters();
        DoubleArgument thresholdArg = new ConstantDouble(0.2);

        // Create a 1D lattice of length 10.
        // Create an occupancy test that checks for 30% occupancy.
        BaseProcessArguments arguments = makeBaseProcessArguments(layerManager, p);
        AgentProcessArguments cpArguments = makeAgentProcessArguments(geom);
        query = new CheckForThresholdOccupancy(arguments, cpArguments, thresholdArg);
        query.init();
    }

    @Test
    public void testAboveThreshold() throws Exception {
        for (int i = 1; i < 4; i++) {
            placeNumberedAgent(i);
        }

        doTest(true);
    }

    private void doTest(boolean expectThrow) throws Exception {
        boolean thrown = false;

        try {
            query.fire(new StepState(0.0, 0));
        } catch (ThresholdOccupancyReachedEvent ex) {
            thrown = true;
        }

        assertEquals(expectThrow, thrown);
    }

    private void placeNumberedAgent(int x) throws HaltCondition {
        Agent cell = new Agent(layerManager, x, x, x, null);
        Coordinate coord = new Coordinate2D(x, 0, 0);
        layer.getUpdateManager().place(cell, coord);
    }

    @Test
    public void testAtThreshold() throws Exception {
        for (int i = 1; i < 3; i++) {
            placeNumberedAgent(i);
        }

        doTest(true);
    }

    @Test
    public void testBelowThreshold() throws Exception {
        for (int i = 1; i < 2; i++) {
            placeNumberedAgent(i);
        }

        doTest(false);
    }

}