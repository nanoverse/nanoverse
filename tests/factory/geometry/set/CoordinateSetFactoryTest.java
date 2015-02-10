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

package factory.geometry.set;//import junit.framework.TestCase;

import control.arguments.Argument;
import control.arguments.ConstantInteger;
import control.identifiers.Coordinate;
import geometry.Geometry;
import geometry.boundaries.Arena;
import geometry.boundaries.Boundary;
import geometry.lattice.Lattice;
import geometry.lattice.LinearLattice;
import geometry.set.CompleteSet;
import geometry.set.CoordinateSet;
import geometry.set.CustomSet;
import geometry.set.DiscSet;
import geometry.shape.Line;
import geometry.shape.Shape;
import org.dom4j.Element;
import structural.MockGeneralParameters;
import test.EslimeTestCase;

public class CoordinateSetFactoryTest extends EslimeTestCase {

    private Element root;
    private Geometry g;
    private MockGeneralParameters p;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        root = readXmlFile("factories/geometry/set/CoordinateSetFactoryTest.xml");
        p = makeMockGeneralParameters();
        g = makeGeometry();
    }

    public void testDefault() throws Exception {
        // Element does not actually exist, so e is null
        Element e = root.element("default-case");
        CoordinateSet expected = new CompleteSet(g);
        CoordinateSet actual = CoordinateSetFactory.instantiate(e, g, p);
        assertEquals(expected, actual);
    }

    public void testAll() throws Exception {
        Element e = root.element("all-case");
        CoordinateSet expected = new CompleteSet(g);
        CoordinateSet actual = CoordinateSetFactory.instantiate(e, g, p);
        assertEquals(expected, actual);
    }

    public void testDisc() throws Exception {
        Element e = root.element("disc-case");
        Coordinate offset = g.getZeroVector();
        Argument<Integer> radius = new ConstantInteger(1);
        CoordinateSet expected = new DiscSet(g, radius, offset);
        CoordinateSet actual = CoordinateSetFactory.instantiate(e, g, p);
        assertEquals(expected, actual);
    }

    public void testList() throws Exception {
        Element e = root.element("list-case");
        CoordinateSet expected = new CustomSet();
        CoordinateSet actual = CoordinateSetFactory.instantiate(e, g, p);
        assertEquals(expected, actual);
    }

    private Geometry makeGeometry() {
        Lattice lattice = new LinearLattice();
        Shape shape = new Line(lattice, 10);
        Boundary boundary = new Arena(shape, lattice);
        Geometry ret = new Geometry(lattice, shape, boundary);
        return ret;
    }

}