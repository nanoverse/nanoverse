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
import nanoverse.runtime.geometry.boundaries.*;
import nanoverse.runtime.geometry.lattice.*;
import nanoverse.runtime.geometry.shape.*;
import org.junit.*;
import test.LegacyTest;

import static org.junit.Assert.*;

public class ArenaTest extends LegacyTest {

    private Boundary rect;
    private Boundary tri;

    @Before
    public void setUp() {
        Lattice rectLattice = new RectangularLattice();
        Lattice triLattice = new TriangularLattice();

        Shape rectShape = new Rectangle(rectLattice, 3, 5);
        Shape triShape = new Rectangle(triLattice, 3, 5);

        rect = makeBoundary(rectShape, rectLattice);
        tri = makeBoundary(triShape, triLattice);
    }

    protected Boundary makeBoundary(Shape shape, Lattice lattice) {
        return new Arena(shape, lattice);
    }

    @Test
    public void testInfinite() {
        assertTrue(rect.isInfinite());
        assertTrue(tri.isInfinite());
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

        Coordinate actual, expected;

        // p
        actual = rect.apply(p);
        assertFalse(actual.hasFlag(Flags.UNDEFINED));
        expected = p.addFlags(Flags.END_OF_WORLD | Flags.BOUNDARY_APPLIED);
        assertEquals(expected, actual);

        actual = tri.apply(p);
        assertFalse(actual.hasFlag(Flags.UNDEFINED));
        assertEquals(expected, actual);

        // q
        actual = rect.apply(q);
        assertFalse(actual.hasFlag(Flags.UNDEFINED));
        expected = q.addFlags(Flags.END_OF_WORLD | Flags.BOUNDARY_APPLIED);
        assertEquals(expected, actual);

        actual = tri.apply(q);
        assertFalse(actual.hasFlag(Flags.UNDEFINED));
        assertEquals(expected, actual);
    }

    @Test
    public void testApplyOutsideY() {
        Coordinate p, q;
        p = new Coordinate2D(0, 5, 0);
        q = new Coordinate2D(2, -1, 0);

        Coordinate actual, expected;

        // p
        actual = rect.apply(p);
        assertFalse(actual.hasFlag(Flags.UNDEFINED));
        expected = p.addFlags(Flags.END_OF_WORLD | Flags.BOUNDARY_APPLIED);
        assertEquals(expected, actual);

        actual = tri.apply(p);
        assertFalse(actual.hasFlag(Flags.UNDEFINED));
        assertEquals(expected, actual);

        // q
        actual = rect.apply(q);
        assertFalse(actual.hasFlag(Flags.UNDEFINED));
        expected = q.addFlags(Flags.END_OF_WORLD | Flags.BOUNDARY_APPLIED);
        assertEquals(expected, actual);

        actual = tri.apply(q);
        assertFalse(actual.hasFlag(Flags.UNDEFINED));
        assertEquals(expected, actual);
    }

    @Test
    public void testApplyOutsideXY() {
        Coordinate p, q;
        p = new Coordinate2D(-1, 4, 0);
        q = new Coordinate2D(5, -5, 0);

        Coordinate actual, expected;

        // p
        actual = rect.apply(p);
        assertFalse(actual.hasFlag(Flags.UNDEFINED));
        expected = p.addFlags(Flags.END_OF_WORLD | Flags.BOUNDARY_APPLIED);
        assertEquals(expected, actual);

        actual = tri.apply(p);
        assertFalse(actual.hasFlag(Flags.UNDEFINED));
        assertEquals(expected, actual);

        // q
        actual = rect.apply(q);
        assertFalse(actual.hasFlag(Flags.UNDEFINED));
        expected = q.addFlags(Flags.END_OF_WORLD | Flags.BOUNDARY_APPLIED);
        assertEquals(expected, actual);

        actual = tri.apply(q);
        assertFalse(actual.hasFlag(Flags.UNDEFINED));
        assertEquals(expected, actual);
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
