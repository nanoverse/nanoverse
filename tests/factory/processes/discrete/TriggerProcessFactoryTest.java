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
import control.arguments.*;
import control.identifiers.Coordinate;
import control.identifiers.Coordinate2D;
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
import processes.discrete.TriggerProcess;
import processes.discrete.filter.DepthFilter;
import processes.discrete.filter.Filter;
import processes.discrete.filter.NullFilter;
import test.EslimeTestCase;

public class TriggerProcessFactoryTest extends EslimeTestCase {
    private GeneralParameters p;
    private Element root;
    private LayerManager layerManager;
    private Geometry geom;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
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

    public void testImplicit() throws Exception {
        Element testElem = root.element("implicit-case");

        BaseProcessArguments arguments = makeBaseProcessArguments(layerManager, p);
        CellProcessArguments cpArguments = makeCellProcessArguments(geom);

        TriggerProcess expected = new TriggerProcess(arguments, cpArguments, "default", new NullFilter(), false, false);
        TriggerProcess actual = TriggerProcessFactory.instantiate(testElem, layerManager, p, 0);

        assertEquals(expected, actual);
    }

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