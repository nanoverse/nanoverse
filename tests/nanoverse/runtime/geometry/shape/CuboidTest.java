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

package nanoverse.runtime.geometry.shape;

import nanoverse.runtime.control.identifiers.*;
import nanoverse.runtime.geometry.lattice.*;
import org.junit.*;
import test.LegacyTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class CuboidTest extends LegacyTest {
    Lattice evenLattice;
    private Shape odd;
    private Shape even;

    @Before
    public void setUp() {
        Lattice oddLattice = new CubicLattice();
        evenLattice = new CubicLattice();

        even = new Cuboid(evenLattice, 2, 2, 2);
        odd = new Cuboid(oddLattice, 3, 3, 3);
    }

    @Test
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

    @Test
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

    @Test
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

    @Test
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

    @Test
    public void getDistanceOverBoundary() {
        int actual, expected;

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
        expected = 0;
        actual = even.getDistanceOverBoundary(a);
        assertEquals(actual, expected);

        expected = 0;
        actual = even.getDistanceOverBoundary(b);
        assertEquals(actual, expected);

        expected = 0;
        actual = even.getDistanceOverBoundary(c);
        assertEquals(actual, expected);

        expected = 2;
        actual = even.getDistanceOverBoundary(p);
        assertEquals(actual, expected);

        expected = 9;
        actual = even.getDistanceOverBoundary(q);
        assertEquals(actual, expected);

        expected = 3;
        actual = even.getDistanceOverBoundary(r);
        assertEquals(actual, expected);

        // Odd
        expected = 0;
        actual = odd.getDistanceOverBoundary(a);
        assertEquals(actual, expected);

        expected = 0;
        actual = odd.getDistanceOverBoundary(b);
        assertEquals(actual, expected);

        expected = 0;
        actual = odd.getDistanceOverBoundary(c);
        assertEquals(actual, expected);

        expected = 1;
        actual = odd.getDistanceOverBoundary(p);
        assertEquals(actual, expected);

        expected = 7;
        actual = odd.getDistanceOverBoundary(q);
        assertEquals(actual, expected);

        expected = 0;
        actual = odd.getDistanceOverBoundary(r);
        assertEquals(actual, expected);
    }

    @Test
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

    @Test
    public void testCloneAtScale() {
        Lattice clonedLattice = evenLattice.clone();
        Shape cloned = even.cloneAtScale(clonedLattice, 2.0);

        assertEquals(even.getClass(), cloned.getClass());
        assertEquals(8, even.getCanonicalSites().length);
        assertEquals(64, cloned.getCanonicalSites().length);
    }
}
