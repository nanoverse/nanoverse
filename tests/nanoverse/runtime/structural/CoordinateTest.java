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

package nanoverse.runtime.structural;

import nanoverse.runtime.control.identifiers.*;
import org.junit.Test;

import java.util.HashSet;

import static org.junit.Assert.*;

public class CoordinateTest {

    @Test
    public void testConstructors2D() {
        // Construct a 3D coordinate.
        Coordinate first = new Coordinate3D(2, 4, 6, 0);

        // Check values and dimensionality flag.
        assertEquals(first.x(), 2);
        assertEquals(first.y(), 4);
        assertEquals(first.z(), 6);
        assertEquals(first.flags(), 0);

        // Copy that coordinate into another coordinate.
        Coordinate second = first;

        // Check overloaded equality operator.
        assertEquals(first, second);

        // Replace one of the coordinates with something different.
        second = new Coordinate3D(3, 6, 9, 0);

        // Check values of new coordinate.
        assertEquals(second.x(), 3);
        assertEquals(second.y(), 6);
        assertEquals(second.z(), 9);

        // Verify non-equality.
        assertFalse(first.equals(second));
    }

    @Test
    public void testConstructors3D() {
        // Construct a 2D coordinate.
        Coordinate first = new Coordinate2D(2, 4, 0);

        // Check values and dimensionality flag.
        assertEquals(first.x(), 2);
        assertEquals(first.y(), 4);
        assertEquals(Flags.PLANAR, first.flags());

        // Copy that coordinate into another coordinate.
        Coordinate second = first;

        // Check overloaded equality operator.
        assertEquals(first, second);

        // Replace one of the coordinates with something different.
        second = new Coordinate2D(3, 6, 0);

        // Check values of new coordinate.
        assertEquals(second.x(), 3);
        assertEquals(second.y(), 6);
        assertEquals(second.flags(), Flags.PLANAR);

        // Verify non-equality.
        assertFalse(first.equals(second));
    }

    @Test
    public void testFlags() {
        // Create a coordinate with a couple of flags.
        Coordinate first = new Coordinate2D(2, 4, Flags.BOUNDARY_APPLIED | Flags.BEYOND_BOUNDS);

        // Verify that hasFlag(...) works for each.
        assertEquals(first.flags(), Flags.BOUNDARY_APPLIED | Flags.BEYOND_BOUNDS | Flags.PLANAR);
        assertTrue(first.hasFlag(Flags.BOUNDARY_APPLIED));
        assertTrue(first.hasFlag(Flags.BEYOND_BOUNDS));
        assertTrue(first.hasFlag(Flags.PLANAR));

        // Create a second coordinate at same location but without flags.
        Coordinate second = new Coordinate2D(2, 4, 0);
        assertEquals(second.flags(), Flags.PLANAR);

        // Verify non-equality.
        assertFalse(first.equals(second));
    }

    @Test
    public void testStrings() {
        // Create a 2D coordinate.
        Coordinate first = new Coordinate2D(2, 4, 0);

        // EXPECT_STREQ, but it was acting weird -- done is better than perfect
        // Verify expected string form.

        assertEquals("(2, 4 | 1)", first.stringForm());

        // Verify expected vector form.
        Coordinate firstVector = first.addFlags(Flags.VECTOR);
        assertEquals("<2, 4>", firstVector.stringForm());

        // Create a 3D coordinate.
        Coordinate second = new Coordinate3D(2, 4, 6, 0);

        // Verify expected string form.
        assertEquals("(2, 4, 6 | 0)", second.stringForm());

        // Verify expected vector form.
        Coordinate secondVector = second.addFlags(Flags.VECTOR);
        assertEquals("<2, 4, 6>", secondVector.stringForm());

    }

    @Test
    public void testHashing() {
        // Create two logically equivalent coordinates.
        Coordinate first = new Coordinate2D(2, 4, 0);
        Coordinate second = new Coordinate2D(2, 4, 0);

        // Note that GoogleTest's assertEquals does not use the equality operator,
        // which is weird.

        assertEquals(first, second);

        // Create a logically different coordinate.
        Coordinate third = new Coordinate2D(3, 6, 0);
        assertFalse(second.equals(third));

        // Adding two logically different coordinates to a set should be fine.
        HashSet<Coordinate> coords = new HashSet<Coordinate>();
        assertEquals(coords.size(), 0);

        coords.add(first);
        assertEquals(coords.size(), 1);

        coords.add(third);
        assertEquals(coords.size(), 2);


        // Adding a coordinate that is logically identical to one already present
        // shouldn't add anything
        coords.add(second);
        assertEquals(coords.size(), 2);
    }

    @Test
    public void testAddFlags() {
        Coordinate c = new Coordinate3D(1, 2, 3, Flags.END_OF_WORLD);
        Coordinate d = c.addFlags(Flags.BOUNDARY_APPLIED);

        assertFalse(c.hasFlag(Flags.BOUNDARY_APPLIED));
        assertEquals(c.flags() | Flags.BOUNDARY_APPLIED, d.flags());
    }

    @Test
    public void testNorm() {
        Coordinate c = new Coordinate3D(0, 0, 0, 0);
        assertEquals(0, c.norm());

        c = new Coordinate2D(1, 0, 0);
        assertEquals(1, c.norm());

        c = new Coordinate2D(-2, 2, 5);
        assertEquals(4, c.norm());
    }

    @Test
    public void testClone() {
        Coordinate c = new Coordinate3D(1, 2, 3, 4);
        Coordinate d = c.clone();

        // Memory addresses should be different
        assertFalse(c == d);

        // Objects should be identical
        assertEquals(c, d);
    }

    @Test
    public void testCanonicalize() {
        int flags = Flags.BEYOND_BOUNDS | Flags.BOUNDARY_APPLIED | Flags.BOUNDARY_IGNORED | Flags.END_OF_WORLD;

        Coordinate a = new Coordinate2D(0, 0, flags);
        Coordinate b = new Coordinate3D(0, 0, 0, flags);

        Coordinate a1 = a.canonicalize();
        Coordinate b1 = b.canonicalize();

        assertEquals(Flags.PLANAR, a1.flags());
        assertEquals(0, b1.flags());
    }
}
