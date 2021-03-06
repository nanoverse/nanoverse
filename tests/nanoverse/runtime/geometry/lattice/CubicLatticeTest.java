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

public class CubicLatticeTest extends LegacyTest {
    private Lattice lattice;

    @Before
    public void setUp() throws Exception {
        lattice = new CubicLattice();
    }

    @Test
    public void testDimensionality() {
        assertEquals(3, lattice.getDimensionality());
    }

    @Test
    public void testConnectivity() {
        assertEquals(3, lattice.getConnectivity());
    }

    @Test
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

    @Test
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

    @Test
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

    @Test
    public void testGetNeighborhoodDistance() {
        Coordinate o, p, q, r, s;
        int expected, actual;

        o = new Coordinate3D(0, 0, 0, 0);
        p = new Coordinate3D(3, 0, 0, 0);
        q = new Coordinate3D(0, 3, 0, 0);
        r = new Coordinate3D(0, 0, 3, 0);
        s = new Coordinate3D(3, 3, 3, 0);

        // +i
        actual = lattice.getNeighborhoodDistance(o, p);
        expected = 3;
        assertEquals(expected, actual);

        // +j
        actual = lattice.getNeighborhoodDistance(o, q);
        expected = 3;
        assertEquals(expected, actual);

        // +k
        actual = lattice.getNeighborhoodDistance(o, r);
        expected = 3;
        assertEquals(expected, actual);

        // +ijk
        actual = lattice.getNeighborhoodDistance(o, s);
        expected = 9;
        assertEquals(expected, actual);
    }

    @Test
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

    @Test
    public void testClone() {
        Object cloned = lattice.clone();
        assertEquals(lattice.getClass(), cloned.getClass());
        assertFalse(lattice == cloned);
    }

    @Test
    public void testGetZeroVector() {
        Coordinate expected = new Coordinate3D(0, 0, 0, 0);
        Coordinate actual = lattice.getZeroVector();
        assertEquals(expected, actual);
    }
}
