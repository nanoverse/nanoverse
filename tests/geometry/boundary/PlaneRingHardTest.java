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

package geometry.boundary;

import control.identifiers.*;
import geometry.boundaries.*;
import geometry.lattice.*;
import geometry.shape.*;
import org.junit.*;
import test.EslimeTestCase;

import static org.junit.Assert.*;
public class PlaneRingHardTest extends EslimeTestCase {
    private Boundary rect;
    private Boundary tri;

    @Before
    public void setUp() {
        Lattice rectLattice = new RectangularLattice();
        Lattice triLattice = new TriangularLattice();

        Shape rectShape = new Rectangle(rectLattice, 5, 3);
        Shape triShape = new Rectangle(triLattice, 5, 3);

        rect = new PlaneRingHard(rectShape, rectLattice);
        tri = new PlaneRingHard(triShape, triLattice);
    }

    @Test
    public void testInfinite() {
        assertFalse(rect.isInfinite());
        assertFalse(tri.isInfinite());
    }

    @Test
    public void testApplyInBounds() {
        // These are in bounds for both triangular and rectangular
        Coordinate a, b, c;
        a = new Coordinate2D(0, 0, 0);
        b = new Coordinate2D(1, 1, 0);
        c = new Coordinate2D(2, 2, 0);

        // Rectangular
        Coordinate actual, expected;
        expected = new Coordinate2D(0, 0, 0);
        actual = rect.apply(a);
        assertEquals(expected, actual);

        expected = new Coordinate2D(1, 1, 0);
        actual = rect.apply(b);
        assertEquals(expected, actual);

        expected = new Coordinate2D(2, 2, 0);
        actual = rect.apply(c);
        assertEquals(expected, actual);

        // Triangular
        expected = new Coordinate2D(0, 0, 0);
        actual = tri.apply(a);
        assertEquals(expected, actual);

        expected = new Coordinate2D(1, 1, 0);
        actual = tri.apply(b);
        assertEquals(expected, actual);

        expected = new Coordinate2D(2, 2, 0);
        actual = tri.apply(c);
        assertEquals(expected, actual);
    }

    @Test
    public void testApplyOutsideX() {
        Coordinate p, q;
        p = new Coordinate2D(-1, 1, 0);
        q = new Coordinate2D(5, 2, 0);

        Coordinate expected, actual;

        // Rectangular
        expected = new Coordinate2D(4, 1, Flags.BOUNDARY_APPLIED);
        actual = rect.apply(p);
        assertEquals(expected, actual);

        expected = new Coordinate2D(0, 2, Flags.BOUNDARY_APPLIED);
        actual = rect.apply(q);
        assertEquals(expected, actual);

        // Triangular
        expected = new Coordinate2D(4, 4, Flags.BOUNDARY_APPLIED);
        actual = tri.apply(p);
        assertEquals(expected, actual);

        expected = new Coordinate2D(0, 0, Flags.BOUNDARY_APPLIED);
        actual = tri.apply(q);
        assertEquals(expected, actual);
    }

    @Test
    public void testApplyOutsideY() {
        Coordinate p, q;
        p = new Coordinate2D(0, 4, 0);
        q = new Coordinate2D(2, -1, 0);

        Coordinate actual;

        // Rectangular
        actual = rect.apply(p);
        assertNull(actual);

        actual = rect.apply(q);
        assertNull(actual);

        // Triangular
        actual = tri.apply(p);
        assertNull(actual);

        actual = tri.apply(q);
        assertNull(actual);
    }

    @Test
    public void testApplyOutsideXY() {
        Coordinate p, q;
        p = new Coordinate2D(-1, 4, 0);
        q = new Coordinate2D(5, -5, 0);

        Coordinate actual;

        // Rectangular
        actual = rect.apply(p);
        assertNull(actual);

        actual = rect.apply(q);
        assertNull(actual);

        // Triangular
        actual = tri.apply(p);
        assertNull(actual);

        actual = tri.apply(q);
        assertNull(actual);
    }

    @Test
    public void testCloneWithArguments() {
        Lattice lattice = new RectangularLattice();
        Shape singleton = new Rectangle(lattice, 1, 1);

        Boundary query = rect.clone(singleton, lattice);

        // Boundaries are equal based on their class, not their dependencies
        assertEquals(rect, query);
        assertFalse(rect == query);
    }
}
