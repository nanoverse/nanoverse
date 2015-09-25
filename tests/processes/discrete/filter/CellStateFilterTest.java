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

package processes.discrete.filter;

import cells.*;
import control.arguments.ConstantInteger;
import control.identifiers.Coordinate;
import layers.cell.CellUpdateManager;
import org.junit.*;
import test.LegacyLatticeTest;

import java.util.*;

import static org.junit.Assert.assertTrue;

public class CellStateFilterTest extends LegacyLatticeTest {
    private Cell yes, no;
    private CellStateFilter query;

    @Before
    public void setUp() throws Exception {
        super.setUp();

        yes = new MockCell(1);
        no = new MockCell(2);

        CellUpdateManager u = cellLayer.getUpdateManager();

        u.place(yes, x);
        u.place(no, y);

        query = new CellStateFilter(cellLayer, new ConstantInteger(1));
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