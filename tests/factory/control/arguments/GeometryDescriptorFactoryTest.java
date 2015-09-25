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

package factory.control.arguments;//import junit.framework.TestCase;

import control.arguments.GeometryDescriptor;
import geometry.lattice.*;
import geometry.shape.*;
import org.dom4j.Element;
import org.junit.*;
import test.LegacyTest;

import static org.junit.Assert.assertEquals;

public class GeometryDescriptorFactoryTest extends LegacyTest {

    private Element root;

    @Before
    public void setUp() throws Exception {
        root = readXmlFile("factories/control/arguments/GeometryDescriptorFactoryTest.xml");
    }

    @Test
    public void testExplicitCase() throws Exception {
        Element elem = root.element("explicit-case");
        Lattice lattice = new LinearLattice();
        Shape shape = new Line(lattice, 10);
        GeometryDescriptor expected = new GeometryDescriptor(lattice, shape);
        GeometryDescriptor actual = GeometryDescriptorFactory.instantiate(elem);
        assertEquals(expected, actual);
    }

    // No implicit case because that behavior has not yet been implemented.
    // This one will need to be slightly complicated, since lattice and shape
    // arguments should be able to narrow down the default behavior for the
    // other one. This is a good example of a smart default -- an important
    // concept for Eco 1.0.
}