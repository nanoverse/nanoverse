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

package nanoverse.runtime.factory.geometry.set;//import junit.framework.TestCase;

import nanoverse.runtime.control.arguments.*;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.geometry.Geometry;
import nanoverse.runtime.geometry.boundaries.*;
import nanoverse.runtime.geometry.lattice.*;
import nanoverse.runtime.geometry.set.*;
import nanoverse.runtime.geometry.shape.*;
import org.dom4j.Element;
import org.junit.*;
import nanoverse.runtime.structural.MockGeneralParameters;
import test.LegacyTest;

import static org.junit.Assert.assertEquals;

public class CoordinateSetFactoryTest extends LegacyTest {

    private Element root;
    private Geometry g;
    private MockGeneralParameters p;

    @Before
    public void setUp() throws Exception {
        root = readXmlFile("factories/geometry/set/CoordinateSetFactoryTest.xml");
        p = makeMockGeneralParameters();
        g = makeGeometry();
    }

    private Geometry makeGeometry() {
        Lattice lattice = new LinearLattice();
        Shape shape = new Line(lattice, 10);
        Boundary boundary = new Arena(shape, lattice);
        Geometry ret = new Geometry(lattice, shape, boundary);
        return ret;
    }

    @Test
    public void testDefault() throws Exception {
        // Element does not actually exist, so e is null
        Element e = root.element("default-case");
        CoordinateSet expected = new CompleteSet(g);
        CoordinateSet actual = CoordinateSetFactory.instantiate(e, g, p);
        assertEquals(expected, actual);
    }

    @Test
    public void testAll() throws Exception {
        Element e = root.element("all-case");
        CoordinateSet expected = new CompleteSet(g);
        CoordinateSet actual = CoordinateSetFactory.instantiate(e, g, p);
        assertEquals(expected, actual);
    }

    @Test
    public void testDisc() throws Exception {
        Element e = root.element("disc-case");
        Coordinate offset = g.getZeroVector();
        IntegerArgument radius = new ConstantInteger(1);
        CoordinateSet expected = new DiscSet(g, radius, offset);
        CoordinateSet actual = CoordinateSetFactory.instantiate(e, g, p);
        assertEquals(expected, actual);
    }

    @Test
    public void testList() throws Exception {
        Element e = root.element("list-case");
        CoordinateSet expected = new CustomSet();
        CoordinateSet actual = CoordinateSetFactory.instantiate(e, g, p);
        assertEquals(expected, actual);
    }

}