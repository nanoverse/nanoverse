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

package layers.cell;

import cells.MockCell;
import control.identifiers.*;
import org.junit.*;

import static org.junit.Assert.*;
/**
 * Created by David B Borenstein on 2/5/14.
 */
public class CellLocationIndexTest {
    private MockCell o1, o2;
    private Coordinate c1, c2;

    // The object to be tested
    private CellLocationIndex locationIndex;

    @Before
    public void setUp() throws Exception {
        o1 = new MockCell();
        o2 = new MockCell();

        c1 = new Coordinate2D(0, 0, 0);
        c2 = new Coordinate2D(0, 1, 0);

        locationIndex = new CellLocationIndex();
    }

    @Test
    public void testPlace() throws Exception {
        // Index should be empty
        assertEquals(0, locationIndex.keySet().size());

        // Place a cell
        locationIndex.add(o1, c1);

        // Cell should index to placed coordinate
        assertEquals(1, locationIndex.keySet().size());
        assertEquals(c1, locationIndex.get(o1));
    }

    @Test
    public void testRemove() throws Exception {
        // Manually add a cell to the map
        locationIndex.put(o1, c1);
        assertEquals(1, locationIndex.keySet().size());

        // Remove the cell using the index object
        locationIndex.remove(o1);

        // Map should be empty
        assertEquals(0, locationIndex.keySet().size());
    }

    @Test
    public void testLocate() throws Exception {
        locationIndex.add(o1, c1);
        locationIndex.add(o2, c2);
        assertEquals(c1, locationIndex.locate(o1));
        assertEquals(c2, locationIndex.locate(o2));
    }

    @Test
    public void testIsIndexed() throws Exception {
        locationIndex.add(o1, c1);
        assertTrue(locationIndex.isIndexed(o1));
        assertFalse(locationIndex.isIndexed(o2));
    }

}
