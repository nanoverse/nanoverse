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

package nanoverse.runtime.structural;

import nanoverse.runtime.cells.MockCell;
import nanoverse.runtime.control.identifiers.*;
import org.junit.*;
import test.LegacyTest;

import static org.junit.Assert.*;
/**
 * Created by David B Borenstein on 4/11/14.
 */
public class CanonicalCellMapTest extends LegacyTest {
    private CanonicalCellMap query;
    private Coordinate c, nc, d;
    private MockCell cell;

    @Before
    public void setUp() throws Exception {
        query = new CanonicalCellMap();
        c = new Coordinate2D(0, 0, 0);
        nc = new Coordinate2D(0, 0, Flags.BOUNDARY_APPLIED);
        d = new Coordinate3D(0, 0, 0, 0);

        cell = new MockCell(1);
    }

    @Test
    public void testPutGet() throws Exception {
        query.put(c, cell);
        assertEquals(cell, query.get(c));
        assertEquals(cell, query.get(nc));
        assertEquals(null, query.get(d));
    }

    @Test
    public void testContainsKey() throws Exception {
        query.put(c, cell);
        assertTrue(query.containsKey(c));
        assertTrue(query.containsKey(nc));
        assertFalse(query.containsKey(d));
    }
}
