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

import agent.control.BehaviorDispatcher;
import agent.targets.MockTargetRule;
import cells.BehaviorCell;
import cells.Cell;
import cells.MockCell;
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
import structural.MockRandom;
import test.EslimeTestCase;

import java.util.ArrayList;
import java.util.function.Supplier;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ExpandTest extends EslimeTestCase {

    private static final int MOCK_PROGENY_STATE = 7;

    private MockLayerManager layerManager;
    private BehaviorCell parent;
    private MockRandom random;
    private CellLayer layer;
    private MockTargetRule parentTargetRule;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        Lattice lattice = new RectangularLattice();
        layerManager = new MockLayerManager();
        Shape shape = new Rectangle(lattice, 10, 1);
        Boundary boundary = new Periodic(shape, lattice);
        Geometry geom = new Geometry(lattice, shape, boundary);
        layer = new CellLayer(geom);
        layerManager.setCellLayer(layer);
        random = new MockRandom();

        // Place the parent at site 4 and get its target rule
        parentTargetRule = placeNumberedCell(4);
        parent = (BehaviorCell) layer.getViewer().getCell(new Coordinate2D(4, 0, 0));

    }

    /**
     * Nearest vacancy is left; cell divides left.
     * <p>
     * 0123456789
     * ____45____  Initial condition
     * ^       Cell 4 divides left
     * <p>
     * 0123456789
     * ___445____  Resulting condition
     */
    public void testOneVacancy() throws Exception {
        // Place blocking cell
        placeNumberedCell(5);

        Coordinate target = new Coordinate2D(3, 0, 0);
        ArrayList<Coordinate> targets = new ArrayList<>(1);
        targets.add(target);
        parentTargetRule.setTargets(targets);
        parent.trigger("replicate-self", null);

        checkPosition(3, 4);
        checkPosition(4, 4);
        checkPosition(5, 5);
    }

    /**
     * Both sides vacant; outcome depends on random number value.
     * <p>
     * 0123456789
     * ____4_____  Initial condition
     * <p>
     * 0123456789
     * ___44_____  Case 1
     * <p>
     * 0123456789
     * ____44____  Case 2
     * <p>
     * Since the difference between case 1 and case 2 is handled
     * in a helper object (ShoveHelper) which is tested separately,
     * we consider only case 1.
     */
    public void testMultipleVacancies() throws Exception {

        Coordinate target = new Coordinate2D(3, 0, 0);
        ArrayList<Coordinate> targets = new ArrayList<>(1);
        targets.add(target);
        parent.trigger("replicate-self", null);

        checkPosition(3, 4);
        checkPosition(4, 4);
    }

    /**
     * Both sides occupied, one has closer vacancy
     * <p>
     * 0123456789
     * __2345____  Initial condition
     * ^
     * 0123456789
     * __23445___  Resulting condition
     */
    public void testUnequalBarrierWidths() throws Exception {
        // Place blocking cell
        placeNumberedCell(2);
        placeNumberedCell(3);
        placeNumberedCell(5);

        Coordinate target = new Coordinate2D(3, 0, 0);
        ArrayList<Coordinate> targets = new ArrayList<>(1);
        targets.add(target);
        parent.trigger("replicate-self", null);

        checkPosition(2, 2);
        checkPosition(3, 3);
        checkPosition(4, 4);
        checkPosition(5, 4);
        checkPosition(6, 5);
    }


    private MockTargetRule placeNumberedCell(int x) throws Exception {
        Supplier<BehaviorCell> supplier = mock(Supplier.class);
        when(supplier.get()).thenReturn(new MockCell(x));
        BehaviorCell cell = new BehaviorCell(layerManager, x, x, x, supplier);
        Coordinate coord = new Coordinate2D(x, 0, 0);
        layer.getUpdateManager().place(cell, coord);
        BehaviorDispatcher bd = new BehaviorDispatcher();
        cell.setDispatcher(bd);

        MockTargetRule targetRule = new MockTargetRule();

        // Cells always divide to the right
        ArrayList<Coordinate> targets = new ArrayList<>(1);
        Coordinate target = new Coordinate2D(x + 1, 0, 0);
        targets.add(target);
        targetRule.setTargets(targets);

        Expand expand = new Expand(cell, layerManager, null, null, random);

        Action behavior = new CompoundAction(cell, layerManager, new Action[]{expand});
        bd.map("replicate-self", behavior);

        return targetRule;
    }

    private void checkPosition(int x, int state) {
        Coordinate c = new Coordinate2D(x, 0, 0);
        Cell cell = layer.getViewer().getCell(c);
        assertEquals(state, cell.getState());
    }
}