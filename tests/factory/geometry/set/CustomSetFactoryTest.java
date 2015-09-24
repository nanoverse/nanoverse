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

package factory.geometry.set;

import control.identifiers.Coordinate2D;
import geometry.Geometry;
import geometry.boundaries.*;
import geometry.lattice.*;
import geometry.set.*;
import geometry.shape.*;
import org.dom4j.Element;
import org.junit.*;
import structural.MockGeneralParameters;
import test.EslimeTestCase;

import static org.junit.Assert.assertEquals;

public class CustomSetFactoryTest extends EslimeTestCase {
    private Geometry geom;
    private Element root;
    private MockGeneralParameters p;

    @Before
    public void setUp() throws Exception {
        root = readXmlFile("factories/geometry/set/CustomSetFactoryTest.xml");
        p = makeMockGeneralParameters();
        geom = makeGeometry();
    }

    private Geometry makeGeometry() {
        Lattice lattice = new LinearLattice();
        Shape shape = new Line(lattice, 10);
        Boundary boundary = new Arena(shape, lattice);
        Geometry ret = new Geometry(lattice, shape, boundary);
        return ret;
    }

    @Test
    public void testDefault() {
        Element e = root.element("default-case");
        CoordinateSet actual = CustomSetFactory.instantiate(e, geom);
        CoordinateSet expected = new CustomSet();
        assertEquals(expected, actual);
    }

    @Test
    public void testMixedCase() {
        Element e = root.element("mixed-case");
        CoordinateSet actual = CustomSetFactory.instantiate(e, geom);

        CoordinateSet expected = new CustomSet();
        expected.add(new Coordinate2D(0, 0, 0));
        expected.add(new Coordinate2D(0, 1, 0));
        expected.add(new Coordinate2D(0, 2, 0));
        assertEquals(expected, actual);
    }
}