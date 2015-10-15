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
import nanoverse.runtime.control.identifiers.*;
import nanoverse.runtime.geometry.Geometry;
import nanoverse.runtime.geometry.boundaries.*;
import nanoverse.runtime.geometry.lattice.*;
import nanoverse.runtime.geometry.shape.*;
import nanoverse.runtime.layers.MockLayerManager;
import nanoverse.runtime.layers.cell.*;
import nanoverse.runtime.processes.StepState;
import nanoverse.runtime.structural.MockGeneralParameters;
import org.junit.*;
import test.*;

public class SurfaceCensusWriterTest extends LegacyTest {
    private MockGeneralParameters p;
    private SurfaceCensusWriter writer;
    private ManualHaltEvent haltEvent;
    private Geometry geom;
    private MockLayerManager layerManager;
    private AgentLayer cellLayer;

    @Before
    public void setUp() throws Exception {
        p = makeMockGeneralParameters();

        Lattice lattice = new LinearLattice();
        Shape shape = new Line(lattice, 10);
        Boundary boundary = new Arena(shape, lattice);
        geom = new Geometry(lattice, shape, boundary);

        cellLayer = new AgentLayer(geom);
        layerManager = new MockLayerManager();
        layerManager.setAgentLayer(cellLayer);
        haltEvent = new ManualHaltEvent("");
        writer = new SurfaceCensusWriter(p, layerManager);
    }

    @Test
    public void testLifeCycle() throws Exception {
        writer.init();

        // Place single cell at center -- it is the surface
        StepState state = new StepState(0.0, 0);
        put(5, 1);
        state.record(layerManager);
        writer.flush(state);

        // Place a cell to its left -- both surface
        put(4, 1);
        state = new StepState(1.0, 1);
        state.record(layerManager);
        writer.flush(state);

        // Place a cell to the right of the original -- now the center is not surface
        put(6, 2);
        state = new StepState(2.0, 2);
        state.record(layerManager);
        writer.flush(state);

        // Replace left cell with a different name
        replace(4, 2);
        state = new StepState(3.0, 3);
        state.record(layerManager);
        writer.flush(state);

        haltEvent.setGillespie(3.0);
        writer.dispatchHalt(haltEvent);
        writer.close();
        FileAssertions.assertOutputMatchesFixture("surface_census.txt", true);
    }

    private void replace(int y, int state) throws Exception {
        Coordinate c = new Coordinate2D(0, y, 0);
        AgentUpdateManager u = cellLayer.getUpdateManager();
        u.banish(c);
        MockAgent cell = new MockAgent(state);
        u.place(cell, c);
    }

    private void put(int y, int state) throws Exception {
        Coordinate c = new Coordinate2D(0, y, 0);
        MockAgent cell = new MockAgent(state);
        AgentUpdateManager u = cellLayer.getUpdateManager();
        u.place(cell, c);
    }

    @Test
    public void testImaginarySites() throws Exception {
        writer.init();

        // Place nanoverse.runtime.cells in imaginary sites -- they should be treated normally
        StepState state = new StepState(0.0, 0);
        put(-2, 2);
        put(-1, 1);
        state.record(layerManager);
        writer.flush(state);
        haltEvent.setGillespie(0.0);
        writer.dispatchHalt(haltEvent);
        writer.close();
        FileAssertions.assertOutputMatchesFixture("surface_census_imaginary.txt", "surface_census.txt", true);
//        assertFilesEqual("surface_census_imaginary.txt", "surface_census.txt");
    }
}