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

package geometry.set;//import junit.framework.TestCase;

import control.arguments.Argument;
import control.arguments.ConstantInteger;
import control.identifiers.Coordinate;
import geometry.Geometry;
import geometry.boundaries.Arena;
import geometry.boundaries.Boundary;
import geometry.lattice.*;
import geometry.shape.*;
import test.EslimeTestCase;

public class DiscSetTest extends EslimeTestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    public void test1D() {
        Lattice lattice = new LinearLattice();
        Shape shape = new Line(lattice, 9);
        Boundary boundary = new Arena(shape, lattice);
        Geometry geom = new Geometry(lattice, shape, boundary);

        // Center is (0, 4), offset of -1 --> disc is centered around (0, 3)
        CustomSet expected = new CustomSet();
        expected.add(new Coordinate(0, 2, 0));
        expected.add(new Coordinate(0, 3, 0));
        expected.add(new Coordinate(0, 4, 0));

        Argument<Integer> radiusArg = new ConstantInteger(1);
        Coordinate offset = new Coordinate(0, -1, 0);
        DiscSet actual = new DiscSet(geom, radiusArg, offset);

        assertEquals(expected, actual);
    }

    public void test2DTri() {
        Lattice lattice = new TriangularLattice();
        Shape shape = new Hexagon(lattice, 2);
        Boundary boundary = new Arena(shape, lattice);
        Geometry geom = new Geometry(lattice, shape, boundary);
        CustomSet expected = new CustomSet();
        expected.add(new Coordinate(0, 0, 0));
        expected.add(new Coordinate(1, 0, 0));
        expected.add(new Coordinate(2, 1, 0));
        expected.add(new Coordinate(2, 2, 0));
        expected.add(new Coordinate(1, 2, 0));
        expected.add(new Coordinate(0, 1, 0));
        expected.add(new Coordinate(1, 1, 0));
        Argument<Integer> radiusArg = new ConstantInteger(1);
        Coordinate offset = new Coordinate(0, -1, 0, 0);
        DiscSet actual = new DiscSet(geom, radiusArg, offset);
        assertEquals(expected, actual);
    }

    public void test2DRec() {
        Lattice lattice = new RectangularLattice();

        // Center is (2, 2)
        Shape shape = new Rectangle(lattice, 5, 5);
        Boundary boundary = new Arena(shape, lattice);
        Geometry geom = new Geometry(lattice, shape, boundary);
        Argument<Integer> radiusArg = new ConstantInteger(1);
        Coordinate offset = new Coordinate(-1, -1, 0);
        DiscSet actual = new DiscSet(geom, radiusArg, offset);

        CustomSet expected = new CustomSet();
        expected.add(new Coordinate(0, 1, 0));
        expected.add(new Coordinate(1, 0, 0));
        expected.add(new Coordinate(1, 1, 0));
        expected.add(new Coordinate(1, 2, 0));
        expected.add(new Coordinate(2, 1, 0));
        assertEquals(expected, actual);
    }

    public void test3D() {
        Lattice lattice = new CubicLattice();
        Shape shape = new Cuboid(lattice, 5, 5, 5);
        Boundary boundary = new Arena(shape, lattice);
        Geometry geom = new Geometry(lattice, shape, boundary);
        Coordinate offset = new Coordinate(-1, -1, -1, 0);
        Argument<Integer> radiusArg = new ConstantInteger(1);
        DiscSet actual = new DiscSet(geom, radiusArg, offset);

        CustomSet expected = new CustomSet();
        expected.add(new Coordinate(1, 1, 1, 0));
        expected.add(new Coordinate(0, 1, 1, 0));
        expected.add(new Coordinate(2, 1, 1, 0));
        expected.add(new Coordinate(1, 0, 1, 0));
        expected.add(new Coordinate(1, 2, 1, 0));
        expected.add(new Coordinate(1, 1, 2, 0));
        expected.add(new Coordinate(1, 1, 0, 0));

        assertEquals(expected, actual);
    }
}