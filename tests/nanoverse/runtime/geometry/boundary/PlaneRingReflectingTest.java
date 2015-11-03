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

package nanoverse.runtime.geometry.boundary;

import nanoverse.runtime.control.identifiers.*;
import nanoverse.runtime.geometry.Geometry;
import nanoverse.runtime.geometry.boundaries.*;
import nanoverse.runtime.geometry.lattice.*;
import nanoverse.runtime.geometry.shape.*;
import org.junit.*;
import test.LegacyTest;

import static org.junit.Assert.*;

public class PlaneRingReflectingTest extends LegacyTest {
    private Boundary rect;
    private Boundary tri;

    private Lattice rectLattice;
    private Lattice triLattice;

    private Shape rectShape;
    private Shape triShape;

    @Before
    public void setUp() {
        rectLattice = new RectangularLattice();
        triLattice = new TriangularLattice();

        rectShape = new Rectangle(rectLattice, 6, 4);
        triShape = new Rectangle(triLattice, 6, 4);

        rect = new PlaneRingReflecting(rectShape, rectLattice);
        tri = new PlaneRingReflecting(triShape, triLattice);
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
        q = new Coordinate2D(6, 2, 0);

        Coordinate expected, actual;

        // Rectangular
        expected = new Coordinate2D(5, 1, Flags.BOUNDARY_APPLIED);
        actual = rect.apply(p);
        assertEquals(expected, actual);

        expected = new Coordinate2D(0, 2, Flags.BOUNDARY_APPLIED);
        actual = rect.apply(q);
        assertEquals(expected, actual);

        // Triangular
        expected = new Coordinate2D(5, 4, Flags.BOUNDARY_APPLIED);
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

        Coordinate expected, actual;

        // Rectangular
        expected = new Coordinate2D(0, 3, Flags.BOUNDARY_APPLIED);
        actual = rect.apply(p);
        assertEquals(expected, actual);

        expected = new Coordinate2D(2, 0, Flags.BOUNDARY_APPLIED);
        actual = rect.apply(q);
        assertEquals(expected, actual);

        // Triangular
        expected = new Coordinate2D(0, 3, Flags.BOUNDARY_APPLIED);
        actual = tri.apply(p);
        assertEquals(expected, actual);

        expected = new Coordinate2D(2, 2, Flags.BOUNDARY_APPLIED);
        actual = tri.apply(q);
        assertEquals(expected, actual);
    }

    @Test
    public void testApplyRectXY() {
        Coordinate p, q;
        p = new Coordinate2D(-1, 4, 0);
        q = new Coordinate2D(6, -1, 0);

        Coordinate actual, expected;

        expected = new Coordinate2D(5, 3, Flags.BOUNDARY_APPLIED);
        actual = rect.apply(p);
        assertEquals(expected, actual);

        expected = new Coordinate2D(0, 0, Flags.BOUNDARY_APPLIED);
        actual = rect.apply(q);
        assertEquals(expected, actual);

    }

    @Test
    public void testApplyTriXY() {
        Coordinate p, q;
        p = new Coordinate2D(-1, 3, 0);
        q = new Coordinate2D(6, 2, 0);

        Coordinate actual, expected;

        expected = new Coordinate2D(5, 5, Flags.BOUNDARY_APPLIED);
        actual = tri.apply(p);
        assertEquals(expected, actual);

        expected = new Coordinate2D(0, 0, Flags.BOUNDARY_APPLIED);
        actual = tri.apply(q);
        assertEquals(expected, actual);
    }

    /**
     * Integration test verifying that correct neighbors are being
     * reported. Created for bug report 62997644.
     */
    @Test
    public void testBoundaryNeighbors() {
        Geometry geom = new Geometry(rectLattice, rectShape, rect);

        Coordinate[] expected, actual;
        Coordinate query;

        Coordinate bottom = new Coordinate2D(2, 0, 0);
        Coordinate top = new Coordinate2D(2, 3, 0);
        Coordinate leftmost = new Coordinate2D(0, 2, 0);
        Coordinate rightmost = new Coordinate2D(5, 2, 0);

        // Test bottom position
        query = bottom;
        expected = new Coordinate2D[]{
            new Coordinate2D(1, 0, 0),
            new Coordinate2D(3, 0, 0),
            new Coordinate2D(2, 0, Flags.BOUNDARY_APPLIED),  // Reflect up
            new Coordinate2D(2, 1, 0)
        };
        actual = geom.getNeighbors(query, Geometry.APPLY_BOUNDARIES);
        assertArraysEqual(expected, actual, true);

        // Test top position
        query = top;
        expected = new Coordinate2D[]{
            new Coordinate2D(1, 3, 0),
            new Coordinate2D(3, 3, 0),
            new Coordinate2D(2, 3, Flags.BOUNDARY_APPLIED),  // Reflect down
            new Coordinate2D(2, 2, 0)
        };
        actual = geom.getNeighbors(query, Geometry.APPLY_BOUNDARIES);
        assertArraysEqual(expected, actual, true);

        // Test leftmost position
        query = leftmost;
        expected = new Coordinate2D[]{
            new Coordinate2D(5, 2, Flags.BOUNDARY_APPLIED),  // Wrap to right
            new Coordinate2D(1, 2, 0),
            new Coordinate2D(0, 1, 0),
            new Coordinate2D(0, 3, 0)
        };
        actual = geom.getNeighbors(query, Geometry.APPLY_BOUNDARIES);
        assertArraysEqual(expected, actual, true);

        // Test rightmost position
        query = rightmost;
        expected = new Coordinate2D[]{
            new Coordinate2D(4, 2, 0),
            new Coordinate2D(0, 2, Flags.BOUNDARY_APPLIED),  // Wrap to left
            new Coordinate2D(5, 3, 0),
            new Coordinate2D(5, 1, 0)
        };
        actual = geom.getNeighbors(query, Geometry.APPLY_BOUNDARIES);
        assertArraysEqual(expected, actual, true);

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
