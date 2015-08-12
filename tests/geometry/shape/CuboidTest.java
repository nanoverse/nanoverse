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

import control.identifiers.Coordinate;
import control.identifiers.Coordinate3D;
import control.identifiers.Coordinate3D;
import control.identifiers.Flags;
import geometry.lattice.CubicLattice;
import geometry.lattice.Lattice;
import test.EslimeTestCase;

public class CuboidTest extends EslimeTestCase {
    Lattice evenLattice;
    private Shape odd;
    private Shape even;

    @Override
    public void setUp() {
        Lattice oddLattice = new CubicLattice();
        evenLattice = new CubicLattice();

        even = new Cuboid(evenLattice, 2, 2, 2);
        odd = new Cuboid(oddLattice, 3, 3, 3);
    }

    public void testGetCenter() {
        Coordinate actual, expected;

        // Even -- we round down
        expected = new Coordinate3D(0, 0, 0, 0);
        actual = even.getCenter();
        assertEquals(expected, actual);

        // Odd
        expected = new Coordinate3D(1, 1, 1, 0);
        actual = odd.getCenter();
        assertEquals(expected, actual);
    }

    public void testGetBoundaries() {
        Coordinate[] actual, expected;

        // Even -- all points are on boundary
        expected = new Coordinate[]{
                new Coordinate3D(0, 0, 0, 0),
                new Coordinate3D(0, 0, 1, 0),
                new Coordinate3D(0, 1, 0, 0),
                new Coordinate3D(0, 1, 1, 0),
                new Coordinate3D(1, 0, 0, 0),
                new Coordinate3D(1, 0, 1, 0),
                new Coordinate3D(1, 1, 0, 0),
                new Coordinate3D(1, 1, 1, 0)
        };

        actual = even.getBoundaries();
        assertArraysEqual(actual, expected, true);

        // Odd -- every point but (1, 1, 1)
        expected = new Coordinate[]{
                new Coordinate3D(0, 0, 0, 0),
                new Coordinate3D(0, 0, 1, 0),
                new Coordinate3D(0, 0, 2, 0),
                new Coordinate3D(0, 1, 0, 0),
                new Coordinate3D(0, 1, 1, 0),
                new Coordinate3D(0, 1, 2, 0),
                new Coordinate3D(0, 2, 0, 0),
                new Coordinate3D(0, 2, 1, 0),
                new Coordinate3D(0, 2, 2, 0),
                new Coordinate3D(1, 0, 0, 0),
                new Coordinate3D(1, 0, 1, 0),
                new Coordinate3D(1, 0, 2, 0),
                new Coordinate3D(1, 1, 0, 0),
                // Not (1, 1, 1)
                new Coordinate3D(1, 1, 2, 0),
                new Coordinate3D(1, 2, 0, 0),
                new Coordinate3D(1, 2, 1, 0),
                new Coordinate3D(1, 2, 2, 0),
                new Coordinate3D(2, 0, 0, 0),
                new Coordinate3D(2, 0, 1, 0),
                new Coordinate3D(2, 0, 2, 0),
                new Coordinate3D(2, 1, 0, 0),
                new Coordinate3D(2, 1, 1, 0),
                new Coordinate3D(2, 1, 2, 0),
                new Coordinate3D(2, 2, 0, 0),
                new Coordinate3D(2, 2, 1, 0),
                new Coordinate3D(2, 2, 2, 0)
        };
        actual = odd.getBoundaries();
        assertArraysEqual(actual, expected, true);
    }

    public void testCanonicalSites() {
        Coordinate[] actual, expected;

        expected = new Coordinate[]{
                new Coordinate3D(0, 0, 0, 0),
                new Coordinate3D(0, 0, 1, 0),
                new Coordinate3D(0, 1, 0, 0),
                new Coordinate3D(0, 1, 1, 0),
                new Coordinate3D(1, 0, 0, 0),
                new Coordinate3D(1, 0, 1, 0),
                new Coordinate3D(1, 1, 0, 0),
                new Coordinate3D(1, 1, 1, 0)
        };
        actual = even.getCanonicalSites();
        assertArraysEqual(actual, expected, true);
    }

    public void testOverbounds() {
        Coordinate actual, expected;

        // Test coordinates -- in bounds
        Coordinate a, b, c;

        a = new Coordinate3D(0, 0, 0, 0);
        b = new Coordinate3D(0, 1, 0, 0);
        c = new Coordinate3D(1, 1, 1, 0);

        // Test coordinates -- out of bounds
        Coordinate p, q, r;
        p = new Coordinate3D(3, 0, 0, 0);
        q = new Coordinate3D(-3, 3, 5, 0);
        r = new Coordinate3D(2, 2, 2, 0);

        // Even
        expected = new Coordinate3D(0, 0, 0, Flags.VECTOR);
        actual = even.getOverbounds(a);
        assertEquals(actual, expected);

        expected = new Coordinate3D(0, 0, 0, Flags.VECTOR);
        actual = even.getOverbounds(b);
        assertEquals(actual, expected);

        expected = new Coordinate3D(0, 0, 0, Flags.VECTOR);
        actual = even.getOverbounds(c);
        assertEquals(actual, expected);

        expected = new Coordinate3D(2, 0, 0, Flags.VECTOR);
        actual = even.getOverbounds(p);
        assertEquals(actual, expected);

        expected = new Coordinate3D(-3, 2, 4, Flags.VECTOR);
        actual = even.getOverbounds(q);
        assertEquals(actual, expected);

        expected = new Coordinate3D(1, 1, 1, Flags.VECTOR);
        actual = even.getOverbounds(r);
        assertEquals(actual, expected);

        // Odd
        expected = new Coordinate3D(0, 0, 0, Flags.VECTOR);
        actual = odd.getOverbounds(a);
        assertEquals(actual, expected);

        expected = new Coordinate3D(0, 0, 0, Flags.VECTOR);
        actual = odd.getOverbounds(b);
        assertEquals(actual, expected);

        expected = new Coordinate3D(0, 0, 0, Flags.VECTOR);
        actual = odd.getOverbounds(c);
        assertEquals(actual, expected);

        expected = new Coordinate3D(1, 0, 0, Flags.VECTOR);
        actual = odd.getOverbounds(p);
        assertEquals(actual, expected);

        expected = new Coordinate3D(-3, 1, 3, Flags.VECTOR);
        actual = odd.getOverbounds(q);
        assertEquals(actual, expected);

        expected = new Coordinate3D(0, 0, 0, Flags.VECTOR);
        actual = odd.getOverbounds(r);
        assertEquals(actual, expected);
    }

    public void testDimensions() {
        int[] actual, expected;

        // Odd
        expected = new int[]{3, 3, 3};
        actual = odd.getDimensions();
        assertArraysEqual(actual, expected, false);

        // Even
        expected = new int[]{2, 2, 2};
        actual = even.getDimensions();
        assertArraysEqual(actual, expected, false);
    }

    public void testCloneAtScale() {
        Lattice clonedLattice = evenLattice.clone();
        Shape cloned = even.cloneAtScale(clonedLattice, 2.0);

        assertEquals(even.getClass(), cloned.getClass());
        assertEquals(8, even.getCanonicalSites().length);
        assertEquals(64, cloned.getCanonicalSites().length);
    }

	/*public void testGetLimits() {
        Coordinate[] actual, expected;

		// Even
		expected = new Coordinate[] {
			new Coordinate3D(0, 0, 0, Flags.VECTOR),
			new Coordinate3D(1, 1, 1, Flags.VECTOR)
		};
		
		actual = even.getLimits();
		assertArraysEqual(actual, expected, false);
		
		// Odd
		expected = new Coordinate[] {
				new Coordinate3D(-1, -1, -1, Flags.VECTOR),
				new Coordinate3D(1, 1, 1, Flags.VECTOR)
		};
		
		actual = odd.getLimits();
		assertArraysEqual(actual, expected, false);
	}*/
}
