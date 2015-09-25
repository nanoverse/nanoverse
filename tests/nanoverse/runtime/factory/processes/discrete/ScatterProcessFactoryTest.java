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

package nanoverse.runtime.factory.processes.discrete;

import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.control.arguments.*;
import nanoverse.runtime.control.identifiers.Coordinate2D;
import nanoverse.runtime.factory.control.arguments.CellDescriptorFactory;
import nanoverse.runtime.geometry.Geometry;
import nanoverse.runtime.geometry.boundaries.*;
import nanoverse.runtime.geometry.lattice.*;
import nanoverse.runtime.geometry.set.*;
import nanoverse.runtime.geometry.shape.*;
import nanoverse.runtime.layers.*;
import nanoverse.runtime.layers.cell.CellLayer;
import nanoverse.runtime.processes.BaseProcessArguments;
import nanoverse.runtime.processes.discrete.*;
import org.dom4j.Element;
import org.junit.*;
import test.LegacyTest;

import static org.junit.Assert.assertEquals;

public class ScatterProcessFactoryTest extends LegacyTest {

    private GeneralParameters p;
    private Element root;
    private LayerManager layerManager;
    private Geometry geom;

    @Before
    public void setUp() throws Exception {
        p = makeMockGeneralParameters();
        root = readXmlFile("factories/processes/discrete/ScatterProcessFactoryTest.xml");

        Lattice lattice = new LinearLattice();
        Shape shape = new Line(lattice, 10);
        Boundary boundary = new Absorbing(shape, lattice);
        geom = new Geometry(lattice, shape, boundary);

        CellLayer layer = new CellLayer(geom);
        layerManager = new MockLayerManager();
        layerManager.setCellLayer(layer);
    }

    @Test
    public void testImplicit() throws Exception {
        Element testElem = root.element("implicit-case");

        BaseProcessArguments arguments = makeBaseProcessArguments(layerManager, p);
        CellProcessArguments cpArguments = makeCellProcessArguments(geom);
        CellDescriptor descriptor = CellDescriptorFactory.makeDefault(layerManager, p);

        Scatter expected = new Scatter(arguments, cpArguments, descriptor);
        Scatter actual = ScatterProcessFactory.instantiate(testElem, layerManager, p, 0);

        assertEquals(expected, actual);
    }

    @Test
    public void testExplicit() throws Exception {
        Element testElem = root.element("explicit-case");

        BaseProcessArguments arguments = makeBaseProcessArguments(layerManager, p);
        CoordinateSet activeSites = new DiscSet(geom, new ConstantInteger(2), new Coordinate2D(0, 0, 0));
        IntegerArgument maxTargets = new ConstantInteger(5);
        CellProcessArguments cpArguments = new CellProcessArguments(activeSites, maxTargets);
        CellDescriptor cd = makeCellDescriptor();

        Scatter expected = new Scatter(arguments, cpArguments, cd);
        Scatter actual = ScatterProcessFactory.instantiate(testElem, layerManager, p, 0);

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
