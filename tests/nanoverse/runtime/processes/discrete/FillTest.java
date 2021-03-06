/*
 * Nanoverse: a declarative agent-based modeling language for natural and
 * social science.
 *
 * Copyright (c) 2015 David Bruce Borenstein and Nanoverse, LLC.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package nanoverse.runtime.processes.discrete;

import nanoverse.runtime.cells.MockAgent;
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

import static org.junit.Assert.*;

public class FillTest extends LegacyTest {

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
        Fill query = new Fill(arguments, cpArguments, false, cd);
        query.init();
        query.iterate();

        assertEquals(10, lm.getAgentLayer().getViewer().getOccupiedSites().count());
    }

    @Test
    public void testRespectActiveSites() throws Exception {
        CoordinateSet activeSites = new CustomSet();
        for (int y = 2; y < 5; y++) {
            activeSites.add(new Coordinate2D(0, y, 0));
        }

        AgentProcessArguments cpArguments = new AgentProcessArguments(activeSites, new ConstantInteger(-1));

        Fill query = new Fill(arguments, cpArguments, false, cd);
        query.init();
        query.iterate();

        for (Coordinate c : geom.getCanonicalSites()) {
            boolean actual = lm.getAgentLayer().getViewer().isOccupied(c);
            boolean expected = activeSites.contains(c);
            assertEquals(expected, actual);
        }
    }

    @Test
    public void testSkipFilledYes() throws Exception {
        Coordinate c = new Coordinate2D(0, 2, 0);
        lm.getAgentLayer().getUpdateManager().place(new MockAgent("test"), c);
        doSkipFilledTest(true);

        // Original cell should not have been replaced, because it was skipped
        assertEquals("test", lm.getAgentLayer().getViewer().getName(c));
    }

    private void doSkipFilledTest(boolean skip) throws Exception {
        AgentProcessArguments cpArguments = makeAgentProcessArguments(geom);
        Fill query = new Fill(arguments, cpArguments, skip, cd);
        query.init();

        boolean thrown = false;
        try {
            query.iterate();
        } catch (Exception ex) {
            thrown = true;
        }

        assertTrue(thrown == !skip);
    }

    @Test
    public void testSkipFilledNo() throws Exception {
        Coordinate c = new Coordinate2D(0, 2, 0);
        lm.getAgentLayer().getUpdateManager().place(new MockAgent(), c);
        doSkipFilledTest(false);
    }
}