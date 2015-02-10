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
import structural.MockGeneralParameters;
import structural.MockRandom;
import test.EslimeTestCase;

/**
 * Created by dbborens on 4/21/14.
 */
public class GeneralNeighborSwapTest extends EslimeTestCase {

    private GeneralNeighborSwap query;
    private MockLayerManager layerManager;
    private MockCell a, b, c;
    private Coordinate ac, bc, cc;
    private MockRandom random;

    @Override
    protected void setUp() throws Exception {
        Lattice lattice = new RectangularLattice();
        Shape shape = new Rectangle(lattice, 3, 3);
        Boundary boundary = new Arena(shape, lattice);
        Geometry geom = new Geometry(lattice, shape, boundary);
        CellLayer cellLayer = new CellLayer(geom);

        layerManager = new MockLayerManager();
        layerManager.setCellLayer(cellLayer);

        MockGeneralParameters p = makeMockGeneralParameters();
        random = new MockRandom();
        p.setRandom(random);
        BaseProcessArguments arguments = makeBaseProcessArguments(layerManager, p);
        CellProcessArguments cpArguments = new CellProcessArguments(new CompleteSet(geom), new ConstantInteger(1));
        query = new GeneralNeighborSwap(arguments, cpArguments);
        query.init();

        /*
         * Cell layout:
         *     0 1 2
         *  0  b . .
         *  1  a c .
         *  2  . . .
         */
        a = new MockCell(1);
        b = new MockCell(2);
        c = new MockCell(3);

        ac = new Coordinate(0, 1, 0);
        bc = new Coordinate(0, 0, 0);
        cc = new Coordinate(1, 1, 0);
        cellLayer.getUpdateManager().place(a, ac);
        cellLayer.getUpdateManager().place(b, bc);
        cellLayer.getUpdateManager().place(c, cc);
    }

    public void testCellsReflectSwap() throws Exception {
        query.target(null);
        MockStepState state = new MockStepState();

        CellLayer cl = layerManager.getCellLayer();

        // Enforce that the desired random event happens
        random.setNextIntValue(1);
        // a should move
        assertTrue(cl.getViewer().getCell(ac).equals(a));
        query.fire(state);
        assertFalse(cl.getViewer().getCell(ac).equals(a));

        // specifically, a should be swapped with c
        assertTrue(cl.getViewer().getCell(bc).equals(b));
        assertTrue(cl.getViewer().getCell(ac).equals(c));
        assertTrue(cl.getViewer().getCell(cc).equals(a));

    }
}
