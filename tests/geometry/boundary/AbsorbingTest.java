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

import control.identifiers.Coordinate;
import control.identifiers.Coordinate2D;
import geometry.boundaries.Absorbing;
import geometry.boundaries.Boundary;
import geometry.lattice.Lattice;
import geometry.lattice.RectangularLattice;
import geometry.lattice.TriangularLattice;
import geometry.shape.Rectangle;
import geometry.shape.Shape;
import test.EslimeTestCase;

public class AbsorbingTest extends EslimeTestCase {

    private Boundary rect;
    private Boundary tri;

    public void setUp() {
        Lattice rectLattice = new RectangularLattice();
        Lattice triLattice = new TriangularLattice();

        Shape rectShape = new Rectangle(rectLattice, 3, 5);
        Shape triShape = new Rectangle(triLattice, 3, 5);

        rect = new Absorbing(rectShape, rectLattice);
        tri = new Absorbing(triShape, triLattice);
    }

    public void testInfinite() {
        assertFalse(rect.isInfinite());
        assertFalse(tri.isInfinite());
    }

    public void testApplyInBounds() {
        // These are in bounds for both triangular and rectangular
        Coordinate2D a, b, c;
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

    public void testApplyOutsideX() {
        Coordinate2D p, q;
        p = new Coordinate2D(-1, 1, 0);
        q = new Coordinate2D(5, 2, 0);

        Coordinate actual;

        // p
        actual = rect.apply(p);
        assertNull(actual);

        actual = tri.apply(p);
        assertNull(actual);

        // q
        actual = rect.apply(q);
        assertNull(actual);

        actual = tri.apply(q);
        assertNull(actual);
    }

    public void testApplyOutsideY() {
        Coordinate2D p, q;
        p = new Coordinate2D(0, 5, 0);
        q = new Coordinate2D(2, -1, 0);

        Coordinate actual;

        // p
        actual = rect.apply(p);
        assertNull(actual);

        actual = tri.apply(p);
        assertNull(actual);

        // q
        actual = rect.apply(q);
        assertNull(actual);

        actual = tri.apply(q);
        assertNull(actual);
    }

    public void testApplyOutsideXY() {
        Coordinate p, q;
        p = new Coordinate2D(-1, 4, 0);
        q = new Coordinate2D(5, -5, 0);

        Coordinate actual;

        // p
        actual = rect.apply(p);
        assertNull(actual);

        actual = tri.apply(p);
        assertNull(actual);

        // q
        actual = rect.apply(q);
        assertNull(actual);

        actual = tri.apply(q);
        assertNull(actual);
    }

    public void testCloneWithArguments() {
        Lattice lattice = new RectangularLattice();
        Shape singleton = new Rectangle(lattice, 1, 1);

        Boundary query = rect.clone(singleton, lattice);

        // Boundaries are equal based on their class, not their dependencies
        assertEquals(rect, query);
        assertFalse(rect == query);
    }
}