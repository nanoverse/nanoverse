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

package geometry.lattice;

import control.identifiers.Coordinate;
import control.identifiers.Coordinate2D;
import control.identifiers.Coordinate3D;
import control.identifiers.Flags;
import test.EslimeTestCase;

public class CubicLatticeTest extends EslimeTestCase {
    private Lattice lattice;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        lattice = new CubicLattice();
    }

    public void testDimensionality() {
        assertEquals(3, lattice.getDimensionality());
    }

    public void testConnectivity() {
        assertEquals(3, lattice.getConnectivity());
    }

    public void testAdjust() {
        Coordinate initial, actual, expected;

        // The origin should be unaffected
        initial = new Coordinate3D(0, 0, 0, 0);
        actual = lattice.adjust(initial);
        expected = new Coordinate3D(0, 0, 0, 0);
        assertEquals(actual, expected);

        // X offset should be unaffected
        initial = new Coordinate3D(8, 0, 0, 0);
        actual = lattice.adjust(initial);
        expected = new Coordinate3D(8, 0, 0, 0);
        assertEquals(actual, expected);

        // Y offset should be unaffected
        initial = new Coordinate3D(0, 8, 0, 0);
        actual = lattice.adjust(initial);
        expected = new Coordinate3D(0, 8, 0, 0);
        assertEquals(actual, expected);

        // Z offset should be unaffected
        initial = new Coordinate3D(0, 0, 8, 0);
        actual = lattice.adjust(initial);
        expected = new Coordinate3D(0, 0, 8, 0);
        assertEquals(actual, expected);
    }

    public void testBasis() {
        Coordinate[] basis = lattice.getBasis();

        assertEquals(basis.length, 3);

        Coordinate i = new Coordinate3D(1, 0, 0, 0);
        Coordinate j = new Coordinate3D(0, 1, 0, 0);
        Coordinate k = new Coordinate3D(0, 0, 1, 0);

        assertEquals(basis[0], i);
        assertEquals(basis[1], j);
        assertEquals(basis[2], k);
    }


    public void testGetAnnulus() {
        Coordinate[] actual, expected;
        Coordinate origin = new Coordinate3D(0, 0, 0, 0);

        // r=0
        actual = lattice.getAnnulus(origin, 0);
        expected = new Coordinate[]{origin};
        assertArraysEqual(actual, expected, true);

        // r=1
        actual = lattice.getAnnulus(origin, 1);
        expected = new Coordinate[]{
                new Coordinate3D(1, 0, 0, 0),
                new Coordinate3D(-1, 0, 0, 0),
                new Coordinate3D(0, 1, 0, 0),
                new Coordinate3D(0, -1, 0, 0),
                new Coordinate3D(0, 0, 1, 0),
                new Coordinate3D(0, 0, -1, 0)
        };

        assertArraysEqual(actual, expected, true);

        // r=2
        actual = lattice.getAnnulus(origin, 2);
        expected = new Coordinate[]{new Coordinate3D(1, 1, 0, 0),
                new Coordinate3D(1, -1, 0, 0),
                new Coordinate3D(1, 0, 1, 0),
                new Coordinate3D(1, 0, -1, 0),
                new Coordinate3D(-1, 1, 0, 0),
                new Coordinate3D(-1, -1, 0, 0),
                new Coordinate3D(-1, 0, 1, 0),
                new Coordinate3D(-1, 0, -1, 0),
                new Coordinate3D(0, 1, 1, 0),
                new Coordinate3D(0, 1, -1, 0),
                new Coordinate3D(0, -1, 1, 0),
                new Coordinate3D(0, -1, -1, 0),
                new Coordinate3D(2, 0, 0, 0),
                new Coordinate3D(-2, 0, 0, 0),
                new Coordinate3D(0, 2, 0, 0),
                new Coordinate3D(0, -2, 0, 0),
                new Coordinate3D(0, 0, 2, 0),
                new Coordinate3D(0, 0, -2, 0)
        };

        assertArraysEqual(actual, expected, true);
    }

    public void testGetNeighbors() {
        Coordinate[] actual, expected;
        Coordinate origin = new Coordinate2D(0, 0, 0);

        // Should be same as r=1 annulus
        actual = lattice.getNeighbors(origin);
        expected = new Coordinate[]{new Coordinate3D(1, 0, 0, 0),
                new Coordinate3D(-1, 0, 0, 0),
                new Coordinate3D(0, 1, 0, 0),
                new Coordinate3D(0, -1, 0, 0),
                new Coordinate3D(0, 0, 1, 0),
                new Coordinate3D(0, 0, -1, 0)
        };

        assertArraysEqual(expected, actual, true);
    }

    public void testGetDisplacement() {
        Coordinate o, p, q, r, s;
        Coordinate expected, actual;

        o = new Coordinate3D(0, 0, 0, 0);
        p = new Coordinate3D(3, 0, 0, 0);
        q = new Coordinate3D(0, 3, 0, 0);
        r = new Coordinate3D(0, 0, 3, 0);
        s = new Coordinate3D(3, 3, 3, 0);

        // +i
        actual = lattice.getDisplacement(o, p);
        expected = new Coordinate3D(3, 0, 0, Flags.VECTOR);
        assertEquals(expected, actual);

        // +j
        actual = lattice.getDisplacement(o, q);
        expected = new Coordinate3D(0, 3, 0, Flags.VECTOR);
        assertEquals(expected, actual);

        // +k
        actual = lattice.getDisplacement(o, r);
        expected = new Coordinate3D(0, 0, 3, Flags.VECTOR);
        assertEquals(expected, actual);

        // +ijk
        actual = lattice.getDisplacement(o, s);
        expected = new Coordinate3D(3, 3, 3, Flags.VECTOR);
        assertEquals(expected, actual);
    }

    public void testGetL1Distance() {
        Coordinate o, p, q, r, s;
        int expected, actual;

        o = new Coordinate3D(0, 0, 0, 0);
        p = new Coordinate3D(3, 0, 0, 0);
        q = new Coordinate3D(0, 3, 0, 0);
        r = new Coordinate3D(0, 0, 3, 0);
        s = new Coordinate3D(3, 3, 3, 0);

        // +i
        actual = lattice.getL1Distance(o, p);
        expected = 3;
        assertEquals(expected, actual);

        // +j
        actual = lattice.getL1Distance(o, q);
        expected = 3;
        assertEquals(expected, actual);

        // +k
        actual = lattice.getL1Distance(o, r);
        expected = 3;
        assertEquals(expected, actual);

        // +ijk
        actual = lattice.getL1Distance(o, s);
        expected = 9;
        assertEquals(expected, actual);
    }

    public void testRel2Abs() {
        Coordinate o, p, q, r;
        Coordinate actual, expected;

        o = new Coordinate3D(0, 0, 0, 0);
        p = new Coordinate3D(3, 0, 0, 0);
        q = new Coordinate3D(0, 3, 0, 0);
        r = new Coordinate3D(0, 0, 3, 0);

        Coordinate op, oq, or;

        op = new Coordinate3D(3, 0, 0, Flags.VECTOR);
        oq = new Coordinate3D(0, 3, 0, Flags.VECTOR);
        or = new Coordinate3D(0, 0, 3, Flags.VECTOR);

        // +i
        actual = lattice.rel2abs(o, op);
        expected = p;
        assertEquals(expected, actual);

        // +j
        actual = lattice.rel2abs(o, oq);
        expected = q;
        assertEquals(expected, actual);

        // +k
        actual = lattice.rel2abs(o, or);
        expected = r;
        assertEquals(expected, actual);
    }

    public void testOrthoDisplacement() {
        Coordinate o, p, q, r, s;
        Coordinate expected, actual;

        o = new Coordinate3D(0, 0, 0, 0);
        p = new Coordinate3D(3, 0, 0, 0);
        q = new Coordinate3D(0, 3, 0, 0);
        r = new Coordinate3D(0, 0, 3, 0);
        s = new Coordinate3D(3, 3, 3, 0);

        // +i
        actual = lattice.getOrthoDisplacement(o, p);
        expected = new Coordinate3D(3, 0, 0, Flags.VECTOR);
        assertEquals(expected, actual);

        // +j
        actual = lattice.getOrthoDisplacement(o, q);
        expected = new Coordinate3D(0, 3, 0, Flags.VECTOR);
        assertEquals(expected, actual);

        // +k
        actual = lattice.getOrthoDisplacement(o, r);
        expected = new Coordinate3D(0, 0, 3, Flags.VECTOR);
        assertEquals(expected, actual);

        // +ijk
        actual = lattice.getOrthoDisplacement(o, s);
        expected = new Coordinate3D(3, 3, 3, Flags.VECTOR);
        assertEquals(expected, actual);
    }

    public void testInvAdjust() {
        Coordinate initial, actual, expected;

        // The origin should be unaffected
        initial = new Coordinate3D(0, 0, 0, 0);
        actual = lattice.invAdjust(initial);
        expected = new Coordinate3D(0, 0, 0, 0);
        assertEquals(actual, expected);

        // X offset should be unaffected
        initial = new Coordinate3D(8, 0, 0, 0);
        actual = lattice.invAdjust(initial);
        expected = new Coordinate3D(8, 0, 0, 0);
        assertEquals(actual, expected);

        // Y offset should be unaffected
        initial = new Coordinate3D(0, 8, 0, 0);
        actual = lattice.invAdjust(initial);
        expected = new Coordinate3D(0, 8, 0, 0);
        assertEquals(actual, expected);

        // Z offset should be unaffected
        initial = new Coordinate3D(0, 0, 8, 0);
        actual = lattice.invAdjust(initial);
        expected = new Coordinate3D(0, 0, 8, 0);
        assertEquals(actual, expected);
    }

    public void testClone() {
        Object cloned = lattice.clone();
        assertEquals(lattice.getClass(), cloned.getClass());
        assertFalse(lattice == cloned);
    }

    public void testGetZeroVector() {
        Coordinate expected = new Coordinate3D(0, 0, 0, 0);
        Coordinate actual = lattice.getZeroVector();
        assertEquals(expected, actual);
    }
}
