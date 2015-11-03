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

package nanoverse.runtime.layers;

import nanoverse.runtime.cells.MockAgent;
import nanoverse.runtime.control.identifiers.*;
import nanoverse.runtime.geometry.MockGeometry;
import nanoverse.runtime.layers.cell.*;
import org.junit.*;
import test.LegacyTest;

import static org.junit.Assert.*;

public abstract class AgentLayerContentTest extends LegacyTest {
    protected Coordinate[] c;
    protected MockGeometry geom;
    protected AgentLayerContent query;
    protected MockAgent f0, f1, f2;
    protected MockAgentLayerIndices indices;

    @Before
    public void setUp() throws Exception {
        c = new Coordinate[3];

        c[0] = new Coordinate2D(0, 0, 0);
        c[1] = new Coordinate2D(1, 0, 0);
        c[2] = new Coordinate2D(2, 0, 0);

        geom = new MockGeometry();
        geom.setCanonicalSites(c);

        indices = new MockAgentLayerIndices();

        query = makeQuery();

        f0 = makeMockAgent("1");
        f1 = makeMockAgent("1");
        f2 = makeMockAgent("2");
    }

    private MockAgent makeMockAgent(String name) {
        MockAgent cell = new MockAgent(name);
        return cell;
    }

    public abstract AgentLayerContent makeQuery();

    // Actually, create a full mock for dependencies and test out
    // that all appropriate requests are fired--nothing more
    @Test
    public void testPutHasGetRemove() throws Exception {

        // Test before and after name
        assertFalse(query.has(c[0]));
        query.put(c[0], f0);
        assertTrue(query.has(c[0]));
        assertEquals(f0, query.get(c[0]));

        // Check that indices were triggered correctly.
        assertEquals(c[0], indices.getLastCoord());
        assertNull(indices.getLastPrevious());
        assertEquals(f0, indices.getLastCurrent());

        query.remove(c[0]);

        assertFalse(query.has(c[0]));
        assertEquals(c[0], indices.getLastCoord());
        assertEquals(f0, indices.getLastPrevious());
        assertNull(indices.getLastCurrent());
    }

    @Test
    public void testGetCanonicalSites() {
        assertEquals(query.getCanonicalSites()[0], c[0]);
        assertEquals(query.getCanonicalSites()[1], c[1]);
        assertEquals(query.getCanonicalSites()[2], c[2]);
    }

    @Test
    public void testGetNameVector() throws Exception {
        query.put(c[0], f0);
        query.put(c[1], f1);
        query.put(c[2], f2);

        // Health vector goes in order of canonical sites array
        assertEquals(query.getNames()[0], "1");
        assertEquals(query.getNames()[1], "1");
        assertEquals(query.getNames()[2], "2");
    }

    @Test
    public void testHasCanonicalForm() {
        // Try something that does...
        Coordinate yes = new Coordinate2D(0, 0, Flags.BEYOND_BOUNDS);
        assertTrue(query.hasCanonicalForm(yes));

        // Try something that doesn't
        Coordinate no = new Coordinate2D(-1, 0, Flags.BEYOND_BOUNDS);
        assertFalse(query.hasCanonicalForm(no));
    }

    @Test
    public void testClone() {
        Object clone = query.clone();
        assertEquals(query, clone);
        assertFalse(query == clone);
    }
}
