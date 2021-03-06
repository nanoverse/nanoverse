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

package nanoverse.runtime.layers.cell;

import nanoverse.runtime.agent.Agent;
import nanoverse.runtime.cells.*;
import nanoverse.runtime.control.halt.BoundaryReachedEvent;
import nanoverse.runtime.control.identifiers.*;
import nanoverse.runtime.geometry.Geometry;
import nanoverse.runtime.geometry.boundaries.*;
import nanoverse.runtime.geometry.lattice.*;
import nanoverse.runtime.geometry.shape.*;
import org.junit.*;
import test.LegacyTest;

import static org.junit.Assert.assertTrue;

public class HaltAgentLayerContentTest extends LegacyTest {

    private HaltAgentLayerContent query;

    @Before
    public void setUp() throws Exception {
        Lattice lattice = new LinearLattice();
        Shape shape = new Line(lattice, 5);
        Boundary boundary = new Arena(shape, lattice);
        Geometry geometry = new Geometry(lattice, shape, boundary);
        AgentLayerIndices indices = new AgentLayerIndices();
        query = new HaltAgentLayerContent(geometry, indices);
    }

    @Test
    public void testPutInBounds() throws Exception {
        MockAgent cell = new MockAgent();
        Coordinate c = new Coordinate2D(0, 0, 0);
        query.put(c, cell);
        Agent actual = query.get(c);
        assertTrue(actual == cell);
    }

    @Test
    public void testPutOutOfBounds() throws Exception {
        MockAgent cell = new MockAgent();
        Coordinate c = new Coordinate2D(-1, 0, Flags.END_OF_WORLD);

        boolean thrown = false;
        try {
            query.put(c, cell);
        } catch (BoundaryReachedEvent ex) {
            thrown = true;
        }

        assertTrue(thrown);
    }
}