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

package nanoverse.runtime.geometry.set;

import nanoverse.runtime.control.identifiers.*;
import nanoverse.runtime.geometry.Geometry;
import nanoverse.runtime.geometry.boundaries.*;
import nanoverse.runtime.geometry.lattice.*;
import nanoverse.runtime.geometry.shape.*;
import org.junit.Test;
import test.LegacyTest;

import static org.junit.Assert.assertEquals;

public class CompleteSetTest extends LegacyTest {

    @Test
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

    @Test
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

    @Test
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

    @Test
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