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

package nanoverse.runtime.processes.discrete;

import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.control.arguments.*;
import nanoverse.runtime.control.identifiers.*;
import nanoverse.runtime.geometry.Geometry;
import nanoverse.runtime.geometry.boundaries.*;
import nanoverse.runtime.geometry.lattice.*;
import nanoverse.runtime.geometry.set.*;
import nanoverse.runtime.geometry.shape.*;
import nanoverse.runtime.layers.MockLayerManager;
import nanoverse.runtime.layers.cell.AgentLayer;
import nanoverse.runtime.processes.BaseProcessArguments;
import org.junit.*;
import test.LegacyTest;

import static org.junit.Assert.assertEquals;

public class ScatterTest extends LegacyTest {
    private Geometry geom;
    private MockLayerManager lm;
    private GeneralParameters p;
    private BaseProcessArguments arguments;
    private AgentDescriptor cd;

    @Before
    public void setUp() throws Exception {

        p = makeMockGeneralParameters();
        lm = new MockLayerManager();

        Lattice lattice = new LinearLattice();
        Shape shape = new Line(lattice, 10);
        Boundary boundary = new Arena(shape, lattice);
        geom = new Geometry(lattice, shape, boundary);

        AgentLayer layer = new AgentLayer(geom);
        lm.setAgentLayer(layer);

        arguments = makeBaseProcessArguments(lm, p);

        cd = new MockAgentDescriptor();
    }

    @Test
    public void testBaseBehavior() throws Exception {
        AgentProcessArguments cpArguments = makeAgentProcessArguments(geom);
        Scatter query = new Scatter(arguments, cpArguments, cd);
        query.init();
        query.iterate();

        assertEquals(10, lm.getAgentLayer().getViewer().getOccupiedSites().size());
    }

    @Test
    public void testRespectActiveSites() throws Exception {
        CoordinateSet activeSites = new CustomSet();
        for (int y = 2; y < 5; y++) {
            activeSites.add(new Coordinate2D(0, y, 0));
        }

        AgentProcessArguments cpArguments = new AgentProcessArguments(activeSites, new ConstantInteger(-1));

        Scatter query = new Scatter(arguments, cpArguments, cd);
        query.init();
        query.iterate();

        for (Coordinate c : geom.getCanonicalSites()) {
            boolean actual = lm.getAgentLayer().getViewer().isOccupied(c);
            boolean expected = activeSites.contains(c);
            assertEquals(expected, actual);
        }
    }

    @Test
    public void testRespectMaxTargets() throws Exception {
        CoordinateSet activeSites = new CompleteSet(geom);
        IntegerArgument maxTargets = new ConstantInteger(3);
        AgentProcessArguments cpArguments = new AgentProcessArguments(activeSites, maxTargets);
        Scatter query = new Scatter(arguments, cpArguments, cd);
        query.init();
        query.iterate();

        assertEquals(3, lm.getAgentLayer().getViewer().getOccupiedSites().size());
    }
}