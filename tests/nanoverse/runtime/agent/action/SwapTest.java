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

import nanoverse.runtime.agent.AbstractAgent;
import nanoverse.runtime.agent.Agent;
import nanoverse.runtime.agent.control.BehaviorDispatcher;
import nanoverse.runtime.agent.targets.MockTargetRule;
import nanoverse.runtime.control.identifiers.*;
import nanoverse.runtime.geometry.Geometry;
import nanoverse.runtime.geometry.boundaries.*;
import nanoverse.runtime.geometry.lattice.*;
import nanoverse.runtime.geometry.shape.*;
import nanoverse.runtime.layers.MockLayerManager;
import nanoverse.runtime.layers.cell.AgentLayer;
import nanoverse.runtime.structural.NotYetImplementedException;
import org.junit.*;
import test.LegacyTest;

import java.util.*;

import static org.junit.Assert.*;

public class SwapTest extends LegacyTest {

    private MockLayerManager layerManager;
    private Agent parent;
    private AgentLayer layer;
    private MockTargetRule parentTargetRule;

    @Before
    public void setUp() throws Exception {
//
//        Lattice lattice = new RectangularLattice();
//        layerManager = new MockLayerManager();
//        Shape shape = new Rectangle(lattice, 10, 1);
//        Boundary boundary = new Periodic(shape, lattice);
//        Geometry geom = new Geometry(lattice, shape, boundary);
//        layer = new AgentLayer(geom);
//        layerManager.setAgentLayer(layer);
//
//        // Place the parent at site 4 and get its target rule
//        parentTargetRule = placeNumberedAgent(4);
//        parent = (Agent) layer.getViewer().getAgent(new Coordinate2D(4, 0, 0));

    }

    private MockTargetRule placeNumberedAgent(int x) throws Exception {
        throw new NotYetImplementedException();
//        Agent cell = new Agent(layerManager, x, x, x, null);
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
//        Action action = new Swap(cell, layerManager, targetRule, null, null);
//
//        Action behavior = new CompoundAction(cell, layerManager, new Action[]{action});
//        bd.map("swap", behavior);
//
//        return targetRule;
    }

    /**
     * 0123456789
     * ____45____  Initial condition
     * ^^
     * 0123456789
     * ____54____  Resulting condition
     */
    @Test
    public void testTwoOccupied() throws Exception {
        fail("Rewrite as a modern test");
//        placeNumberedAgent(5);
//        Coordinate target = new Coordinate2D(5, 0, 0);
//        List<Coordinate> targets = new ArrayList<>(1);
//        targets.add(target);
//        parent.trigger("swap", null);
//
//        checkIsVacant(3);
//        checkPosition(4, 5);
//        checkPosition(5, 4);
//        checkIsVacant(6);
    }

    private void checkPosition(int x, int state) {
//        Coordinate c = new Coordinate2D(x, 0, 0);
//        AbstractAgent agent = layer.getViewer().getAgent(c);
//        assertEquals(state, agent.getState());
    }

    private void checkIsVacant(int x) {
        Coordinate c = new Coordinate2D(x, 0, 0);
        assertFalse(layer.getViewer().isOccupied(c));
    }

    /**
     * 0123456789
     * ____4_____  Initial condition
     * ^^
     * 0123456789
     * _____4____  Resulting condition
     */
    @Test
    public void testOneVacant() throws Exception {
        fail("Rewrite as a modern test");
//        Coordinate target = new Coordinate2D(5, 0, 0);
//        List<Coordinate> targets = new ArrayList<>(1);
//        targets.add(target);
//        parentTargetRule.setTargets(targets);
//        parent.trigger("swap", null);
//
//        checkIsVacant(3);
//        checkIsVacant(4);
//        checkPosition(5, 4);
    }

}