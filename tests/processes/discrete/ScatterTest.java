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

package processes.discrete;

import control.GeneralParameters;
import control.arguments.*;
import control.identifiers.*;
import geometry.Geometry;
import geometry.boundaries.*;
import geometry.lattice.*;
import geometry.set.*;
import geometry.shape.*;
import layers.MockLayerManager;
import layers.cell.CellLayer;
import org.junit.*;
import processes.BaseProcessArguments;
import test.LegacyTest;

import static org.junit.Assert.assertEquals;

public class ScatterTest extends LegacyTest {
    private Geometry geom;
    private MockLayerManager lm;
    private GeneralParameters p;
    private BaseProcessArguments arguments;
    private CellDescriptor cd;

    @Before
    public void setUp() throws Exception {

        p = makeMockGeneralParameters();
        lm = new MockLayerManager();

        Lattice lattice = new LinearLattice();
        Shape shape = new Line(lattice, 10);
        Boundary boundary = new Arena(shape, lattice);
        geom = new Geometry(lattice, shape, boundary);

        CellLayer layer = new CellLayer(geom);
        lm.setCellLayer(layer);

        arguments = makeBaseProcessArguments(lm, p);

        cd = new MockCellDescriptor();
    }

    @Test
    public void testBaseBehavior() throws Exception {
        CellProcessArguments cpArguments = makeCellProcessArguments(geom);
        Scatter query = new Scatter(arguments, cpArguments, cd);
        query.init();
        query.iterate();

        assertEquals(10, lm.getCellLayer().getViewer().getOccupiedSites().size());
    }

    @Test
    public void testRespectActiveSites() throws Exception {
        CoordinateSet activeSites = new CustomSet();
        for (int y = 2; y < 5; y++) {
            activeSites.add(new Coordinate2D(0, y, 0));
        }

        CellProcessArguments cpArguments = new CellProcessArguments(activeSites, new ConstantInteger(-1));

        Scatter query = new Scatter(arguments, cpArguments, cd);
        query.init();
        query.iterate();

        for (Coordinate c : geom.getCanonicalSites()) {
            boolean actual = lm.getCellLayer().getViewer().isOccupied(c);
            boolean expected = activeSites.contains(c);
            assertEquals(expected, actual);
        }
    }

    @Test
    public void testRespectMaxTargets() throws Exception {
        CoordinateSet activeSites = new CompleteSet(geom);
        IntegerArgument maxTargets = new ConstantInteger(3);
        CellProcessArguments cpArguments = new CellProcessArguments(activeSites, maxTargets);
        Scatter query = new Scatter(arguments, cpArguments, cd);
        query.init();
        query.iterate();

        assertEquals(3, lm.getCellLayer().getViewer().getOccupiedSites().size());
    }
}