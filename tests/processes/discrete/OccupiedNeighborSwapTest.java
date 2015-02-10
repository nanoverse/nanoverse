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

import cells.MockCell;
import control.arguments.ConstantInteger;
import control.identifiers.Coordinate;
import geometry.Geometry;
import geometry.boundaries.Arena;
import geometry.boundaries.Boundary;
import geometry.lattice.Lattice;
import geometry.lattice.RectangularLattice;
import geometry.set.CompleteSet;
import geometry.shape.Rectangle;
import geometry.shape.Shape;
import layers.MockLayerManager;
import layers.cell.CellLayer;
import processes.BaseProcessArguments;
import processes.MockStepState;
import processes.gillespie.GillespieState;
import structural.MockGeneralParameters;
import test.EslimeTestCase;

/**
 * Created by dbborens on 4/21/14.
 */
public class OccupiedNeighborSwapTest extends EslimeTestCase {

    private OccupiedNeighborSwap query;
    private MockLayerManager layerManager;
    private MockCell a, b, c;
    private Coordinate aa, bb, cc;

    @Override
    protected void setUp() throws Exception {
        Lattice lattice = new RectangularLattice();
        Shape shape = new Rectangle(lattice, 2, 2);
        Boundary boundary = new Arena(shape, lattice);
        Geometry geom = new Geometry(lattice, shape, boundary);
        CellLayer cellLayer = new CellLayer(geom);

        layerManager = new MockLayerManager();
        layerManager.setCellLayer(cellLayer);

        MockGeneralParameters p = makeMockGeneralParameters();
        BaseProcessArguments arguments = makeBaseProcessArguments(layerManager, p);
        CellProcessArguments cpArguments = new CellProcessArguments(new CompleteSet(geom), new ConstantInteger(1));

        query = new OccupiedNeighborSwap(arguments, cpArguments);

        /*
         * Cell layout:
         *     0 1
         *  0  b .
         *  1  a c
         */
        a = new MockCell(1);
        b = new MockCell(2);
        c = new MockCell(3);

        aa = new Coordinate(0, 1, 0);
        bb = new Coordinate(0, 0, 0);
        cc = new Coordinate(1, 1, 0);
        cellLayer.getUpdateManager().place(a, aa);
        cellLayer.getUpdateManager().place(b, bb);
        cellLayer.getUpdateManager().place(c, cc);
    }

    public void testCellsReflectSwap() throws Exception {
        query.target(null);
        MockStepState state = new MockStepState();
        query.fire(state);

        CellLayer cl = layerManager.getCellLayer();

        assertFalse(cl.getViewer().getCell(aa).equals(a));
    }

    public void testGillespie() throws Exception {
        GillespieState gs = new GillespieState(new Integer[]{0});
        query.target(gs);
        gs.close();
        // a, b, and c are all involved.
        // WARNING: This method will count cells that cannot participate in
        // a swap toward the weight of the Gillespie function as of 4/30/2014.
        assertEquals(3.0, gs.getWeight(0));
        assertEquals(2, gs.getEventCount(0));
    }
}
