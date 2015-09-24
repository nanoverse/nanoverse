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

package agent.action;

import agent.control.BehaviorDispatcher;
import agent.targets.MockTargetRule;
import cells.*;
import control.identifiers.*;
import geometry.Geometry;
import geometry.boundaries.*;
import geometry.lattice.*;
import geometry.shape.*;
import layers.MockLayerManager;
import layers.cell.CellLayer;
import org.junit.*;
import structural.MockRandom;
import test.EslimeTestCase;

import java.util.ArrayList;
import java.util.function.Supplier;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
/**
 * Created by annie on 3/3/15.
 */
public class ExpandRandomTest extends EslimeTestCase {

    private MockLayerManager layerManager;
    private BehaviorCell parent;
    private MockRandom random;
    private CellLayer layer;
    private MockTargetRule parentTargetRule;

    @Before
    public void setUp() throws Exception {

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

    private MockTargetRule placeNumberedCell(int x) throws Exception {
        Supplier<BehaviorCell> ncSupplier = mock(Supplier.class);
        BehaviorCell child = new MockCell(x);
        when(ncSupplier.get()).thenReturn(child);
        BehaviorCell cell = new BehaviorCell(layerManager, x, x, x, ncSupplier);
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
     *
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
        Cell cell = layer.getViewer().getCell(c);
        assertEquals(state, cell.getState());
    }

    /**
     * Cell divides left and shoves.
     * <p>
     * 0123456789
     * _1234_____  Initial condition
     * ^       Cell 4 divides left
     * <p>
     * 0123456789
     * 12344_____  Resulting condition
     */
    @Test
    public void testVacancySameDirection() throws Exception {
        placeNumberedCell(1);
        placeNumberedCell(2);
        placeNumberedCell(3);

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