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
import geometry.boundaries.helpers.PlaneRingHelper;
import geometry.lattice.*;
import org.junit.*;
import test.LegacyTest;

import static org.junit.Assert.assertEquals;

public class PlaneRingHelperTest extends LegacyTest {

    private PlaneRingHelper helper;

    @Before
    public void setUp() {
        Lattice lattice = new RectangularLattice();
        int[] dims = new int[]{4, 4};
        helper = new PlaneRingHelper(lattice, dims);
    }

    @Test
    public void testWrapLeft() {
        Coordinate initial, actual, expected;

        initial = new Coordinate2D(-1, 1, 0);
        expected = new Coordinate2D(3, 1, Flags.BOUNDARY_APPLIED);
        actual = helper.wrap(initial);
        assertEquals(expected, actual);

        initial = new Coordinate2D(-5, 2, 0);
        expected = new Coordinate2D(3, 2, Flags.BOUNDARY_APPLIED);
        actual = helper.wrap(initial);
        assertEquals(expected, actual);
    }

    @Test
    public void testWrapRight() {
        Coordinate initial, actual, expected;

        initial = new Coordinate2D(4, 1, 0);
        expected = new Coordinate2D(0, 1, Flags.BOUNDARY_APPLIED);
        actual = helper.wrap(initial);
        assertEquals(expected, actual);

        initial = new Coordinate2D(8, 2, 0);
        expected = new Coordinate2D(0, 2, Flags.BOUNDARY_APPLIED);
        actual = helper.wrap(initial);
        assertEquals(expected, actual);
    }

    @Test
    public void testReflectAbove() {
        Coordinate initial, actual, expected;

        initial = new Coordinate2D(2, 4, 0);
        expected = new Coordinate2D(2, 3, Flags.BOUNDARY_APPLIED);
        actual = helper.reflect(initial);
        assertEquals(expected, actual);

        initial = new Coordinate2D(3, 5, 0);
        expected = new Coordinate2D(3, 2, Flags.BOUNDARY_APPLIED);
        actual = helper.reflect(initial);
        assertEquals(expected, actual);

        initial = new Coordinate2D(0, 6, 0);
        expected = new Coordinate2D(0, 1, Flags.BOUNDARY_APPLIED);
        actual = helper.reflect(initial);
        assertEquals(expected, actual);

        initial = new Coordinate2D(1, 8, 0);
        expected = new Coordinate2D(1, 0, Flags.BOUNDARY_APPLIED);
        actual = helper.reflect(initial);
        assertEquals(expected, actual);
    }

    @Test
    public void testReflectBelow() {
        Coordinate initial, actual, expected;

        initial = new Coordinate2D(2, -1, 0);
        expected = new Coordinate2D(2, 0, Flags.BOUNDARY_APPLIED);
        actual = helper.reflect(initial);
        assertEquals(expected, actual);

        initial = new Coordinate2D(3, -2, 0);
        expected = new Coordinate2D(3, 1, Flags.BOUNDARY_APPLIED);
        actual = helper.reflect(initial);
        assertEquals(expected, actual);

        initial = new Coordinate2D(3, -4, 0);
        expected = new Coordinate2D(3, 3, Flags.BOUNDARY_APPLIED);
        actual = helper.reflect(initial);
        assertEquals(expected, actual);

        initial = new Coordinate2D(3, -5, 0);
        expected = new Coordinate2D(3, 3, Flags.BOUNDARY_APPLIED);
        actual = helper.reflect(initial);
        assertEquals(expected, actual);
    }


}
