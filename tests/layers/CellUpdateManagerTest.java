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

import cells.*;
import control.identifiers.*;
import geometry.MockGeometry;
import layers.cell.*;
import org.junit.*;
import test.LegacyTest;

import static org.junit.Assert.*;

public class CellUpdateManagerTest extends LegacyTest {
    private MockGeometry geom;
    private MockCellLayerIndices indices;
    private MockCellLayerContent content;
    private Coordinate o, t;
    private CellUpdateManager query;

    @Before
    public void setUp() throws Exception {
        geom = new MockGeometry();
        o = new Coordinate2D(0, 0, 0);
        t = new Coordinate2D(1, 0, 0);
        Coordinate[] cc = new Coordinate[]{o, t};
        geom.setCanonicalSites(cc);

        indices = new MockCellLayerIndices();
        content = new MockCellLayerContent(geom, indices);
        query = new CellUpdateManager(content);
    }

    @Test
    public void testConsiderApply() throws Exception {
        MockCell cell = new MockCell();
        query.place(cell, o);
        assertEquals(1, query.consider(o));
        assertEquals(2, query.consider(o));
        query.apply(o);
        assertEquals(1, query.consider(o));

    }

    @Test
    public void testDivideTo() throws Exception {
        MockCell cell = new MockCell(1);
        query.place(cell, o);
        MockCell child = new MockCell(2);
        cell.setChild(child);
        assertNull(indices.getLastPrevious());
        assertEquals(cell, indices.getLastCurrent());
        assertEquals(o, indices.getLastCoord());
        assertTrue(content.has(o));

        query.divideTo(o, t);
        assertNull(indices.getLastPrevious());
        assertEquals(t, indices.getLastCoord());
        assertTrue(content.has(t));

        assertEquals(t, content.locate(child));
    }

    @Test
    public void testDivide() throws Exception {
        MockCell cell = new MockCell(1);
        MockCell child = new MockCell(2);
        cell.setChild(child);
        query.place(cell, o);

        Cell actual = query.divide(o);
        Cell expected = child;
        assertEquals(expected, actual);
    }


    @Test
    public void testBanish() throws Exception {
        Cell cell = new MockCell();
        content.put(o, cell);

        assertTrue(content.has(o));
        query.banish(o);
        assertFalse(content.has(o));
    }

    @Test
    public void testMove() throws Exception {
        Cell cell = new MockCell(1);
        query.place(cell, o);
        assertNull(indices.getLastPrevious());
        assertEquals(cell, indices.getLastCurrent());
        assertEquals(o, indices.getLastCoord());
        assertTrue(content.has(o));

        query.move(o, t);

        assertFalse(content.has(o));
        assertEquals(cell, indices.getLastCurrent());
        assertEquals(t, indices.getLastCoord());
        assertTrue(content.has(t));
    }

    @Test
    public void testSwap() throws Exception {
        Cell cell1 = new MockCell(1);
        Cell cell2 = new MockCell(2);
        query.place(cell1, o);
        query.place(cell2, t);

        assertEquals(o, content.locate(cell1));
        assertEquals(t, content.locate(cell2));

        assertEquals(content.get(o).getState(), 1);
        assertEquals(content.get(t).getState(), 2);

        query.swap(o, t);

        assertEquals(o, content.locate(cell2));
        assertEquals(t, content.locate(cell1));

        assertEquals(content.get(o).getState(), 2);
        assertEquals(content.get(t).getState(), 1);

    }

    @Test
    public void testPlace() throws Exception {
        Cell cell = new MockCell(1);

        assertFalse(content.has(o));
        query.place(cell, o);
        assertTrue(content.has(o));
    }

}
