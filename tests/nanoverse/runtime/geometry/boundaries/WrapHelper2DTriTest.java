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

package nanoverse.runtime.geometry.boundaries;

import nanoverse.runtime.control.identifiers.*;
import nanoverse.runtime.geometry.boundaries.helpers.WrapHelper2D;
import nanoverse.runtime.geometry.lattice.*;
import nanoverse.runtime.geometry.shape.*;
import org.junit.*;
import test.LegacyTest;

import static org.junit.Assert.*;

/**
 * Integration test for the interaction between
 * the 2D wrap helper and a triangular lattice
 * with even width.
 */
public class WrapHelper2DTriTest extends LegacyTest {

    private WrapHelper2D query;

    @Before
    public void setUp() throws Exception {
        Lattice lattice = new TriangularLattice();
        Shape shape = new Rectangle(lattice, 2, 2);
        query = new WrapHelper2D(shape, lattice);
    }

    @Test
    public void testWrapUpperRight() throws Exception {
        Coordinate toWrap = new Coordinate2D(2, 2, 0);
        Coordinate expected = new Coordinate2D(0, 1, 0);
        Coordinate actual = query.wrapAll(toWrap);
        assertEquals(expected, actual);
    }


    @Test
    public void testWrapUpperLeft() throws Exception {
        Coordinate toWrap = new Coordinate2D(-1, 1, 0);
        Coordinate expected = new Coordinate2D(1, 0, 0);
        Coordinate actual = query.wrapAll(toWrap);
        assertEquals(expected, actual);
    }

    @Test
    public void testWrapLowerRight() throws Exception {
        Coordinate toWrap = new Coordinate2D(2, 0, 0);
        Coordinate expected = new Coordinate2D(0, 1, 0);
        Coordinate actual = query.wrapAll(toWrap);
        assertEquals(expected, actual);
    }

    @Test
    public void testWrapLowerLeft() throws Exception {
        Coordinate toWrap = new Coordinate2D(-1, 1, 0);
        Coordinate expected = new Coordinate2D(1, 0, 0);
        Coordinate actual = query.wrapAll(toWrap);
        assertEquals(expected, actual);
    }

    @Test
    public void testWrapRight() throws Exception {
        Coordinate toWrap = new Coordinate2D(2, 1, 0);
        Coordinate expected = new Coordinate2D(0, 0, 0);
        Coordinate actual = query.xWrap(toWrap);
        assertEquals(expected, actual);
    }

    @Test
    public void testWrapLeft() throws Exception {
        Coordinate toWrap = new Coordinate2D(-1, 0, 0);
        Coordinate expected = new Coordinate2D(1, 1, 0);
        Coordinate actual = query.xWrap(toWrap);
        assertEquals(expected, actual);
    }

    @Test
    public void testWrapTop() throws Exception {
        Coordinate toWrap = new Coordinate2D(1, 2, 0);
        Coordinate expected = new Coordinate2D(1, 0, 0);
        Coordinate actual = query.yWrap(toWrap);
        assertEquals(expected, actual);
    }

    @Test
    public void testWrapBottom() throws Exception {
        Coordinate toWrap = new Coordinate2D(1, -1, 0);
        Coordinate expected = new Coordinate2D(1, 1, 0);
        Coordinate actual = query.yWrap(toWrap);
        assertEquals(expected, actual);
    }

    @Test
    public void testZWrap() throws Exception {
        boolean thrown = false;

        try {
            query.zWrap(null);
        } catch (UnsupportedOperationException ex) {
            thrown = true;
        }

        assertTrue(thrown);
    }

    @Test
    public void testAllInBounds() throws Exception {
        Coordinate toWrap = new Coordinate2D(1, 1, 0);
        Coordinate expected = new Coordinate2D(1, 1, 0);
        Coordinate actual = query.wrapAll(toWrap);
        assertEquals(expected, actual);
    }

}