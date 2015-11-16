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

public class RectangularLatticeTest extends LegacyTest {

    private Lattice lattice;

    @Before
    public void setUp() throws Exception {
        lattice = new RectangularLattice();
    }

    @Test
    public void testDimensionality() {
        assertEquals(2, lattice.getDimensionality());
    }

    @Test
    public void testConnectivity() {
        assertEquals(2, lattice.getConnectivity());
    }

//    @Test
//    public void testAdjust() {
//        Coordinate initial, actual, expected;
//
//        // The origin should be unaffected
//        initial = new Coordinate2D(0, 0, 0);
//        actual = lattice.adjust(initial);
//        expected = new Coordinate2D(0, 0, 0);
//        assertEquals(actual, expected);
//
//        // Y offset should be unaffected
//        initial = new Coordinate2D(0, 8, 0);
//        actual = lattice.adjust(initial);
//        expected = new Coordinate2D(0, 8, 0);
//        assertEquals(actual, expected);
//
//        // X offset should be unaffected
//        initial = new Coordinate2D(8, 0, 0);
//        actual = lattice.adjust(initial);
//        expected = new Coordinate2D(8, 0, 0);
//        assertEquals(actual, expected);
//    }

    @Test
    public void testGetAnnulus() {
        Coordinate[] actual, expected;
        Coordinate origin = new Coordinate2D(0, 0, 0);

        // r=0
        actual = lattice.getAnnulus(origin, 0);
        expected = new Coordinate[]{origin};
        assertArraysEqual(actual, expected, true);

        // r=1
        actual = lattice.getAnnulus(origin, 1);
        expected = new Coordinate[]{new Coordinate2D(1, 0, 0),
            new Coordinate2D(-1, 0, 0),
            new Coordinate2D(0, 1, 0),
            new Coordinate2D(0, -1, 0)};

        assertArraysEqual(actual, expected, true);

        // r=2
        actual = lattice.getAnnulus(origin, 2);
        expected = new Coordinate[]{new Coordinate2D(2, 0, 0),
            new Coordinate2D(1, 1, 0),
            new Coordinate2D(0, 2, 0),
            new Coordinate2D(-1, 1, 0),
            new Coordinate2D(-2, 0, 0),
            new Coordinate2D(-1, -1, 0),
            new Coordinate2D(0, -2, 0),
            new Coordinate2D(1, -1, 0)};

        assertArraysEqual(actual, expected, true);
    }


    @Test
    public void testGetNeighbors() {
        Coordinate[] actual, expected;
        Coordinate origin = new Coordinate2D(0, 0, 0);

        // Should be same as r=1 annulus
        actual = lattice.getNeighbors(origin);
        expected = new Coordinate[]{new Coordinate2D(1, 0, 0),
            new Coordinate2D(-1, 0, 0),
            new Coordinate2D(0, 1, 0),
            new Coordinate2D(0, -1, 0)};

        assertArraysEqual(expected, actual, true);
    }

    @Test
    public void testGetDisplacement() {
        Coordinate o, p, q, r;
        Coordinate expected, actual;

        o = new Coordinate2D(0, 0, 0);
        p = new Coordinate2D(3, 0, 0);
        q = new Coordinate2D(0, 3, 0);
        r = new Coordinate2D(3, 3, 0);

        // Horizontal
        actual = lattice.getDisplacement(o, p);
        expected = new Coordinate2D(3, 0, Flags.VECTOR);
        assertEquals(expected, actual);

        // Vertical
        actual = lattice.getDisplacement(o, q);
        expected = new Coordinate2D(0, 3, Flags.VECTOR);
        assertEquals(expected, actual);

        // Both horizontal and vertical
        actual = lattice.getDisplacement(o, r);
        expected = new Coordinate2D(3, 3, Flags.VECTOR);
        assertEquals(expected, actual);
    }

    @Test
    public void testRel2Abs() {
        Coordinate o, p, q;
        Coordinate actual, expected;

        o = new Coordinate2D(0, 0, 0);
        p = new Coordinate2D(3, 0, 0);
        q = new Coordinate2D(0, 3, 0);

        Coordinate op, oq;
        op = new Coordinate2D(3, 0, Flags.VECTOR);
        oq = new Coordinate2D(0, 3, Flags.VECTOR);

        // Horizontal
        actual = lattice.rel2abs(o, op);
        expected = p;
        assertEquals(expected, actual);

        // Vertical
        actual = lattice.rel2abs(o, oq);
        expected = q;
        assertEquals(expected, actual);
    }

    @Test
    public void testGetNeighborhoodDistance() {
        Coordinate o, p, q, r;
        int expected, actual;

        o = new Coordinate2D(0, 0, 0);
        p = new Coordinate2D(3, 0, 0);
        q = new Coordinate2D(0, -3, 0);
        r = new Coordinate2D(-3, 3, 0);

        // Horizontal
        actual = lattice.getNeighborhoodDistance(o, p);
        expected = 3;
        assertEquals(expected, actual);

        // Vertical
        actual = lattice.getNeighborhoodDistance(o, q);
        expected = 3;
        assertEquals(expected, actual);

        // Both horizontal and vertical
        actual = lattice.getNeighborhoodDistance(o, r);
        expected = 6;
        assertEquals(expected, actual);
    }

    @Test
    public void testOrthoDisplacement() {
        Coordinate o, p, q, r;
        Coordinate expected, actual;

        o = new Coordinate2D(0, 0, 0);
        p = new Coordinate2D(3, 0, 0);
        q = new Coordinate2D(0, 3, 0);
        r = new Coordinate2D(3, 3, 0);

        // Horizontal
        actual = lattice.getOrthoDisplacement(o, p);
        expected = new Coordinate2D(3, 0, Flags.VECTOR);
        assertEquals(expected, actual);

        // Vertical
        actual = lattice.getOrthoDisplacement(o, q);
        expected = new Coordinate2D(0, 3, Flags.VECTOR);
        assertEquals(expected, actual);

        // Both horizontal and vertical
        actual = lattice.getOrthoDisplacement(o, r);
        expected = new Coordinate2D(3, 3, Flags.VECTOR);
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
        Coordinate expected = new Coordinate2D(0, 0, 0);
        Coordinate actual = lattice.getZeroVector();
        assertEquals(expected, actual);
    }
}
