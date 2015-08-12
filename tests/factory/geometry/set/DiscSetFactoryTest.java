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

import control.arguments.*;
import control.identifiers.Coordinate;
import control.identifiers.Coordinate2D;
import geometry.Geometry;
import geometry.boundaries.Arena;
import geometry.boundaries.Boundary;
import geometry.lattice.Lattice;
import geometry.lattice.LinearLattice;
import geometry.set.CoordinateSet;
import geometry.set.DiscSet;
import geometry.shape.Line;
import geometry.shape.Shape;
import org.dom4j.Element;
import structural.MockGeneralParameters;
import test.EslimeTestCase;

public class DiscSetFactoryTest extends EslimeTestCase {

    private Geometry geom;
    private Element root;
    private MockGeneralParameters p;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        root = readXmlFile("factories/geometry/set/DiscSetFactoryTest.xml");
        p = makeMockGeneralParameters();
        geom = makeGeometry();
    }

    public void testDefault() {
        Element e = root.element("default-case");

        IntegerArgument radiusArg = new ConstantInteger(1);
        Coordinate offset = geom.getZeroVector();
        CoordinateSet expected = new DiscSet(geom, radiusArg, offset);

        CoordinateSet actual = DiscSetFactory.instantiate(e, geom, p);

        assertEquals(expected, actual);
    }

    public void testExplicit() {
        Element e = root.element("explicit-case");

        IntegerArgument radiusArg = new ConstantInteger(2);
        Coordinate offset = new Coordinate2D(0, -1, 0);
        CoordinateSet expected = new DiscSet(geom, radiusArg, offset);
        CoordinateSet actual = DiscSetFactory.instantiate(e, geom, p);

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