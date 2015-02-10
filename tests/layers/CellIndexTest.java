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

package layers;

import control.identifiers.Coordinate;
import control.identifiers.Flags;
import layers.cell.CellIndex;
import test.EslimeTestCase;

public class CellIndexTest extends EslimeTestCase {

    private Coordinate p, q;
    private CellIndex index;

    protected void setUp() throws Exception {
        p = new Coordinate(0, 0, 0);
        q = new Coordinate(0, 0, Flags.BOUNDARY_APPLIED);

        index = new CellIndex();
    }

    public void testAdd() {
        assertTrue(index.add(p));
        assertFalse(index.add(q));

        index = new CellIndex();

        assertTrue(index.add(q));
        assertFalse(index.add(p));
    }

    public void testClear() {
        assertTrue(index.add(p));
        assertFalse(index.add(p));

        index.clear();

        assertTrue(index.add(p));
    }

    public void testContains() {
        assertFalse(index.contains(p));
        assertFalse(index.contains(q));

        index.add(p);

        assertTrue(index.contains(p));
        assertTrue(index.contains(q));
    }

    public void testRemove() {
        assertFalse(index.remove(q));
        index.add(p);
        assertTrue(index.remove(q));

        assertFalse(index.remove(p));
        index.add(q);
        assertTrue(index.remove(p));

    }
}
