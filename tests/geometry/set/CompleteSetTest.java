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

import control.identifiers.Coordinate;
import control.identifiers.Coordinate2D;
import control.identifiers.Coordinate3D;
import geometry.Geometry;
import geometry.boundaries.Arena;
import geometry.boundaries.Boundary;
import geometry.lattice.*;
import geometry.shape.*;
import test.EslimeTestCase;

public class CompleteSetTest extends EslimeTestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    public void test1D() {
        Lattice lattice = new LinearLattice();
        Shape shape = new Line(lattice, 4);
        Boundary boundary = new Arena(shape, lattice);
        Geometry geom = new Geometry(lattice, shape, boundary);
        CustomSet expected = new CustomSet();
        expected.add(new Coordinate2D(0, 0, 0));
        expected.add(new Coordinate2D(0, 1, 0));
        expected.add(new Coordinate2D(0, 2, 0));
        expected.add(new Coordinate2D(0, 3, 0));
        CompleteSet actual = new CompleteSet(geom);
        assertEquals(expected, actual);
    }

    public void test2DTri() {
        Lattice lattice = new TriangularLattice();
        Shape shape = new Hexagon(lattice, 1);
        Boundary boundary = new Arena(shape, lattice);
        Geometry geom = new Geometry(lattice, shape, boundary);
        CompleteSet actual = new CompleteSet(geom);
        CustomSet expected = new CustomSet();
        expected.add(new Coordinate2D(0, 0, 0));
        expected.add(new Coordinate2D(1, 0, 0));
        expected.add(new Coordinate2D(2, 1, 0));
        expected.add(new Coordinate2D(2, 2, 0));
        expected.add(new Coordinate2D(1, 2, 0));
        expected.add(new Coordinate2D(0, 1, 0));
        expected.add(new Coordinate2D(1, 1, 0));
        assertEquals(expected, actual);
    }

    public void test2DRec() {
        Lattice lattice = new RectangularLattice();
        Shape shape = new Rectangle(lattice, 2, 2);
        Boundary boundary = new Arena(shape, lattice);
        Geometry geom = new Geometry(lattice, shape, boundary);
        CustomSet expected = new CustomSet();
        expected.add(new Coordinate2D(0, 0, 0));
        expected.add(new Coordinate2D(0, 1, 0));
        expected.add(new Coordinate2D(1, 0, 0));
        expected.add(new Coordinate2D(1, 1, 0));
        CompleteSet actual = new CompleteSet(geom);
        assertEquals(expected, actual);
    }

    public void test3D() {
        Lattice lattice = new CubicLattice();
        Shape shape = new Cuboid(lattice, 2, 2, 2);
        Boundary boundary = new Arena(shape, lattice);
        Geometry geom = new Geometry(lattice, shape, boundary);
        CustomSet expected = new CustomSet();
        expected.add(new Coordinate3D(0, 0, 0, 0));
        expected.add(new Coordinate3D(0, 0, 1, 0));
        expected.add(new Coordinate3D(0, 1, 0, 0));
        expected.add(new Coordinate3D(0, 1, 1, 0));
        expected.add(new Coordinate3D(1, 0, 0, 0));
        expected.add(new Coordinate3D(1, 0, 1, 0));
        expected.add(new Coordinate3D(1, 1, 0, 0));
        expected.add(new Coordinate3D(1, 1, 1, 0));
        CompleteSet actual = new CompleteSet(geom);
        assertEquals(expected, actual);
    }
}