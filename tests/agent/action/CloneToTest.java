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

import agent.Behavior;
import agent.control.BehaviorDispatcher;
import agent.targets.MockTargetRule;
import cells.BehaviorCell;
import cells.Cell;
import control.identifiers.Coordinate;
import geometry.Geometry;
import geometry.boundaries.Boundary;
import geometry.boundaries.Periodic;
import geometry.lattice.Lattice;
import geometry.lattice.RectangularLattice;
import geometry.shape.Rectangle;
import geometry.shape.Shape;
import layers.cell.CellLayer;
import test.EslimeLatticeTestCase;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CloneToTest extends EslimeLatticeTestCase {

    private BehaviorCell original;
    private MockTargetRule targetRule;
    private CloneTo query;
    private Random random;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        // Create mock targeter that lists other two sites as targets.
        targetRule = new MockTargetRule();
        List<Coordinate> targets = new ArrayList<>(2);
        targets.add(x);
        targets.add(y);
        targetRule.setTargets(targets);

        // Place a single cell at origin.
        original = new BehaviorCell(layerManager, 1, 1.0, 1.0);
        BehaviorDispatcher bd = new BehaviorDispatcher();
        original.setDispatcher(bd);

        cellLayer.getUpdateManager().place(original, origin);

        random = new Random(RANDOM_SEED);
        // Create query.
        query = new CloneTo(original, layerManager, targetRule, false, null,
                null, random);
    }

    public void testLifeCycle() throws Exception {
        // Trigger the replicate event.
        query.run(null);

        // The other two sites should be occupied.
        assertTrue(cellLayer.getViewer().isOccupied(x));
        assertTrue(cellLayer.getViewer().isOccupied(y));

        // The cells at the other sites should each be equal to the original.
        assertEquals(original, cellLayer.getViewer().getCell(x));
        assertEquals(original, cellLayer.getViewer().getCell(y));
        assertFalse(original == cellLayer.getViewer().getCell(y));
    }

    /**
     * Integration test using replacement process.
     *
     * @throws Exception
     */
    public void testReplacement() throws Exception {
        CellLayer layer = linearLayer(false);
        Cell cell = layer.getViewer().getCell(new Coordinate(4, 0, 0));

        // Divide cell at position 4 toward 5
        cell.trigger("replicate-self", null);

        // New configuration: _123446_89
        assertEquals(4, layer.getViewer().getState(new Coordinate(4, 0, 0)));
        assertEquals(4, layer.getViewer().getState(new Coordinate(5, 0, 0)));
        assertEquals(6, layer.getViewer().getState(new Coordinate(6, 0, 0)));
        assertFalse(layer.getViewer().isOccupied(new Coordinate(7, 0, 0)));
    }

    /**
     * _123456_89  Initial condition
     * ^       (Cell to be divided)
     */
    private CellLayer linearLayer(boolean shoving) throws Exception {
        Lattice lattice = new RectangularLattice();
        Shape shape = new Rectangle(lattice, 10, 1);
        Boundary boundary = new Periodic(shape, lattice);
        Geometry geom = new Geometry(lattice, shape, boundary);
        CellLayer layer = new CellLayer(geom);
        layerManager.setCellLayer(layer);
        placeCells(layer, shoving);

        return layer;
    }

    private void placeNumberedCell(int x, CellLayer layer, boolean shoving) throws Exception {
        BehaviorCell cell = new BehaviorCell(layerManager, x, x, x);
        Coordinate coord = new Coordinate(x, 0, 0);
        layer.getUpdateManager().place(cell, coord);
        BehaviorDispatcher bd = new BehaviorDispatcher();
        cell.setDispatcher(bd);

        MockTargetRule mtr = new MockTargetRule();

        // Cells always divide to the right
        Coordinate target = new Coordinate(x + 1, 0, 0);
        List<Coordinate> targets = new ArrayList<>(1);
        targets.add(target);
        mtr.setTargets(targets);

        CloneTo cloneTo = new CloneTo(cell, layerManager, mtr,
                shoving, null, null, random);

        Behavior behavior = new Behavior(cell, layerManager, new Action[]{cloneTo});
        bd.map("replicate-self", behavior);

    }

    private void placeCells(CellLayer layer, boolean shoving) throws Exception {
        for (int x = 1; x < 7; x++) {
            placeNumberedCell(x, layer, shoving);
        }

        for (int x = 8; x <= 9; x++) {
            placeNumberedCell(x, layer, shoving);
        }
    }

}