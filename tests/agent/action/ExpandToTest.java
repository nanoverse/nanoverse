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
import layers.MockLayerManager;
import layers.cell.CellLayer;
import structural.MockRandom;
import test.EslimeTestCase;

import java.util.ArrayList;
import java.util.List;

/**
 * Functional test for the ExpandTo action, which utilizes a path-of-least-
 * resistance preferential division algorithm.
 */
public class ExpandToTest extends EslimeTestCase {

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
        parent = (BehaviorCell) layer.getViewer().getCell(new Coordinate(4, 0, 0));

        // A cell exists in position 5 for all cases
        placeNumberedCell(5);
    }

    /**
     * Parent and target sites have adjacent vacancy; divide toward vacancy.
     * Population should grow in direction of vacancy.
     * <p>
     * 0123456789
     * ____45____  Initial condition
     * <^       Cell 4 divides left
     * <p>
     * 0123456789
     * ___445____  Resulting condition
     */
    public void testOutwardSymmetricDisplacement() throws Exception {
        Coordinate target = new Coordinate(3, 0, 0);
        List<Coordinate> targets = new ArrayList<>(1);
        targets.add(target);
        parentTargetRule.setTargets(targets);
        parent.trigger("replicate-self", null);

        checkPosition(3, 4);
        checkPosition(4, 4);
        checkPosition(5, 5);
    }

    /**
     * Parent and target sites have adjacent vacancies; divide toward
     * interior. Parent gets shoved; result should be shifted in direction
     * of parent vacancy.
     * <p>
     * 0123456789
     * ____45____  Initial condition
     * ^>      Cell 4 divides right. Parent and target are equidistant
     * from vacancies, and the coin toss favors parent (4)
     * getting shoved.
     * <p>
     * 0123456789
     * ___445____  Resulting condition
     */
    public void testInwardSymmetricParentDisplacement() throws Exception {
        Coordinate target = new Coordinate(5, 0, 0);
        List<Coordinate> targets = new ArrayList<>(1);
        targets.add(target);
        parentTargetRule.setTargets(targets);

        // The coin toss arbitrarily favors shoving parent on true.
        random.setBooleanValue(true);
        parent.trigger("replicate-self", null);

        checkPosition(3, 4);
        checkPosition(4, 4);
        checkPosition(5, 5);
    }

    /**
     * Parent and target sites have adjacent vacancies; divide toward
     * interior. Target gets shoved; result should be shifted in direction
     * of target vacancy.
     * <p>
     * 0123456789
     * ____45____  Initial condition
     * ^>      Cell 4 divides right. Parent and target are equidistant
     * from vacancies, and the coin toss favors target (5)
     * getting shoved.
     * <p>
     * 0123456789
     * ____445___  Resulting condition
     */
    public void testInwardSymmetricTargetDisplacement() throws Exception {
        Coordinate target = new Coordinate(5, 0, 0);
        List<Coordinate> targets = new ArrayList<>(1);
        targets.add(target);
        parentTargetRule.setTargets(targets);

        // The coin toss arbitrarily favors shoving parent on true.
        random.setBooleanValue(false);
        parent.trigger("replicate-self", null);

        checkPosition(4, 4);
        checkPosition(5, 4);
        checkPosition(6, 5);
    }

    /**
     * Parent has an adjacent vacancy; target site does not. Divide toward
     * interior. Minimum distance from target site to vacancy is greater
     * than the minimum distance from parent site to vacancy. Therefore,
     * the parent will get shoved despite having divided toward the interior.
     * <p>
     * 0123456789
     * ____456___  Initial condition
     * ^>      Cell 4 divides right. Parent is closer to a vacancy than
     * child. Parent gets shoved.
     * <p>
     * 0123456789
     * ___4456___  Resulting condition
     */
    public void testInwardAsymmetricDisplacement() throws Exception {
        // A cell exists in position 5 for all cases
        placeNumberedCell(6);

        Coordinate target = new Coordinate(5, 0, 0);
        List<Coordinate> targets = new ArrayList<>(1);
        targets.add(target);
        parentTargetRule.setTargets(targets);

        // The coin toss arbitrarily favors shoving parent on true.
        random.setBooleanValue(true);
        parent.trigger("replicate-self", null);

        checkPosition(3, 4);
        checkPosition(4, 4);
        checkPosition(5, 5);
        checkPosition(6, 6);
    }

    private MockTargetRule placeNumberedCell(int x) throws Exception {
        BehaviorCell cell = new BehaviorCell(layerManager, x, x, x, null);
        Coordinate coord = new Coordinate(x, 0, 0);
        layer.getUpdateManager().place(cell, coord);
        BehaviorDispatcher bd = new BehaviorDispatcher();
        cell.setDispatcher(bd);

        MockTargetRule targetRule = new MockTargetRule();

        // Cells always divide to the right
        List<Coordinate> targets = new ArrayList<>(1);
        Coordinate target = new Coordinate(x + 1, 0, 0);
        targets.add(target);
        targetRule.setTargets(targets);

        ExpandTo expandTo = new ExpandTo(cell, layerManager, targetRule,
                null, null, random);

        Behavior behavior = new Behavior(cell, layerManager, new Action[]{expandTo});
        bd.map("replicate-self", behavior);

        return targetRule;
    }

    private void checkPosition(int x, int state) {
        Coordinate c = new Coordinate(x, 0, 0);
        Cell cell = layer.getViewer().getCell(c);
        assertEquals(state, cell.getState());
    }
}