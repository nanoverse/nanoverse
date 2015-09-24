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

package geometry.shape;

import control.identifiers.*;
import geometry.lattice.*;
import org.junit.*;
import test.EslimeTestCase;

import static org.junit.Assert.assertEquals;
public class RectangleTest extends EslimeTestCase {

    private Shape odd;
    private Shape even;

    private Lattice evenLattice;

    @Before
    public void setUp() {
        Lattice oddLattice = new RectangularLattice();
        evenLattice = new RectangularLattice();

        even = new Rectangle(evenLattice, 2, 4);
        odd = new Rectangle(oddLattice, 3, 5);
    }

    @Test
    public void testGetCenter() {
        Coordinate actual, expected;

        // Even -- we round down
        expected = new Coordinate2D(0, 1, 0);
        actual = even.getCenter();
        assertEquals(expected, actual);

        // Odd
        expected = new Coordinate2D(1, 2, 0);
        actual = odd.getCenter();
        assertEquals(expected, actual);
    }

    @Test
    public void testGetBoundaries() {
        Coordinate[] actual, expected;

        // Even
        expected = new Coordinate[]{
                new Coordinate2D(0, 0, 0),
                new Coordinate2D(0, 1, 0),
                new Coordinate2D(0, 2, 0),
                new Coordinate2D(0, 3, 0),
                new Coordinate2D(1, 3, 0),
                new Coordinate2D(1, 2, 0),
                new Coordinate2D(1, 1, 0),
                new Coordinate2D(1, 0, 0)
        };
        actual = even.getBoundaries();
        assertArraysEqual(actual, expected, true);

        // Odd
        expected = new Coordinate[]{
                new Coordinate2D(0, 0, 0),
                new Coordinate2D(0, 1, 0),
                new Coordinate2D(0, 2, 0),
                new Coordinate2D(0, 3, 0),
                new Coordinate2D(0, 4, 0),
                new Coordinate2D(1, 4, 0),
                new Coordinate2D(2, 4, 0),
                new Coordinate2D(2, 3, 0),
                new Coordinate2D(2, 2, 0),
                new Coordinate2D(2, 1, 0),
                new Coordinate2D(2, 0, 0),
                new Coordinate2D(1, 0, 0)
        };
        actual = odd.getBoundaries();
        assertArraysEqual(actual, expected, true);
    }

    @Test
    public void testCanonicalSites() {
        Coordinate[] actual, expected;

        expected = new Coordinate[]{
                new Coordinate2D(0, 0, 0),
                new Coordinate2D(0, 1, 0),
                new Coordinate2D(0, 2, 0),
                new Coordinate2D(0, 3, 0),
                new Coordinate2D(1, 3, 0),
                new Coordinate2D(1, 2, 0),
                new Coordinate2D(1, 1, 0),
                new Coordinate2D(1, 0, 0)
        };
        actual = even.getCanonicalSites();
        assertArraysEqual(actual, expected, true);
    }

    @Test
    public void testOverbounds() {
        Coordinate actual, expected;

        // Test coordinates -- in bounds
        Coordinate a, b, c;

        a = new Coordinate2D(0, 0, 0);
        b = new Coordinate2D(0, 2, 0);
        c = new Coordinate2D(1, 1, 0);

        // Test coordinates -- out of bounds
        Coordinate p, q, r, s;
        p = new Coordinate2D(3, 0, 0);
        q = new Coordinate2D(0, 6, 0);
        r = new Coordinate2D(-3, 0, 0);
        s = new Coordinate2D(-1, 5, 0);

        // Even
        expected = new Coordinate2D(0, 0, Flags.VECTOR);
        actual = even.getOverbounds(a);
        assertEquals(actual, expected);

        expected = new Coordinate2D(0, 0, Flags.VECTOR);
        actual = even.getOverbounds(b);
        assertEquals(actual, expected);

        expected = new Coordinate2D(0, 0, Flags.VECTOR);
        actual = even.getOverbounds(c);
        assertEquals(actual, expected);

        expected = new Coordinate2D(2, 0, Flags.VECTOR);
        actual = even.getOverbounds(p);
        assertEquals(actual, expected);

        expected = new Coordinate2D(0, 3, Flags.VECTOR);
        actual = even.getOverbounds(q);
        assertEquals(actual, expected);

        expected = new Coordinate2D(-3, 0, Flags.VECTOR);
        actual = even.getOverbounds(r);
        assertEquals(actual, expected);

        expected = new Coordinate2D(-1, 2, Flags.VECTOR);
        actual = even.getOverbounds(s);
        assertEquals(actual, expected);

        // Odd
        expected = new Coordinate2D(0, 0, Flags.VECTOR);
        actual = odd.getOverbounds(a);
        assertEquals(actual, expected);

        expected = new Coordinate2D(0, 0, Flags.VECTOR);
        actual = odd.getOverbounds(b);
        assertEquals(actual, expected);

        expected = new Coordinate2D(0, 0, Flags.VECTOR);
        actual = odd.getOverbounds(c);
        assertEquals(actual, expected);

        expected = new Coordinate2D(1, 0, Flags.VECTOR);
        actual = odd.getOverbounds(p);
        assertEquals(actual, expected);

        expected = new Coordinate2D(0, 2, Flags.VECTOR);
        actual = odd.getOverbounds(q);
        assertEquals(actual, expected);

        expected = new Coordinate2D(-3, 0, Flags.VECTOR);
        actual = odd.getOverbounds(r);
        assertEquals(actual, expected);

        expected = new Coordinate2D(-1, 1, Flags.VECTOR);
        actual = odd.getOverbounds(s);
        assertEquals(actual, expected);
    }

    @Test
    public void testDimensions() {
        int[] actual, expected;

        // Odd
        expected = new int[]{3, 5};
        actual = odd.getDimensions();
        assertArraysEqual(actual, expected, false);

        // Even
        expected = new int[]{2, 4};
        actual = even.getDimensions();
        assertArraysEqual(actual, expected, false);
    }

    @Test
    public void testCloneAtScale() {
        Lattice clonedLattice = evenLattice.clone();
        Shape cloned = even.cloneAtScale(clonedLattice, 2.0);

        assertEquals(even.getClass(), cloned.getClass());
        assertEquals(8, even.getCanonicalSites().length);
        assertEquals(32, cloned.getCanonicalSites().length);
    }
}
