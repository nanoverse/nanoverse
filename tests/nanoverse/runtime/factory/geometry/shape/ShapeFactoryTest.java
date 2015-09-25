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

package nanoverse.runtime.factory.geometry.shape;

import nanoverse.runtime.geometry.lattice.*;
import nanoverse.runtime.geometry.shape.*;
import org.dom4j.Element;
import org.junit.*;
import test.LegacyTest;

import static org.junit.Assert.assertEquals;

public class ShapeFactoryTest extends LegacyTest {

    private Element root;

    @Before
    public void setUp() throws Exception {
        root = readXmlFile("factories/geometry/shape/ShapeFactoryTest.xml");
    }

    @Test
    public void testLineCase() {
        Element e = root.element("line-case");
        Lattice lattice = new LinearLattice();
        Shape expected = new Line(lattice, 1);
        Shape actual = ShapeFactory.instantiate(e, lattice);
        assertEquals(expected, actual);
    }

    @Test
    public void testRectangleCase() {
        Element e = root.element("rectangle-case");
        Lattice lattice = new RectangularLattice();
        Shape expected = new Rectangle(lattice, 2, 1);
        Shape actual = ShapeFactory.instantiate(e, lattice);
        assertEquals(expected, actual);
    }

    @Test
    public void testHexagonCase() {
        Element e = root.element("hexagon-case");
        Lattice lattice = new TriangularLattice();
        Shape expected = new Hexagon(lattice, 1);
        Shape actual = ShapeFactory.instantiate(e, lattice);
        assertEquals(expected, actual);
    }

    @Test
    public void testCuboidCase() {
        Element e = root.element("cuboid-case");
        Lattice lattice = new CubicLattice();
        Shape expected = new Cuboid(lattice, 1, 2, 3);
        Shape actual = ShapeFactory.instantiate(e, lattice);
        assertEquals(expected, actual);
    }
}