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
import control.identifiers.Flags;
import test.EslimeTestCase;

public class LinearLatticeTest extends EslimeTestCase {
    private Lattice lattice;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        lattice = new LinearLattice();
    }

    public void testDimensionality() {
        assertEquals(1, lattice.getDimensionality());
    }

    public void testConnectivity() {
        assertEquals(1, lattice.getConnectivity());
    }

    public void testAdjust() {
        Coordinate initial, actual, expected;

        // The origin should be unaffected
        initial = new Coordinate(0, 0, 0);
        actual = lattice.adjust(initial);
        expected = new Coordinate(0, 0, 0);
        assertEquals(actual, expected);

        // Y offset should be unaffected
        initial = new Coordinate(0, 8, 0);
        actual = lattice.adjust(initial);
        expected = new Coordinate(0, 8, 0);
        assertEquals(actual, expected);

    }

    public void testBasis() {
        Coordinate[] basis = lattice.getBasis();

        assertEquals(basis.length, 1);

        Coordinate north = new Coordinate(0, 1, 0);

        assertEquals(basis[0], north);
    }

    public void testGetAnnulus() {
        Coordinate[] actual, expected;
        Coordinate origin = new Coordinate(0, 0, 0);

        // r=0
        actual = lattice.getAnnulus(origin, 0);
        expected = new Coordinate[]{origin};
        assertArraysEqual(actual, expected, true);

        // r=1
        actual = lattice.getAnnulus(origin, 1);
        expected = new Coordinate[]{
                new Coordinate(0, 1, 0),
                new Coordinate(0, -1, 0)
        };

        assertArraysEqual(actual, expected, true);

        // r=2
        actual = lattice.getAnnulus(origin, 2);
        expected = new Coordinate[]{
                new Coordinate(0, 2, 0),
                new Coordinate(0, -2, 0),
        };

        assertArraysEqual(actual, expected, true);
    }


    public void testGetNeighbors() {
        Coordinate[] actual, expected;
        Coordinate origin = new Coordinate(0, 0, 0);

        // Should be same as r=1 annulus
        actual = lattice.getNeighbors(origin);
        expected = new Coordinate[]{
                new Coordinate(0, 1, 0),
                new Coordinate(0, -1, 0)
        };

        assertArraysEqual(expected, actual, true);
    }

    public void testGetDisplacement() {
        Coordinate o, q;
        Coordinate expected, actual;

        o = new Coordinate(0, 0, 0);
        q = new Coordinate(0, 3, 0);

        // Vertical
        actual = lattice.getDisplacement(o, q);
        expected = new Coordinate(0, 3, Flags.VECTOR);
        assertEquals(expected, actual);

    }

    public void testRel2Abs() {
        Coordinate o, q;
        Coordinate actual, expected;

        o = new Coordinate(0, 0, 0);
        q = new Coordinate(0, 3, 0);

        Coordinate oq;
        oq = new Coordinate(0, 3, Flags.VECTOR);

        // Vertical
        actual = lattice.rel2abs(o, oq);
        expected = q;
        assertEquals(expected, actual);
    }

    public void testGetL1Distance() {
        Coordinate o, q;
        int expected, actual;

        o = new Coordinate(0, 0, 0);
        q = new Coordinate(0, -3, 0);


        // Vertical
        actual = lattice.getL1Distance(o, q);
        expected = 3;
        assertEquals(expected, actual);
    }

    public void testOrthoDisplacement() {
        Coordinate o, q;
        Coordinate expected, actual;

        o = new Coordinate(0, 0, 0);
        q = new Coordinate(0, 3, 0);

        // Vertical
        actual = lattice.getOrthoDisplacement(o, q);
        expected = new Coordinate(0, 3, Flags.VECTOR);
        assertEquals(expected, actual);
    }

    public void testInvAdjust() {
        Coordinate initial, actual, expected;

        // The origin should be unaffected
        initial = new Coordinate(0, 0, 0);
        actual = lattice.invAdjust(initial);
        expected = new Coordinate(0, 0, 0);
        assertEquals(actual, expected);

        // Y offset should be unaffected
        initial = new Coordinate(0, 8, 0);
        actual = lattice.invAdjust(initial);
        expected = new Coordinate(0, 8, 0);
        assertEquals(actual, expected);
    }

    public void testClone() {
        Object cloned = lattice.clone();
        assertEquals(lattice.getClass(), cloned.getClass());
        assertFalse(lattice == cloned);
    }

    public void testGetZeroVector() {
        Coordinate expected = new Coordinate(0, 0, 0);
        Coordinate actual = lattice.getZeroVector();
        assertEquals(expected, actual);
    }
}