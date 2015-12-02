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

package nanoverse.runtime.geometry.boundaries.helpers;

import nanoverse.runtime.control.identifiers.*;
import nanoverse.runtime.geometry.lattice.*;
import nanoverse.runtime.geometry.shape.Rectangle;
import org.junit.*;

import static org.junit.Assert.assertEquals;

/**
 * Created by dbborens on 12/2/2015.
 */
public class ReflectHelper2DTriFunctionalTest {
    private ReflectHelper2D query;

    @Before
    public void before() throws Exception {
        Lattice lattice = new TriangularLattice();
        Rectangle shape = new Rectangle(lattice, 4, 4);
        query = new ReflectHelper2D(shape, lattice);
    }

    @Test
    public void testReflectAbove() {
        Coordinate initial, actual, expected;

        initial = new Coordinate2D(2, 5, 0);
        expected = new Coordinate2D(2, 4, Flags.BOUNDARY_APPLIED);
        actual = query.yReflect(initial);
        assertEquals(expected, actual);

        initial = new Coordinate2D(2, 6, 0);
        expected = new Coordinate2D(2, 3, Flags.BOUNDARY_APPLIED);
        actual = query.yReflect(initial);
        assertEquals(expected, actual);

        initial = new Coordinate2D(2, 7, 0);
        expected = new Coordinate2D(2, 2, Flags.BOUNDARY_APPLIED);
        actual = query.yReflect(initial);
        assertEquals(expected, actual);


    }

    @Test
    public void extremeReflections() {
        Coordinate initial, actual, expected;

        initial = new Coordinate2D(2, 8, 0);
        expected = new Coordinate2D(2, 1, Flags.BOUNDARY_APPLIED);
        actual = query.yReflect(initial);
        assertEquals(expected, actual);

        initial = new Coordinate2D(2, 9, 0);
        expected = new Coordinate2D(2, 1, Flags.BOUNDARY_APPLIED);
        actual = query.yReflect(initial);
        assertEquals(expected, actual);

        initial = new Coordinate2D(2, 10, 0);
        expected = new Coordinate2D(2, 2, Flags.BOUNDARY_APPLIED);
        actual = query.yReflect(initial);
        assertEquals(expected, actual);
    }

    @Test
    public void testReflectBelow() {
        Coordinate initial, actual, expected;

        initial = new Coordinate2D(2, 0, 0);
        expected = new Coordinate2D(2, 1, Flags.BOUNDARY_APPLIED);
        actual = query.yReflect(initial);
        assertEquals(expected, actual);

        initial = new Coordinate2D(2, -1, 0);
        expected = new Coordinate2D(2, 2, Flags.BOUNDARY_APPLIED);
        actual = query.yReflect(initial);
        assertEquals(expected, actual);

        initial = new Coordinate2D(2, -2, 0);
        expected = new Coordinate2D(2, 3, Flags.BOUNDARY_APPLIED);
        actual = query.yReflect(initial);
        assertEquals(expected, actual);
    }
}