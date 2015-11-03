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
import nanoverse.runtime.geometry.shape.*;
import org.junit.*;
import test.LegacyTest;

import static org.junit.Assert.assertEquals;

/**
 * Tests that the rectangle nanoverse.runtime.geometry shape works as expected
 * with the triangular lattice.
 *
 * @author dbborens
 */
public class TriLatticeRectangleTest extends LegacyTest {

    private Shape odd;
    private Shape even;

    @Before
    public void setUp() {
        Lattice oddLattice = new TriangularLattice();
        Lattice evenLattice = new TriangularLattice();

        even = new Rectangle(evenLattice, 4, 2);
        odd = new Rectangle(oddLattice, 5, 3);
    }

    @Test
    public void testGetCenter() {
        Coordinate actual, expected;

        // Even -- we round down
        expected = new Coordinate2D(1, 0, 0);
        actual = even.getCenter();
        assertEquals(expected, actual);

        // Odd
        expected = new Coordinate2D(2, 2, 0);
        actual = odd.getCenter();
        assertEquals(expected, actual);
    }

    @Test
    public void testGetBoundaries() {
        Coordinate[] actual, expected;

        // Even
        expected = new Coordinate2D[]{
            new Coordinate2D(0, 0, 0),
            new Coordinate2D(1, 0, 0),
            new Coordinate2D(2, 1, 0),
            new Coordinate2D(3, 1, 0),
            new Coordinate2D(0, 1, 0),
            new Coordinate2D(1, 1, 0),
            new Coordinate2D(2, 2, 0),
            new Coordinate2D(3, 2, 0),
        };
        actual = even.getBoundaries();
        assertArraysEqual(actual, expected, true);

        // Odd
        expected = new Coordinate2D[]{
            new Coordinate2D(0, 0, 0),
            new Coordinate2D(1, 0, 0),
            new Coordinate2D(2, 1, 0),
            new Coordinate2D(3, 1, 0),
            new Coordinate2D(4, 2, 0),
            new Coordinate2D(4, 3, 0),
            new Coordinate2D(4, 4, 0),
            new Coordinate2D(3, 3, 0),
            new Coordinate2D(2, 3, 0),
            new Coordinate2D(1, 2, 0),
            new Coordinate2D(0, 2, 0),
            new Coordinate2D(0, 1, 0)
        };

        actual = odd.getBoundaries();
        assertArraysEqual(expected, actual, true);
    }

    @Test
    public void testCanonicalSites() {
        Coordinate[] actual, expected;

        expected = new Coordinate2D[]{
            new Coordinate2D(0, 0, 0),
            new Coordinate2D(1, 0, 0),
            new Coordinate2D(2, 1, 0),
            new Coordinate2D(3, 1, 0),
            new Coordinate2D(0, 1, 0),
            new Coordinate2D(1, 1, 0),
            new Coordinate2D(2, 2, 0),
            new Coordinate2D(3, 2, 0),
        };
        actual = even.getCanonicalSites();
        assertArraysEqual(actual, expected, true);
    }

    @Test
    public void testOverbounds() {

        // In bounds coordinates
        Coordinate a, b, c;
        a = new Coordinate2D(0, 0, 0);
        b = new Coordinate2D(1, 0, 0);
        c = new Coordinate2D(1, 1, 0);

        // Out of bounds coordinates
        Coordinate p, q, r, s;
        p = new Coordinate2D(0, 3, 0);
        q = new Coordinate2D(5, 5, 0);
        r = new Coordinate2D(-1, 0, 0);
        s = new Coordinate2D(-1, -1, 0);

        Coordinate expected, actual;

        // Even
        expected = new Coordinate2D(0, 0, Flags.VECTOR);
        actual = even.getOverbounds(a);
        assertEquals(expected, actual);

        expected = new Coordinate2D(0, 0, Flags.VECTOR);
        actual = even.getOverbounds(b);
        assertEquals(expected, actual);

        expected = new Coordinate2D(0, 0, Flags.VECTOR);
        actual = even.getOverbounds(c);
        assertEquals(expected, actual);

        expected = new Coordinate2D(0, 2, Flags.VECTOR);
        actual = even.getOverbounds(p);
        assertEquals(expected, actual);

        expected = new Coordinate2D(2, 2, Flags.VECTOR);
        actual = even.getOverbounds(q);
        assertEquals(expected, actual);

        expected = new Coordinate2D(-1, 0, Flags.VECTOR);
        actual = even.getOverbounds(r);
        assertEquals(expected, actual);

        expected = new Coordinate2D(-1, 0, Flags.VECTOR);
        actual = even.getOverbounds(s);
        assertEquals(expected, actual);

        // Odd
        expected = new Coordinate2D(0, 0, Flags.VECTOR);
        actual = odd.getOverbounds(a);
        assertEquals(expected, actual);

        expected = new Coordinate2D(0, 0, Flags.VECTOR);
        actual = odd.getOverbounds(b);
        assertEquals(expected, actual);

        expected = new Coordinate2D(0, 0, Flags.VECTOR);
        actual = odd.getOverbounds(c);
        assertEquals(expected, actual);

        expected = new Coordinate2D(0, 1, Flags.VECTOR);
        actual = odd.getOverbounds(p);
        assertEquals(expected, actual);

        expected = new Coordinate2D(1, 1, Flags.VECTOR);
        actual = odd.getOverbounds(q);
        assertEquals(expected, actual);

        expected = new Coordinate2D(-1, 0, Flags.VECTOR);
        actual = odd.getOverbounds(r);
        assertEquals(expected, actual);

        expected = new Coordinate2D(-1, 0, Flags.VECTOR);
        actual = odd.getOverbounds(s);
        assertEquals(expected, actual);
    }

    @Test
    public void testDimensions() {
        int[] actual, expected;

        // Odd
        expected = new int[]{5, 3};
        actual = odd.getDimensions();
        assertArraysEqual(actual, expected, false);

        // Even
        expected = new int[]{4, 2};
        actual = even.getDimensions();
        assertArraysEqual(actual, expected, false);
    }

	/*public void testGetLimits() {
        Coordinate[] actual, expected;

		// Even
		expected = new Coordinate2D[] {
			new Coordinate2D(-1, 0, 0, Flags.VECTOR),
			new Coordinate2D(0, 2, 0, Flags.VECTOR)
		};
		
		actual = even.getLimits();
		assertArraysEqual(actual, expected, false);
		
		// Odd
		expected = new Coordinate2D[] {
				new Coordinate2D(-1, -2, Flags.VECTOR),
				new Coordinate2D(1, 2, Flags.VECTOR)
			};
		
		actual = odd.getLimits();
		assertArraysEqual(actual, expected, false);
	}*/
}
