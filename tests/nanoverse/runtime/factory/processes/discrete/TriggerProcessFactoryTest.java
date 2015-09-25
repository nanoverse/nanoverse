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
import nanoverse.runtime.geometry.Geometry;
import nanoverse.runtime.geometry.boundaries.*;
import nanoverse.runtime.geometry.lattice.*;
import nanoverse.runtime.geometry.set.*;
import nanoverse.runtime.geometry.shape.*;
import nanoverse.runtime.layers.*;
import nanoverse.runtime.layers.cell.CellLayer;
import nanoverse.runtime.processes.BaseProcessArguments;
import nanoverse.runtime.processes.discrete.*;
import nanoverse.runtime.processes.discrete.filter.*;
import org.dom4j.Element;
import org.junit.*;
import test.LegacyTest;

import static org.junit.Assert.assertEquals;

public class TriggerProcessFactoryTest extends LegacyTest {
    private GeneralParameters p;
    private Element root;
    private LayerManager layerManager;
    private Geometry geom;

    @Before
    public void setUp() throws Exception {
        p = makeMockGeneralParameters();
        root = readXmlFile("factories/processes/discrete/TriggerProcessFactoryTest.xml");

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

        TriggerProcess expected = new TriggerProcess(arguments, cpArguments, "default", new NullFilter(), false, false);
        TriggerProcess actual = TriggerProcessFactory.instantiate(testElem, layerManager, p, 0);

        assertEquals(expected, actual);
    }

    @Test
    public void testExplicit() throws Exception {
        Element testElem = root.element("explicit-case");

        IntegerArgument maxTargets = new ConstantInteger(2);
        CoordinateSet activeSites = new DiscSet(geom, new ConstantInteger(2), new Coordinate2D(0, 0, 0));
        CellProcessArguments cpArguments = new CellProcessArguments(activeSites, maxTargets);

        BaseProcessArguments arguments = makeBaseProcessArguments(layerManager, p);

        Filter filter = new DepthFilter(layerManager.getCellLayer(), new ConstantInteger(1));

        TriggerProcess expected = new TriggerProcess(arguments, cpArguments, "test", filter, true, true);
        TriggerProcess actual = TriggerProcessFactory.instantiate(testElem, layerManager, p, 0);

        assertEquals(expected, actual);
    }
}