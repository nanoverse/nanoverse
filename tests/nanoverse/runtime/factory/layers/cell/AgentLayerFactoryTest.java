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

package nanoverse.runtime.factory.layers.cell;

import nanoverse.runtime.control.arguments.GeometryDescriptor;
import nanoverse.runtime.factory.control.arguments.GeometryDescriptorFactory;
import nanoverse.runtime.geometry.Geometry;
import nanoverse.runtime.geometry.boundaries.*;
import nanoverse.runtime.geometry.lattice.*;
import nanoverse.runtime.geometry.shape.*;
import nanoverse.runtime.layers.cell.CellLayer;
import org.dom4j.Element;
import org.junit.*;
import test.LegacyTest;

import static org.junit.Assert.assertEquals;

public class AgentLayerFactoryTest extends LegacyTest {

    private Element fixtureRoot;
    private GeometryDescriptor descriptor;

    @Before
    public void setUp() throws Exception {
        fixtureRoot = readXmlFile("factories/layers/cell/CellLayerFactoryTest.xml");
        Element geometryRoot = fixtureRoot.element("geometry");
        descriptor = GeometryDescriptorFactory.instantiate(geometryRoot);
    }

    @Test
    public void testInstantiate() throws Exception {
        Element e = fixtureRoot.element("general-case");
        CellLayer actual = CellLayerFactory.instantiate(e, descriptor);
        Geometry geom = makeGeometry();
        CellLayer expected = new CellLayer(geom);
        assertEquals(expected, actual);
    }

    private Geometry makeGeometry() {
        Lattice lattice = new LinearLattice();
        Shape shape = new Line(lattice, 10);
        Boundary boundary = new Arena(shape, lattice);
        Geometry geometry = new Geometry(lattice, shape, boundary);
        return geometry;
    }
}