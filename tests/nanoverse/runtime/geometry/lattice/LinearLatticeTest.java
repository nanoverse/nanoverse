/*
 * Nanoverse: a declarative agent-based modeling language for natural and
 * social science.
 *
 * Copyright (c) 2015 David Bruce Borenstein and Nanoverse, LLC.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package nanoverse.runtime.geometry.lattice;

import nanoverse.runtime.control.identifiers.*;
import org.junit.*;
import test.LegacyTest;

import static org.junit.Assert.*;

public class LinearLatticeTest extends LegacyTest {
    private Lattice lattice;

    @Before
    public void setUp() throws Exception {
        lattice = new LinearLattice();
    }

    @Test
    public void testDimensionality() {
        assertEquals(1, lattice.getDimensionality());
    }

    @Test
    public void testConnectivity() {
        assertEquals(1, lattice.getConnectivity());
    }

//    @Test
//    public void testAdjust() {
//        Coordinate initial, actual, expected;
//
//        // The origin should be unaffected
//        initial = new Coordinate1D(0, 0);
//        actual = lattice.adjust(initial);
//        expected = new Coordinate1D(0, 0);
//        assertEquals(actual, expected);
//
//        // Y offset should be unaffected
//        initial = new Coordinate1D(8, 0);
//        actual = lattice.adjust(initial);
//        expected = new Coordinate1D(8, 0);
//        assertEquals(actual, expected);
//
//    }

    @Test
    public void testGetAnnulus() {
        Coordinate[] actual, expected;
        Coordinate origin = new Coordinate1D(0, 0);

        // r=0
        actual = lattice.getAnnulus(origin, 0);
        expected = new Coordinate[]{origin};
        assertArraysEqual(actual, expected, true);

        // r=1
        actual = lattice.getAnnulus(origin, 1);
        expected = new Coordinate[]{
            new Coordinate1D(1, 0),
            new Coordinate1D(-1, 0)
        };

        assertArraysEqual(actual, expected, true);

        // r=2
        actual = lattice.getAnnulus(origin, 2);
        expected = new Coordinate[]{
            new Coordinate1D(2, 0),
            new Coordinate1D(-2, 0),
        };

        assertArraysEqual(actual, expected, true);
    }


    @Test
    public void testGetNeighbors() {
        Coordinate[] actual, expected;
        Coordinate origin = new Coordinate1D(0, 0);

        // Should be same as r=1 annulus
        actual = lattice.getNeighbors(origin);
        expected = new Coordinate[]{
            new Coordinate1D(1, 0),
            new Coordinate1D(-1, 0)
        };

        assertArraysEqual(expected, actual, true);
    }

    @Test
    public void testGetDisplacement() {
        Coordinate o, q;
        Coordinate expected, actual;

        o = new Coordinate1D(0, 0);
        q = new Coordinate1D(3, 0);

        // Vertical
        actual = lattice.getDisplacement(o, q);
        expected = new Coordinate1D(3, Flags.VECTOR);
        assertEquals(expected, actual);

    }

    @Test
    public void testRel2Abs() {
        Coordinate o, q;
        Coordinate actual, expected;

        o = new Coordinate1D(0, 0);
        q = new Coordinate1D(3, 0);

        Coordinate oq;
        oq = new Coordinate1D(3, Flags.VECTOR);

        // Vertical
        actual = lattice.rel2abs(o, oq);
        expected = q;
        assertEquals(expected, actual);
    }

    @Test
    public void testGetNeighborhoodDistance() {
        Coordinate o, q;
        int expected, actual;

        o = new Coordinate1D(0, 0);
        q = new Coordinate1D(-3, 0);


        // Vertical
        actual = lattice.getNeighborhoodDistance(o, q);
        expected = 3;
        assertEquals(expected, actual);
    }

    @Test
    public void testClone() {
        Object cloned = lattice.clone();
        assertEquals(lattice.getClass(), cloned.getClass());
        assertFalse(lattice == cloned);
    }

    @Test
    public void testGetZeroVector() {
        Coordinate expected = new Coordinate1D(0, 0);
        Coordinate actual = lattice.getZeroVector();
        assertEquals(expected, actual);
    }

}