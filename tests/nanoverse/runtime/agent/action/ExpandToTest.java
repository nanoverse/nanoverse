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

package nanoverse.runtime.agent.action;

import nanoverse.runtime.agent.Agent;
import nanoverse.runtime.agent.control.BehaviorDispatcher;
import nanoverse.runtime.agent.targets.MockTargetRule;
import nanoverse.runtime.control.identifiers.*;
import nanoverse.runtime.layers.MockLayerManager;
import nanoverse.runtime.layers.cell.AgentLayer;
import nanoverse.runtime.structural.MockRandom;
import org.junit.*;
import test.LegacyTest;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Functional test for the ExpandTo action, which utilizes a path-of-least-
 * resistance preferential division algorithm.
 */
public class ExpandToTest extends LegacyTest {

    private MockLayerManager layerManager;
    private Agent parent;
    private MockRandom random;
    private AgentLayer layer;
    private MockTargetRule parentTargetRule;

    @Before
    public void setUp() throws Exception {
        fail("Rewrite as a modern test");
//        Lattice lattice = new RectangularLattice();
//        layerManager = new MockLayerManager();
//        Shape shape = new Rectangle(lattice, 10, 1);
//        Boundary boundary = new Periodic(shape, lattice);
//        Geometry geom = new Geometry(lattice, shape, boundary);
//        layer = new AgentLayer(geom);
//        layerManager.setAgentLayer(layer);
//        random = new MockRandom();
//
//        // Place the parent at site 4 and get its target rule
//        parentTargetRule = placeNumberedAgent(4);
//        parent = (Agent) layer.getViewer().getAgent(new Coordinate2D(4, 0, 0));
//
//        // A cell exists in position 5 for all cases
//        placeNumberedAgent(5);
    }

    private MockTargetRule placeNumberedAgent(int x) throws Exception {
//        Agent cell = makeNumberedAgent(x);
//        Coordinate coord = new Coordinate2D(x, 0, 0);
//        layer.getUpdateManager().place(cell, coord);
//        BehaviorDispatcher bd = new BehaviorDispatcher();
//        cell.setDispatcher(bd);
//
//        MockTargetRule targetRule = new MockTargetRule();
//
//        // Agents always divide to the right
//        List<Coordinate> targets = new ArrayList<>(1);
//        Coordinate target = new Coordinate2D(x + 1, 0, 0);
//        targets.add(target);
//        targetRule.setTargets(targets);
//
//        ExpandTo expandTo = new ExpandTo(cell, layerManager, targetRule,
//            null, null, random);
//
//        Action behavior = new CompoundAction(cell, layerManager, new Action[]{expandTo});
//        bd.map("replicate-self", behavior);
//
//        return targetRule;
        return null;
    }

    private Agent makeNumberedAgent(int x) throws Exception {
        fail("Rewrite as a modern test");
//        Supplier<Agent> supplier = mock(Supplier.class);
//        when(supplier.get()).thenReturn(new Agent(layerManager, x, x, x, supplier));
//        return new Agent(layerManager, x, x, x, supplier);
        return null;
    }

    /**
     * Parent and target sites have adjacent vacancy; divide toward vacancy.
     * Population should grow in direction of vacancy.
     * <p>
     * 0123456789
     * ____45____  Initial condition
     * <^       Agent 4 divides left
     * <p>
     * 0123456789
     * ___445____  Resulting condition
     */
    @Test
    public void testOutwardSymmetricDisplacement() throws Exception {
        fail("Rewrite as a modern test");
//        Coordinate target = new Coordinate2D(3, 0, 0);
//        List<Coordinate> targets = new ArrayList<>(1);
//        targets.add(target);
//        parentTargetRule.setTargets(targets);
//        parent.trigger("replicate-self", null);
//
//        checkPosition(3, 4);
//        checkPosition(4, 4);
//        checkPosition(5, 5);
    }

    private void checkPosition(int x, int state) {
//        Coordinate c = new Coordinate2D(x, 0, 0);
//        Agent agent = layer.getViewer().getAgent(c);
//        assertEquals(state, agent.getState());
    }

    /**
     * Parent and target sites have adjacent vacancies; divide toward
     * interior. Parent gets shoved; result should be shifted in direction
     * of parent vacancy.
     * <p>
     * 0123456789
     * ____45____  Initial condition
     * ^>      Agent 4 divides right. Parent and target are equidistant
     * from vacancies, and the coin toss favors parent (4)
     * getting shoved.
     * <p>
     * 0123456789
     * ___445____  Resulting condition
     */
    @Test
    public void testInwardSymmetricParentDisplacement() throws Exception {
        fail("Rewrite");
//        Coordinate target = new Coordinate2D(5, 0, 0);
//        List<Coordinate> targets = new ArrayList<>(1);
//        targets.add(target);
//        parentTargetRule.setTargets(targets);
//
//        // The coin toss arbitrarily favors shoving parent on true.
//        random.setBooleanValue(true);
//        parent.trigger("replicate-self", null);
//
//        checkPosition(3, 4);
//        checkPosition(4, 4);
//        checkPosition(5, 5);
    }

    /**
     * Parent and target sites have adjacent vacancies; divide toward
     * interior. Target gets shoved; result should be shifted in direction
     * of target vacancy.
     * <p>
     * 0123456789
     * ____45____  Initial condition
     * ^>      Agent 4 divides right. Parent and target are equidistant
     * from vacancies, and the coin toss favors target (5)
     * getting shoved.
     * <p>
     * 0123456789
     * ____445___  Resulting condition
     */
    @Test
    public void testInwardSymmetricTargetDisplacement() throws Exception {
        fail("Rewrite");
//        Coordinate target = new Coordinate2D(5, 0, 0);
//        List<Coordinate> targets = new ArrayList<>(1);
//        targets.add(target);
//        parentTargetRule.setTargets(targets);
//
//        // The coin toss arbitrarily favors shoving parent on true.
//        random.setBooleanValue(false);
//        parent.trigger("replicate-self", null);
//
//        checkPosition(4, 4);
//        checkPosition(5, 4);
//        checkPosition(6, 5);
    }

    /**
     * Parent has an adjacent vacancy; target site does not. Divide toward
     * interior. Minimum distance from target site to vacancy is greater
     * than the minimum distance from parent site to vacancy. Therefore,
     * the parent will get shoved despite having divided toward the interior.
     * <p>
     * 0123456789
     * ____456___  Initial condition
     * ^>      Agent 4 divides right. Parent is closer to a vacancy than
     * child. Parent gets shoved.
     * <p>
     * 0123456789
     * ___4456___  Resulting condition
     */
    @Test
    public void testInwardAsymmetricDisplacement() throws Exception {
        fail("Rewrite");
//        // A cell exists in position 5 for all cases
//        placeNumberedAgent(6);
//
//        Coordinate target = new Coordinate2D(5, 0, 0);
//        List<Coordinate> targets = new ArrayList<>(1);
//        targets.add(target);
//        parentTargetRule.setTargets(targets);
//
//        // The coin toss arbitrarily favors shoving parent on true.
//        random.setBooleanValue(true);
//        parent.trigger("replicate-self", null);
//
//        checkPosition(3, 4);
//        checkPosition(4, 4);
//        checkPosition(5, 5);
//        checkPosition(6, 6);
    }
}