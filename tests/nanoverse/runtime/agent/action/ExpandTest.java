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
import nanoverse.runtime.agent.BehaviorAgent;
import nanoverse.runtime.agent.control.BehaviorDispatcher;
import nanoverse.runtime.agent.targets.MockTargetRule;
import nanoverse.runtime.cells.*;
import nanoverse.runtime.control.identifiers.*;
import nanoverse.runtime.geometry.Geometry;
import nanoverse.runtime.geometry.boundaries.*;
import nanoverse.runtime.geometry.lattice.*;
import nanoverse.runtime.geometry.shape.*;
import nanoverse.runtime.layers.MockLayerManager;
import nanoverse.runtime.layers.cell.AgentLayer;
import nanoverse.runtime.structural.MockRandom;
import org.junit.*;
import test.LegacyTest;

import java.util.ArrayList;
import java.util.function.Supplier;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ExpandTest extends LegacyTest {

    private MockLayerManager layerManager;
    private BehaviorAgent parent;
    private MockRandom random;
    private AgentLayer layer;
    private MockTargetRule parentTargetRule;

    @Before
    public void setUp() throws Exception {

        Lattice lattice = new RectangularLattice();
        layerManager = new MockLayerManager();
        Shape shape = new Rectangle(lattice, 10, 1);
        Boundary boundary = new Periodic(shape, lattice);
        Geometry geom = new Geometry(lattice, shape, boundary);
        layer = new AgentLayer(geom);
        layerManager.setAgentLayer(layer);
        random = new MockRandom();

        // Place the parent at site 4 and get its target rule
        parentTargetRule = placeNumberedAgent(4);
        parent = (BehaviorAgent) layer.getViewer().getAgent(new Coordinate2D(4, 0, 0));

    }

    private MockTargetRule placeNumberedAgent(int x) throws Exception {
        Supplier<BehaviorAgent> supplier = mock(Supplier.class);
        when(supplier.get()).thenReturn(new MockAgent(x));
        BehaviorAgent cell = new BehaviorAgent(layerManager, x, x, x, supplier);
        Coordinate coord = new Coordinate2D(x, 0, 0);
        layer.getUpdateManager().place(cell, coord);
        BehaviorDispatcher bd = new BehaviorDispatcher();
        cell.setDispatcher(bd);

        MockTargetRule targetRule = new MockTargetRule();

        // Agents always divide to the right
        ArrayList<Coordinate> targets = new ArrayList<>(1);
        Coordinate target = new Coordinate2D(x + 1, 0, 0);
        targets.add(target);
        targetRule.setTargets(targets);

        Expand expand = new Expand(cell, layerManager, null, null, random);

        Action behavior = new CompoundAction(cell, layerManager, new Action[]{expand});
        bd.map("replicate-self", behavior);

        return targetRule;
    }

    /**
     * Nearest vacancy is left; cell divides left.
     * <p>
     * 0123456789
     * ____45____  Initial condition
     * ^       AbstractAgent 4 divides left
     * <p>
     * 0123456789
     * ___445____  Resulting condition
     */
    @Test
    public void testOneVacancy() throws Exception {
        // Place blocking cell
        placeNumberedAgent(5);

        Coordinate target = new Coordinate2D(3, 0, 0);
        ArrayList<Coordinate> targets = new ArrayList<>(1);
        targets.add(target);
        parentTargetRule.setTargets(targets);
        parent.trigger("replicate-self", null);

        checkPosition(3, 4);
        checkPosition(4, 4);
        checkPosition(5, 5);
    }

    private void checkPosition(int x, int state) {
        Coordinate c = new Coordinate2D(x, 0, 0);
        AbstractAgent agent = layer.getViewer().getAgent(c);
        assertEquals(state, agent.getState());
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
    @Test
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
    @Test
    public void testUnequalBarrierWidths() throws Exception {
        // Place blocking cell
        placeNumberedAgent(2);
        placeNumberedAgent(3);
        placeNumberedAgent(5);

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
}