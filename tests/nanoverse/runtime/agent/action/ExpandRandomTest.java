/*
 * Copyright (c) 2014, 2015 David Bruce Borenstein, Annie Maslan,
 * and the Trustees of Princeton University.
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

/**
 * Created by annie on 3/3/15.
 */
public class ExpandRandomTest extends LegacyTest {

    private MockLayerManager layerManager;
    private Agent parent;
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
        parent = (Agent) layer.getViewer().getAgent(new Coordinate2D(4, 0, 0));

    }

    private MockTargetRule placeNumberedAgent(int x) throws Exception {
        Supplier<Agent> ncSupplier = mock(Supplier.class);
        Agent child = new MockAgent(x);
        when(ncSupplier.get()).thenReturn(child);
        Agent cell = new Agent(layerManager, x, x, x, ncSupplier);
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

        ExpandRandom expandRandom = new ExpandRandom(cell, layerManager, null, null, random);

        Action behavior = new CompoundAction(cell, layerManager, new Action[]{expandRandom});
        bd.map("replicate-self", behavior);

        return targetRule;
    }

    /**
     * Direction shoved depends on random number value.
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
     * <p>
     * Since the difference between case 1 and case 2 is handled
     * in a helper object (ShoveHelper) which is testes separately,
     * we consider only case 1.
     */
    @Test
    public void testRandomShove() throws Exception {
        Coordinate target = new Coordinate2D(3, 0, 0);
        ArrayList<Coordinate> targets = new ArrayList<>(1);
        targets.add(target);
        parent.trigger("replicate-self", null);

        checkPosition(3, 4);
        checkPosition(4, 4);
    }

    private void checkPosition(int x, int state) {
        Coordinate c = new Coordinate2D(x, 0, 0);
        AbstractAgent agent = layer.getViewer().getAgent(c);
        assertEquals(state, agent.getState());
    }

    /**
     * AbstractAgent divides left and shoves.
     * <p>
     * 0123456789
     * _1234_____  Initial condition
     * ^       AbstractAgent 4 divides left
     * <p>
     * 0123456789
     * 12344_____  Resulting condition
     */
    @Test
    public void testVacancySameDirection() throws Exception {
        placeNumberedAgent(1);
        placeNumberedAgent(2);
        placeNumberedAgent(3);

        Coordinate target = new Coordinate2D(3, 0, 0);
        ArrayList<Coordinate> targets = new ArrayList<>(1);
        targets.add(target);
        parentTargetRule.setTargets(targets);
        parent.trigger("replicate-self", null);

        checkPosition(0, 1);
        checkPosition(1, 2);
        checkPosition(2, 3);
        checkPosition(3, 4);
        checkPosition(4, 4);
    }
}