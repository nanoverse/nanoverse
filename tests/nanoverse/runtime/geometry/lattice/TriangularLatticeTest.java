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

package nanoverse.runtime.geometry.lattice;

import nanoverse.runtime.control.identifiers.*;
import org.junit.*;
import test.LegacyTest;

import static org.junit.Assert.*;

public class TriangularLatticeTest extends LegacyTest {

    private Lattice lattice;
    private Coordinate o, p, q, r, s, t;

    @Before
    public void setUp() throws Exception {
        lattice = new TriangularLattice();

        // Origin
        o = new Coordinate2D(0, 0, 0);

        // +u
        p = new Coordinate2D(3, 0, 0);

        // +v
        q = new Coordinate2D(3, 3, 0);

        // +w
        r = new Coordinate2D(0, 3, 0);

        // +u +v
        s = new Coordinate2D(6, 3, 0);

        // +u +v -w
        t = new Coordinate2D(6, 6, 0);
    }

    @Test
    public void testDimensionality() {
        assertEquals(2, lattice.getDimensionality());
    }

    @Test
    public void testConnectivity() {
        assertEquals(3, lattice.getConnectivity());
    }

    @Test
    public void testAdjust() {
        Coordinate initial, actual, expected;

        // The origin should be unaffected
        initial = new Coordinate2D(0, 0, 0);
        actual = lattice.adjust(initial);
        expected = new Coordinate2D(0, 0, 0);
        assertEquals(actual, expected);

        // Y offset should be unaffected
        initial = new Coordinate2D(0, 8, 0);
        actual = lattice.adjust(initial);
        expected = new Coordinate2D(0, 8, 0);
        assertEquals(actual, expected);

        // X-offset should get an adjustment to Y
        initial = new Coordinate2D(8, 0, 0);
        actual = lattice.adjust(initial);
        expected = new Coordinate2D(8, 4, 0);
        assertEquals(actual, expected);

        // (-1, 0) --> (-1, -1)
        initial = new Coordinate2D(-1, 0, 0);
        actual = lattice.adjust(initial);
        expected = new Coordinate2D(-1, -1, 0);
        assertEquals(expected, actual);

        // (-2, 0) --> (-2, -1)
        initial = new Coordinate2D(-2, 0, 0);
        actual = lattice.adjust(initial);
        expected = new Coordinate2D(-2, -1, 0);
        assertEquals(expected, actual);

        // (-3, 0) --> (-3, -2)
        initial = new Coordinate2D(-3, 0, 0);
        actual = lattice.adjust(initial);
        expected = new Coordinate2D(-3, -2, 0);
        assertEquals(expected, actual);
    }

    @Test
    public void testBasis() {
        Coordinate[] basis = lattice.getBasis();

        assertEquals(basis.length, 3);

        Coordinate southeast = new Coordinate2D(1, 0, 0);
        Coordinate northeast = new Coordinate2D(1, 1, 0);
        Coordinate north = new Coordinate2D(0, 1, 0);

        assertEquals(basis[0], southeast);
        assertEquals(basis[1], northeast);
        assertEquals(basis[2], north);
    }

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
        expected = new Coordinate2D[]{new Coordinate2D(0, 1, 0),
            new Coordinate2D(1, 1, 0),
            new Coordinate2D(1, 0, 0),
            new Coordinate2D(0, -1, 0),
            new Coordinate2D(-1, -1, 0),
            new Coordinate2D(-1, 0, 0)
        };

        assertArraysEqual(actual, expected, true);

        // r=2
        actual = lattice.getAnnulus(origin, 2);
        expected = new Coordinate2D[]{new Coordinate2D(0, 2, 0),
            new Coordinate2D(1, 2, 0),
            new Coordinate2D(2, 2, 0),
            new Coordinate2D(2, 1, 0),
            new Coordinate2D(2, 0, 0),
            new Coordinate2D(1, -1, 0),
            new Coordinate2D(0, -2, 0),
            new Coordinate2D(-1, -2, 0),
            new Coordinate2D(-2, -2, 0),
            new Coordinate2D(-2, -1, 0),
            new Coordinate2D(-2, 0, 0),
            new Coordinate2D(-1, 1, 0)
        };

        assertArraysEqual(actual, expected, true);
    }

    @Test
    public void testGetNeighbors() {
        Coordinate[] actual, expected;
        Coordinate origin = new Coordinate2D(0, 0, 0);

        // Should be same as r=1 annulus
        actual = lattice.getNeighbors(origin);
        expected = new Coordinate2D[]{new Coordinate2D(0, 1, 0),
            new Coordinate2D(1, 1, 0),
            new Coordinate2D(1, 0, 0),
            new Coordinate2D(0, -1, 0),
            new Coordinate2D(-1, -1, 0),
            new Coordinate2D(-1, 0, 0)
        };

        assertArraysEqual(expected, actual, true);
    }

    @Test
    public void testGetDisplacement() {
        Coordinate expected, actual;

        // +u
        actual = lattice.getDisplacement(o, p);
        expected = new Coordinate3D(3, 0, 0, Flags.VECTOR);
        assertEquals(actual, expected);

        // +v
        actual = lattice.getDisplacement(o, q);
        expected = new Coordinate3D(0, 3, 0, Flags.VECTOR);
        assertEquals(actual, expected);

        // +w
        actual = lattice.getDisplacement(o, r);
        expected = new Coordinate3D(0, 0, 3, Flags.VECTOR);
        assertEquals(actual, expected);

        // +u +v
        actual = lattice.getDisplacement(o, s);
        expected = new Coordinate3D(3, 3, 0, Flags.VECTOR);
        assertEquals(actual, expected);

        // +u +v +w�= +2v (tricky!)
        actual = lattice.getDisplacement(o, t);
        expected = new Coordinate3D(0, 6, 0, Flags.VECTOR);
        assertEquals(actual, expected);

    }

    @Test
    public void testGetRel2Abs() {
        Coordinate actual, expected;

        Coordinate op, oq, or, os, ot;
        op = new Coordinate3D(3, 0, 0, Flags.VECTOR);
        oq = new Coordinate3D(0, 3, 0, Flags.VECTOR);
        or = new Coordinate3D(0, 0, 3, Flags.VECTOR);
        os = new Coordinate3D(3, 3, 0, Flags.VECTOR);
        ot = new Coordinate3D(3, 3, 3, Flags.VECTOR);


        actual = lattice.rel2abs(o, op);
        expected = p;
        assertEquals(expected, actual);

        actual = lattice.rel2abs(o, oq);
        expected = q;
        assertEquals(expected, actual);

        actual = lattice.rel2abs(o, or);
        expected = r;
        assertEquals(expected, actual);

        actual = lattice.rel2abs(o, os);
        expected = s;
        assertEquals(expected, actual);

        actual = lattice.rel2abs(o, ot);
        expected = t;
        assertEquals(expected, actual);
    }

    @Test
    public void testGetL1Distance() {
        int expected, actual;

        // +u
        actual = lattice.getL1Distance(o, p);
        expected = 3;
        assertEquals(expected, actual);

        // +v
        actual = lattice.getL1Distance(o, q);
        expected = 3;
        assertEquals(expected, actual);

        // +w
        actual = lattice.getL1Distance(o, r);
        expected = 3;
        assertEquals(expected, actual);

        // +u +v
        actual = lattice.getL1Distance(o, s);
        expected = 6;
        assertEquals(expected, actual);

        // +u +v +w = +2v (tricky!)
        actual = lattice.getL1Distance(o, t);
        expected = 6;
        assertEquals(expected, actual);
    }

    @Test
    public void testOrthoDisplacement() {
        Coordinate expected, actual;

        // +u
        actual = lattice.getOrthoDisplacement(o, p);
        expected = new Coordinate3D(3, 0, 0, Flags.VECTOR);
        assertEquals(actual, expected);

        // +v
        actual = lattice.getOrthoDisplacement(o, q);
        expected = new Coordinate3D(3, 0, 3, Flags.VECTOR);
        assertEquals(actual, expected);

        // +w
        actual = lattice.getOrthoDisplacement(o, r);
        expected = new Coordinate3D(0, 0, 3, Flags.VECTOR);
        assertEquals(actual, expected);

        // +u +v
        actual = lattice.getOrthoDisplacement(o, s);
        expected = new Coordinate3D(6, 0, 3, Flags.VECTOR);
        assertEquals(actual, expected);

        // +u +v -w
        actual = lattice.getOrthoDisplacement(o, t);
        expected = new Coordinate3D(6, 0, 6, Flags.VECTOR);
        assertEquals(expected, actual);
    }

    @Test
    public void testInvAdjust() {
        Coordinate initial, actual, expected;

        // The origin should be unaffected
        initial = new Coordinate2D(0, 0, 0);
        actual = lattice.invAdjust(initial);
        expected = new Coordinate2D(0, 0, 0);
        assertEquals(actual, expected);

        // Y offset should be unaffected
        initial = new Coordinate2D(0, 8, 0);
        actual = lattice.invAdjust(initial);
        expected = new Coordinate2D(0, 8, 0);
        assertEquals(actual, expected);

        // X-offset should get an adjustment to Y
        initial = new Coordinate2D(8, 4, 0);
        actual = lattice.invAdjust(initial);
        expected = new Coordinate2D(8, 0, 0);
        assertEquals(actual, expected);

        // (-1, 0) <-- (-1, -1)
        initial = new Coordinate2D(-1, -1, 0);
        actual = lattice.invAdjust(initial);
        expected = new Coordinate2D(-1, 0, 0);
        assertEquals(expected, actual);

        // (-2, 0) <-- (-2, -1)
        initial = new Coordinate2D(-2, -1, 0);
        actual = lattice.invAdjust(initial);
        expected = new Coordinate2D(-2, 0, 0);
        assertEquals(expected, actual);

        // (-3, 0) <-- (-3, -2)
        initial = new Coordinate2D(-3, -2, 0);
        actual = lattice.invAdjust(initial);
        expected = new Coordinate2D(-3, 0, 0);
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
