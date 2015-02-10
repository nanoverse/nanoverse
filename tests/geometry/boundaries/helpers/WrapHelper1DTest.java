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

package geometry.boundaries.helpers;

import control.identifiers.Coordinate;
import geometry.lattice.Lattice;
import geometry.lattice.LinearLattice;
import geometry.shape.Line;
import geometry.shape.Shape;
import test.EslimeTestCase;

public class WrapHelper1DTest extends EslimeTestCase {

    private WrapHelper1D query;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        Lattice lattice = new LinearLattice();
        Shape shape = new Line(lattice, 5);
        query = new WrapHelper1D(shape, lattice);
    }

    public void testWrapAll() throws Exception {
        Coordinate toWrap = new Coordinate(0, 5, 0);
        Coordinate expected = new Coordinate(0, 0, 0);
        Coordinate actual = query.wrapAll(toWrap);
        assertEquals(expected, actual);
    }

    public void testXWrap() throws Exception {
        boolean thrown = false;

        try {
            query.xWrap(null);
        } catch (UnsupportedOperationException ex) {
            thrown = true;
        }

        assertTrue(thrown);
    }

    public void testYWrap() throws Exception {
        // Positive out of bounds Y coordinate
        Coordinate toWrap = new Coordinate(0, 6, 0);
        Coordinate expected = new Coordinate(0, 1, 0);
        Coordinate actual = query.yWrap(toWrap);
        assertEquals(expected, actual);

        // Negative out of bounds Y coordinate
        toWrap = new Coordinate(0, -1, 0);
        expected = new Coordinate(0, 4, 0);
        actual = query.yWrap(toWrap);
        assertEquals(expected, actual);
    }

    public void testZWrap() throws Exception {
        boolean thrown = false;

        try {
            query.zWrap(null);
        } catch (UnsupportedOperationException ex) {
            thrown = true;
        }

        assertTrue(thrown);
    }

    public void testAllInBounds() throws Exception {
        Coordinate toWrap = new Coordinate(0, 3, 0);
        Coordinate expected = new Coordinate(0, 3, 0);
        Coordinate actual = query.wrapAll(toWrap);
        assertEquals(expected, actual);
    }
}
