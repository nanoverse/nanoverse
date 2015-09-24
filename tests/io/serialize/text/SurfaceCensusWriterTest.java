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
import control.identifiers.*;
import geometry.Geometry;
import geometry.boundaries.*;
import geometry.lattice.*;
import geometry.shape.*;
import layers.MockLayerManager;
import layers.cell.*;
import org.junit.*;
import processes.StepState;
import structural.MockGeneralParameters;
import test.*;

public class SurfaceCensusWriterTest extends EslimeTestCase {
    private MockGeneralParameters p;
    private SurfaceCensusWriter writer;
    private ManualHaltEvent haltEvent;
    private Geometry geom;
    private MockLayerManager layerManager;
    private CellLayer cellLayer;

    @Before
    public void setUp() throws Exception {
        p = makeMockGeneralParameters();

        Lattice lattice = new LinearLattice();
        Shape shape = new Line(lattice, 10);
        Boundary boundary = new Arena(shape, lattice);
        geom = new Geometry(lattice, shape, boundary);

        cellLayer = new CellLayer(geom);
        layerManager = new MockLayerManager();
        layerManager.setCellLayer(cellLayer);
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

        // Replace left cell with a different state
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
        CellUpdateManager u = cellLayer.getUpdateManager();
        u.banish(c);
        MockCell cell = new MockCell(state);
        u.place(cell, c);
    }

    private void put(int y, int state) throws Exception {
        Coordinate c = new Coordinate2D(0, y, 0);
        MockCell cell = new MockCell(state);
        CellUpdateManager u = cellLayer.getUpdateManager();
        u.place(cell, c);
    }

    @Test
    public void testImaginarySites() throws Exception {
        writer.init();

        // Place cells in imaginary sites -- they should be treated normally
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