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
import nanoverse.runtime.geometry.Geometry;
import nanoverse.runtime.geometry.boundaries.*;
import nanoverse.runtime.geometry.lattice.*;
import nanoverse.runtime.structural.NotYetImplementedException;
import org.junit.*;
import test.LegacyTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;

public class HexagonTest extends LegacyTest {

    private Hexagon hex;
    private Lattice lattice;

    @Before
    public void setUp() {
        lattice = new TriangularLattice();

        hex = new Hexagon(lattice, 2);
    }

    @Test
    public void testGetCenter() {
        Coordinate actual, expected;
        Lattice lattice = new TriangularLattice();

        for (int r = 0; r < 10; r++) {
            hex = new Hexagon(lattice, r);
            expected = new Coordinate2D(r, r, 0);
            actual = hex.getCenter();
            assertEquals(expected, actual);
        }
    }

    @Test
    public void testGetBoundaries() {
        Coordinate[] actual, expected;

        expected = new Coordinate[]{
            new Coordinate2D(0, 0, 0),
            new Coordinate2D(0, 1, 0),
            new Coordinate2D(0, 2, 0),
            new Coordinate2D(1, 3, 0),
            new Coordinate2D(2, 4, 0),
            new Coordinate2D(3, 4, 0),
            new Coordinate2D(4, 4, 0),
            new Coordinate2D(4, 3, 0),
            new Coordinate2D(4, 2, 0),
            new Coordinate2D(3, 1, 0),
            new Coordinate2D(2, 0, 0),
            new Coordinate2D(1, 0, 0)
        };

        actual = hex.getBoundaries();
        assertArraysEqual(actual, expected, true);
    }

    @Test
    public void testCanonicalSites() {

        Coordinate[] actual, expected;

        expected = new Coordinate[]{
            new Coordinate2D(2, 2, 0),
            new Coordinate2D(1, 1, 0),
            new Coordinate2D(1, 2, 0),
            new Coordinate2D(2, 3, 0),
            new Coordinate2D(3, 3, 0),
            new Coordinate2D(3, 2, 0),
            new Coordinate2D(2, 1, 0),
            new Coordinate2D(0, 0, 0),
            new Coordinate2D(0, 1, 0),
            new Coordinate2D(0, 2, 0),
            new Coordinate2D(1, 3, 0),
            new Coordinate2D(2, 4, 0),
            new Coordinate2D(3, 4, 0),
            new Coordinate2D(4, 4, 0),
            new Coordinate2D(4, 3, 0),
            new Coordinate2D(4, 2, 0),
            new Coordinate2D(3, 1, 0),
            new Coordinate2D(2, 0, 0),
            new Coordinate2D(1, 0, 0)
        };
        actual = hex.getCanonicalSites();
        assertArraysEqual(actual, expected, true);
    }


    @Test
    public void testOverboundsInside() {


        // In-bounds coordinates -- origin is (2, 2)
        Coordinate a, b, c, d;
        a = new Coordinate2D(2, 2, 0);
        b = new Coordinate2D(3, 3, 0);
        c = new Coordinate2D(2, 0, 0);
        d = new Coordinate2D(4, 2, 0);

        int actual, expected;

        expected = 0;
        actual = hex.getDistanceOverBoundary(a);
        assertEquals(expected, actual);

        expected = 0;
        actual = hex.getDistanceOverBoundary(b);
        assertEquals(expected, actual);

        expected = 0;
        actual = hex.getDistanceOverBoundary(c);
        assertEquals(expected, actual);

        expected = 0;
        actual = hex.getDistanceOverBoundary(d);
        assertEquals(expected, actual);
    }


    @Test
    public void testOverboundsCorners() {
        Coordinate p, q, r, s, t, u;

        p = new Coordinate2D(5, 2, 0);   // +3u
        q = new Coordinate2D(5, 5, 0);   // +3v
        r = new Coordinate2D(2, 5, 0);   // +3w
        s = new Coordinate2D(-1, 2, 0);  // -3u
        t = new Coordinate2D(-1, -1, 0); // -3v
        u = new Coordinate2D(2, -1, 0); // -3w


        int actual, expected;

        expected = 1;
        actual = hex.getDistanceOverBoundary(p);
        assertEquals(expected, actual);

        expected = 1;
        actual = hex.getDistanceOverBoundary(q);
        assertEquals(expected, actual);

        expected = 1;
        actual = hex.getDistanceOverBoundary(r);
        assertEquals(expected, actual);

        expected = 1;
        actual = hex.getDistanceOverBoundary(s);
        assertEquals(expected, actual);

        expected = 1;
        actual = hex.getDistanceOverBoundary(t);
        assertEquals(expected, actual);

        expected = 1;
        actual = hex.getDistanceOverBoundary(u);
        assertEquals(expected, actual);
    }

    @Test
    public void testOverboundsOffCorners() {
        Coordinate p, q, r, s, t;

        int actual, expected;

        p = new Coordinate2D(-1, 0, 0); // Expect -u
        expected = 1;
        actual = hex.getDistanceOverBoundary(p);
        assertEquals(expected, actual);

        q = new Coordinate2D(-1, 1, 0); // Expect -v
        expected = 1;
        actual = hex.getDistanceOverBoundary(q);
        assertEquals(expected, actual);

        r = new Coordinate2D(1, 4, 0);  // Expect -u
        expected = 1;
        actual = hex.getDistanceOverBoundary(r);
        assertEquals(expected, actual);

        s = new Coordinate2D(5, 4, 0);  // Expect +u
        expected = 1;
        actual = hex.getDistanceOverBoundary(s);
        assertEquals(expected, actual);

        t = new Coordinate2D(5, 3, 0);  // Expect +v
        expected = 1;
        actual = hex.getDistanceOverBoundary(t);
        assertEquals(expected, actual);
    }

    @Test
    public void testOverboundsAmbiguous() {
        Coordinate p, q, r;
        p = new Coordinate2D(-1, 3, 0); // Expect -u +w (because we prefer minimum in each direction).
        q = new Coordinate2D(0, 4, 0);  // Expect -2u (because we prefer u over w).
        r = new Coordinate2D(4, 6, 0);  // Expect +2w (because we never break a tie in favor of v).

        int actual, expected;


        expected = 2;
        actual = hex.getDistanceOverBoundary(p);
        assertEquals(expected, actual);

        expected = 2;
        actual = hex.getDistanceOverBoundary(q);
        assertEquals(expected, actual);

        expected = 2;
        actual = hex.getDistanceOverBoundary(r);
        assertEquals(expected, actual);
    }

    @Test
    public void testDimensions() {
        int[] actual, expected;
        expected = new int[]{5, 5, 5};
        actual = hex.getDimensions();
        assertArraysEqual(actual, expected, false);
    }


    /**
     * Make sure that getNeighbors() returns the correct
     * coordinates.
     */
    @Test
    public void testNeighbors() {

        // Make a bigger nanoverse.runtime.geometry than the one from setUp
        Lattice lattice = new TriangularLattice();
        hex = new Hexagon(lattice, 3);
        Boundary boundary = new Arena(hex, lattice);
        Geometry geometry = new Geometry(lattice, hex, boundary);

        Coordinate query;

        // Check center
        query = new Coordinate2D(2, 2, 0);
        assertNeighborCount(6, query, geometry);

        // Check corner
        query = new Coordinate2D(0, 0, 0);
        assertNeighborCount(3, query, geometry);

        // Check side
        query = new Coordinate2D(1, 0, 0);
        assertNeighborCount(4, query, geometry);
    }

    private void assertNeighborCount(int expected, Coordinate query, Geometry geometry) {
        Coordinate[] neighbors = geometry.getNeighbors(query, Geometry.EXCLUDE_BOUNDARIES);
        int actual = neighbors.length;
        assertEquals(expected, actual);
    }

    @Test
    public void testCloneAtScale() {
        Lattice clonedLattice = lattice.clone();
        Shape cloned = hex.cloneAtScale(clonedLattice, 2.0);

        assertEquals(hex.getClass(), cloned.getClass());
        assertEquals(19, hex.getCanonicalSites().length);
        assertEquals(61, cloned.getCanonicalSites().length);
    }

    @Test(expected = NotYetImplementedException.class)
    public void testGetOverboundsThrows() {
        Coordinate c = mock(Coordinate.class);
        hex.getOverbounds(c);
    }
}
