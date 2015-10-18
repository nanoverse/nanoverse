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

package nanoverse.runtime.processes.discrete.filter;

import nanoverse.runtime.agent.Agent;
import nanoverse.runtime.cells.*;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.layers.cell.AgentUpdateManager;
import org.junit.*;
import test.LegacyLatticeTest;

import java.util.*;

import static org.junit.Assert.assertTrue;

public class AgentNameFilterTest extends LegacyLatticeTest {
    private Agent yes, no;
    private AgentNameFilter query;

    @Before
    public void setUp() throws Exception {
        super.setUp();

        yes = new MockAgent("a");
        no = new MockAgent("b");

        AgentUpdateManager u = cellLayer.getUpdateManager();

        u.place(yes, x);
        u.place(no, y);

        query = new AgentNameFilter(cellLayer, "a");
    }

    @Test
    public void testLifeCycle() throws Exception {
        List<Coordinate> cc = Arrays.asList(geom.getCanonicalSites());
        List<Coordinate> ccCopy = new ArrayList<>(cc);

        // Apply filter.
        List<Coordinate> actual = query.apply(cc);

        // Only "x" should be retained.
        List<Coordinate> expected = Arrays.asList(x);
        assertTrue(collectionsEqual(expected, actual));

        // Original list should be unmodified
        assertTrue(collectionsEqual(cc, ccCopy));
    }

    private boolean collectionsEqual(Collection<Coordinate> p, Collection<Coordinate> q) {
        if (p.size() != q.size()) {
            return false;
        }

        Iterator<Coordinate> qIter = q.iterator();

        for (Coordinate pCoord : p) {
            Coordinate qCoord = qIter.next();

            if (pCoord != qCoord) {
                return false;
            }
        }

        return true;
    }
}