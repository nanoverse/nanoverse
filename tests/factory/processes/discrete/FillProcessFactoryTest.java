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

package factory.processes.discrete;

import control.GeneralParameters;
import control.arguments.Argument;
import control.arguments.CellDescriptor;
import control.arguments.ConstantDouble;
import control.arguments.ConstantInteger;
import control.identifiers.Coordinate;
import factory.control.arguments.CellDescriptorFactory;
import geometry.Geometry;
import geometry.boundaries.Absorbing;
import geometry.boundaries.Boundary;
import geometry.lattice.Lattice;
import geometry.lattice.LinearLattice;
import geometry.set.CoordinateSet;
import geometry.set.DiscSet;
import geometry.shape.Line;
import geometry.shape.Shape;
import layers.LayerManager;
import layers.MockLayerManager;
import layers.cell.CellLayer;
import org.dom4j.Element;
import processes.BaseProcessArguments;
import processes.discrete.CellProcessArguments;
import processes.discrete.Fill;
import test.EslimeTestCase;

public class FillProcessFactoryTest extends EslimeTestCase {

    private GeneralParameters p;
    private Element root;
    private LayerManager layerManager;
    private Geometry geom;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        p = makeMockGeneralParameters();
        root = readXmlFile("factories/processes/discrete/FillProcessFactoryTest.xml");

        Lattice lattice = new LinearLattice();
        Shape shape = new Line(lattice, 10);
        Boundary boundary = new Absorbing(shape, lattice);
        geom = new Geometry(lattice, shape, boundary);

        CellLayer layer = new CellLayer(geom);
        layerManager = new MockLayerManager();
        layerManager.setCellLayer(layer);
    }

    public void testImplicit() throws Exception {
        Element testElem = root.element("implicit-case");

        BaseProcessArguments arguments = makeBaseProcessArguments(layerManager, p);
        CellProcessArguments cpArguments = makeCellProcessArguments(geom);
        CellDescriptor descriptor = CellDescriptorFactory.makeDefault(layerManager, p);

        Fill expected = new Fill(arguments, cpArguments, false, descriptor);
        Fill actual = FillProcessFactory.instantiate(testElem, layerManager, p, 0);

        assertEquals(expected, actual);
    }

    public void testExplicit() throws Exception {
        Element testElem = root.element("explicit-case");

        BaseProcessArguments arguments = makeBaseProcessArguments(layerManager, p);
        CoordinateSet activeSites = new DiscSet(geom, new ConstantInteger(2), new Coordinate(0, 0, 0));
        Argument<Integer> maxTargets = new ConstantInteger(-1);
        CellProcessArguments cpArguments = new CellProcessArguments(activeSites, maxTargets);
        CellDescriptor cd = makeCellDescriptor();

        Fill expected = new Fill(arguments, cpArguments, true, cd);
        Fill actual = FillProcessFactory.instantiate(testElem, layerManager, p, 0);

        assertEquals(expected, actual);
    }

    private CellDescriptor makeCellDescriptor() {

        CellDescriptor descriptor = new CellDescriptor(layerManager);
        descriptor.setCellState(new ConstantInteger(5));
        descriptor.setThreshold(new ConstantDouble(2.0));
        descriptor.setInitialHealth(new ConstantDouble(1.0));
        return descriptor;
    }
}
