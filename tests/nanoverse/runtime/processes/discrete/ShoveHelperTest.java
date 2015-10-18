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

package nanoverse.runtime.processes.discrete;

import nanoverse.runtime.layers.cell.AgentLayer;
import org.junit.*;
import test.LegacyTest;

import static org.junit.Assert.*;

public class ShoveHelperTest extends LegacyTest {

    private AgentLayer layer;
    private ShoveHelper query;

    @Before
    public void setUp() throws Exception {
        fail("Rewrite as a modern test");
        // Create a 10x1 rectangular, 2D nanoverse.runtime.geometry
//        Lattice lattice = new RectangularLattice();
//        Shape shape = new Rectangle(lattice, 10, 1);
//        Boundary boundary = new Periodic(shape, lattice);
//        Geometry geom = new Geometry(lattice, shape, boundary);
//        MockLayerManager lm = new MockLayerManager();
//        layer = new AgentLayer(geom);
//        lm.setAgentLayer(layer);
//        placeAgents();
//
//        Random random = new Random(RANDOM_SEED);
//
//        query = new ShoveHelper(lm, random);
    }

    private void placeAgents() throws Exception {
        for (int x = 0; x < 7; x++) {
            placeNumberedAgent(x);
        }

        for (int x = 8; x <= 9; x++) {
            placeNumberedAgent(x);
        }
    }

    private void placeNumberedAgent(int x) throws Exception {
//        MockAgent cell = new MockAgent(x);
//        Coordinate coord = new Coordinate2D(x, 0, 0);
//        layer.getUpdateManager().place(cell, coord);
    }

    /**
     * This test will create a linear nanoverse.runtime.geometry that has one vacancy. A line
     * of nanoverse.runtime.cells will be pushed toward this vacancy, moving the vacancy to the
     * origin of the shove. This does not test all of the behavior of this
     * process, but it's a start.
     * <p>
     * So here is what is supposed to happen:
     * <p>
     * 0123456_89  Initial condition
     * ^       (Agent to be shoved)
     * <p>
     * 0123_45689  Result
     */
    @Test
    public void test1Dshove() throws Exception {
        fail("Rewrite as a modern test");
//        Coordinate target = new Coordinate2D(7, 0, 0);
//        Coordinate origin = new Coordinate2D(4, 0, 0);
//        query.shove(origin, target);
//
//        int[] leftSeq = new int[]{0, 1, 2, 3};
//        int[] rightSeq = new int[]{4, 5, 6, 8, 9};
//
//        for (int x = 0; x < 4; x++) {
//            Coordinate c = new Coordinate2D(x, 0, 0);
//            Agent observed = layer.getViewer().getAgent(c);
//            int expected = leftSeq[x];
//            int actual = observed.getState();
//            assertEquals(expected, actual);
//        }
//
//        for (int x = 0; x < 5; x++) {
//            Coordinate c = new Coordinate2D(x + 5, 0, 0);
//            Agent observed = layer.getViewer().getAgent(c);
//            int expected = rightSeq[x];
//            int actual = observed.getState();
//            assertEquals(expected, actual);
//        }
    }

    @Test
    public void test3Dshove() throws Exception {
        fail("Rewrite");
        // Create a 10x1 rectangular, 2D nanoverse.runtime.geometry
//        Lattice lattice = new CubicLattice();
//        Shape shape = new Cuboid(lattice, 5, 5, 5);
//        Boundary boundary = new Absorbing(shape, lattice);
//        Geometry geom = new Geometry(lattice, shape, boundary);
//        MockLayerManager lm = new MockLayerManager();
//        layer = new AgentLayer(geom);
//        lm.setAgentLayer(layer);
//        Random random = new MockRandom();
//        query = new ShoveHelper(lm, random);
//
//        for (int x = 0; x < 4; x++) {
//            for (int y = 0; y < 4; y++) {
//                for (int z = 0; z < 4; z++) {
//                    Agent agent = new MockAgent(1);
//                    Coordinate coord = new Coordinate3D(x, y, z, 0);
//                    layer.getUpdateManager().place(agent, coord);
//                }
//            }
//        }
//
//        Coordinate origin = new Coordinate3D(1, 2, 3, 0);
//        Coordinate target = new Coordinate3D(4, 4, 4, 0);
//
//        HashSet<Coordinate> affected = query.shove(origin, target);
//
//        // Algorithm will prefer z, then y, then x. So the shoving should
//        // progress like this:
//        assertTrue(affected.contains(new Coordinate3D(1, 2, 4, 0)));
//        assertTrue(affected.contains(new Coordinate3D(1, 3, 4, 0)));
//        assertTrue(affected.contains(new Coordinate3D(1, 4, 4, 0)));
//        assertTrue(affected.contains(new Coordinate3D(2, 4, 4, 0)));
//        assertTrue(affected.contains(new Coordinate3D(3, 4, 4, 0)));
//        assertTrue(affected.contains(new Coordinate3D(4, 4, 4, 0)));
//
//        // Having shoved, the origin should now be vacant.
//        assertFalse(layer.getViewer().isOccupied(origin));
    }

    @Test
    public void testGetTarget() throws Exception {
        fail("Rewrite");
//        Coordinate origin = new Coordinate2D(4, 0, 0);
//
//        Coordinate expected = new Coordinate2D(7, 0, 0);
//        Coordinate actual = query.chooseVacancy(origin);
//        assertEquals(expected, actual);
    }

    @Test
    public void testRemoveImaginary() throws Exception {
        fail("Rewrite");
//        Lattice lattice = new RectangularLattice();
//        Shape shape = new Rectangle(lattice, 10, 1);
//        Boundary boundary = new Arena(shape, lattice);
//        Geometry geom = new Geometry(lattice, shape, boundary);
//        MockLayerManager lm = new MockLayerManager();
//        layer = new AgentLayer(geom);
//        lm.setAgentLayer(layer);
//        placeAgents();
//        Random random = new Random(RANDOM_SEED);
//        query = new ShoveHelper(lm, random);
//        MockAgent cell = new MockAgent(1);
//        layer.getUpdateManager().place(cell, new Coordinate2D(-1, 0, 0));
//        assertEquals(1, layer.getViewer().getImaginarySites().size());
//        query.removeImaginary();
//        assertEquals(0, layer.getViewer().getImaginarySites().size());
    }

    /**
     * ensure that the displacement vector between each cell in the shoving
     * path is the same for random shoving.
     *
     * @throws Exception
     */
    @Test
    public void test1DShoveRandom() throws Exception {
        fail("rewrite");
//        Lattice lattice = new RectangularLattice();
//        Shape shape = new Rectangle(lattice, 10, 1);
//        Boundary boundary = new Periodic(shape, lattice);
//        Geometry geom = new Geometry(lattice, shape, boundary);
//        MockLayerManager lm = new MockLayerManager();
//        layer = new AgentLayer(geom);
//        lm.setAgentLayer(layer);
//        Random random = new MockRandom();
//        query = new ShoveHelper(lm, random);
//
//        // initial name: _1234567__
//        for (int x = 1; x < 8; x++) {
//            Agent agent = new MockAgent(1);
//            Coordinate coord = new Coordinate2D(x, 0, 0);
//            layer.getUpdateManager().place(agent, coord);
//        }
//
//        Coordinate origin = new Coordinate2D(4, 0, 0);
//        HashSet<Coordinate> affectedSites = query.shoveRandom(origin);
//
//        // make sure displacement vector between each site is the same
//        Coordinate[] affectedArray = affectedSites.toArray(new Coordinate2D[0]);
//        Arrays.sort(affectedArray);
//        Coordinate[] displacements = new Coordinate2D[affectedArray.length - 1];
//        for (int i = 0; i < affectedArray.length - 1; i++) {
//            displacements[i] = lm.getAgentLayer().getGeometry().
//                getDisplacement(affectedArray[i],
//                    affectedArray[i + 1], Geometry.APPLY_BOUNDARIES);
//        }
//        Coordinate displacement = displacements[0];
//        for (int j = 0; j < displacements.length; j++) {
//            assertEquals(displacement, displacements[j]);
//        }
//
//        // Having shoved, the origin should now be vacant.
//        assertFalse(layer.getViewer().isOccupied(origin));
    }

}