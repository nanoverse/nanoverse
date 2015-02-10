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

package factory.layers;

import control.arguments.GeometryDescriptor;
import factory.control.arguments.GeometryDescriptorFactory;
import geometry.Geometry;
import geometry.boundaries.Arena;
import geometry.boundaries.Boundary;
import geometry.lattice.Lattice;
import geometry.lattice.LinearLattice;
import geometry.shape.Line;
import geometry.shape.Shape;
import layers.LayerManager;
import layers.cell.CellLayer;
import org.dom4j.Element;
import test.EslimeTestCase;

public class LayerManagerFactoryTest extends EslimeTestCase {
    private Element root;
    private GeometryDescriptor geometryDescriptor;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        root = readXmlFile("factories/layers/LayerManagerFactoryTest.xml");
        Element geometryRoot = root.element("geometry");
        geometryDescriptor = GeometryDescriptorFactory.instantiate(geometryRoot);
    }

    public void testInstantiate() throws Exception {
        Element e = root.element("general-case");
        LayerManager actual = LayerManagerFactory.instantiate(e, geometryDescriptor);
        LayerManager expected = makeExpected();
        assertEquals(expected, actual);
    }

    private LayerManager makeExpected() {
        CellLayer cellLayer = new CellLayer(arenaGeom());
//        MockSoluteLayer soluteLayer1 = new MockSoluteLayer();
//        MockSoluteLayer soluteLayer2 = new MockSoluteLayer();

        LayerManager layerManager = new LayerManager();
//        layerManager.addSoluteLayer("test1", soluteLayer1);
//        layerManager.addSoluteLayer("test2", soluteLayer2);
        layerManager.setCellLayer(cellLayer);
        return layerManager;
    }

    private Geometry arenaGeom() {
        Lattice lattice = new LinearLattice();
        Shape shape = new Line(lattice, 10);
        Boundary boundary = new Arena(shape, lattice);
        Geometry geometry = new Geometry(lattice, shape, boundary);
        return geometry;
    }

}