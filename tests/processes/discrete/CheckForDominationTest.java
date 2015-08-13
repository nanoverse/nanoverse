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

package processes.discrete;

import cells.BehaviorCell;
import control.arguments.*;
import control.halt.DominationEvent;
import control.identifiers.Coordinate;
import control.identifiers.Coordinate2D;
import geometry.Geometry;
import geometry.boundaries.Boundary;
import geometry.boundaries.Periodic;
import geometry.lattice.Lattice;
import geometry.lattice.RectangularLattice;
import geometry.shape.Rectangle;
import geometry.shape.Shape;
import layers.MockLayerManager;
import layers.cell.CellLayer;
import processes.BaseProcessArguments;
import processes.StepState;
import processes.discrete.check.CheckForDomination;
import structural.MockGeneralParameters;
import test.EslimeTestCase;

public class CheckForDominationTest extends EslimeTestCase {
    private MockLayerManager layerManager;
    private CellLayer layer;
    private CheckForDomination query;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        Lattice lattice = new RectangularLattice();
        layerManager = new MockLayerManager();
        Shape shape = new Rectangle(lattice, 11, 1);
        Boundary boundary = new Periodic(shape, lattice);
        Geometry geom = new Geometry(lattice, shape, boundary);
        layer = new CellLayer(geom);
        layerManager.setCellLayer(layer);
        MockGeneralParameters p = makeMockGeneralParameters();
        Argument<Double> thresholdArg = new ConstantDouble(0.2);
        Argument<Integer> stateArg = new ConstantInteger(1);

        // Create a 1D lattice of length 10.
        // Create an occupancy test that checks for 30% occupancy.
        BaseProcessArguments arguments = makeBaseProcessArguments(layerManager, p);
        CellProcessArguments cpArguments = makeCellProcessArguments(geom);
        query = new CheckForDomination(arguments, cpArguments, stateArg, thresholdArg);
        query.init();
    }

    public void testAboveThreshold() throws Exception {
        for (int i = 1; i < 4; i++) {
            populate(i, 1);
        }

        for (int i = 4; i < 11; i++) {
            populate(i, i);
        }

        doTest(true);
    }

    public void testAtThreshold() throws Exception {
        for (int i = 1; i < 3; i++) {
            populate(i, 1);
        }

        for (int i = 3; i < 11; i++) {
            populate(i, i);
        }

        doTest(true);
    }

    public void testBelowThreshold() throws Exception {
        for (int i = 1; i < 2; i++) {
            populate(i, 1);
        }

        for (int i = 2; i < 11; i++) {
            populate(i, i);
        }

        doTest(false);
    }

    private void doTest(boolean expectThrow) throws Exception {
        boolean thrown = false;

        try {
            query.fire(new StepState(0.0, 0));
        } catch (DominationEvent ex) {
            thrown = true;
        }

        assertEquals(expectThrow, thrown);
    }

    private void populate(int x, int state) throws Exception {
        BehaviorCell cell = new BehaviorCell(layerManager, state, state, state, null);
        Coordinate coord = new Coordinate2D(x, 0, 0);
        layer.getUpdateManager().place(cell, coord);
    }
}