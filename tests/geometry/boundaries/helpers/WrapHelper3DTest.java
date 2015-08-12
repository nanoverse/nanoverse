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
import control.identifiers.Coordinate3D;
import geometry.lattice.CubicLattice;
import geometry.lattice.Lattice;
import geometry.shape.Cuboid;
import geometry.shape.Shape;
import junit.framework.TestCase;

public class WrapHelper3DTest extends TestCase {


    private WrapHelper3D query;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        Lattice lattice = new CubicLattice();
        Shape shape = new Cuboid(lattice, 5, 5, 5);
        query = new WrapHelper3D(shape, lattice);
    }

    public void testWrapAll() throws Exception {
        Coordinate toWrap = new Coordinate3D(5, 5, 5, 0);
        Coordinate expected = new Coordinate3D(0, 0, 0, 0);
        Coordinate actual = query.wrapAll(toWrap);
        assertEquals(expected, actual);
    }

    public void testXWrap() throws Exception {
        // Positive out of bounds X coordinate
        Coordinate toWrap = new Coordinate3D(6, 0, 0, 0);
        Coordinate expected = new Coordinate3D(1, 0, 0, 0);
        Coordinate actual = query.xWrap(toWrap);
        assertEquals(expected, actual);

        // Negative out of bounds X coordinate
        toWrap = new Coordinate3D(-1, 0, 0, 0);
        expected = new Coordinate3D(4, 0, 0, 0);
        actual = query.xWrap(toWrap);
        assertEquals(expected, actual);
    }

    public void testYWrap() throws Exception {
        // Positive out of bounds Y coordinate
        Coordinate toWrap = new Coordinate3D(0, 6, 0, 0);
        Coordinate expected = new Coordinate3D(0, 1, 0, 0);
        Coordinate actual = query.yWrap(toWrap);
        assertEquals(expected, actual);

        // Negative out of bounds Y coordinate
        toWrap = new Coordinate3D(0, -1, 0, 0);
        expected = new Coordinate3D(0, 4, 0, 0);
        actual = query.yWrap(toWrap);
        assertEquals(expected, actual);
    }

    public void testZWrap() throws Exception {
        // Positive out of bounds Z coordinate
        Coordinate toWrap = new Coordinate3D(0, 0, 6, 0);
        Coordinate expected = new Coordinate3D(0, 0, 1, 0);
        Coordinate actual = query.zWrap(toWrap);
        assertEquals(expected, actual);

        // Negative out of bounds Z coordinate
        toWrap = new Coordinate3D(0, 0, -1, 0);
        expected = new Coordinate3D(0, 0, 4, 0);
        actual = query.zWrap(toWrap);
        assertEquals(expected, actual);
    }

    public void testAllInBounds() throws Exception {
        Coordinate toWrap = new Coordinate3D(3, 3, 3, 0);
        Coordinate expected = new Coordinate3D(3, 3, 3, 0);
        Coordinate actual = query.wrapAll(toWrap);
        assertEquals(expected, actual);
    }
}