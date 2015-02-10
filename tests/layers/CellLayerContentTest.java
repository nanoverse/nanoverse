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

import cells.MockCell;
import control.identifiers.Coordinate;
import control.identifiers.Flags;
import geometry.MockGeometry;
import layers.cell.CellLayerContent;
import layers.cell.MockCellLayerIndices;
import test.EslimeTestCase;

public abstract class CellLayerContentTest extends EslimeTestCase {
    protected Coordinate[] c;
    protected MockGeometry geom;
    protected CellLayerContent query;
    protected MockCell f0, f1, f2;
    protected MockCellLayerIndices indices;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        c = new Coordinate[3];

        c[0] = new Coordinate(0, 0, 0);
        c[1] = new Coordinate(1, 0, 0);
        c[2] = new Coordinate(2, 0, 0);

        geom = new MockGeometry();
        geom.setCanonicalSites(c);

        indices = new MockCellLayerIndices();

        query = makeQuery();

        f0 = makeMockCell(1, 0.5);
        f1 = makeMockCell(1, 0.5);
        f2 = makeMockCell(2, 0.7);
    }

    private MockCell makeMockCell(int state, double health) {
        MockCell cell = new MockCell();
        cell.setState(state);
        cell.setHealth(health);

        return cell;
    }


    // Actually, create a full mock for dependencies and test out
    // that all appropriate requests are fired--nothing more
    public void testPutHasGetRemove() throws Exception {

        // Test before and after state
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

    public void testGetCanonicalSites() {
        assertEquals(query.getCanonicalSites()[0], c[0]);
        assertEquals(query.getCanonicalSites()[1], c[1]);
        assertEquals(query.getCanonicalSites()[2], c[2]);
    }

    public void testGetStateVector() throws Exception {
        query.put(c[0], f0);
        query.put(c[1], f1);
        query.put(c[2], f2);

        // Health vector goes in order of canonical sites array
        assertEquals(query.getStateVector()[0], 1);
        assertEquals(query.getStateVector()[1], 1);
        assertEquals(query.getStateVector()[2], 2);
    }

    public void testGetHealthVector() throws Exception {
        query.put(c[0], f0);
        query.put(c[1], f1);
        query.put(c[2], f2);

        // Health vector goes in order of canonical sites array
        assertEquals(query.getHealthVector()[0], 0.5, epsilon);
        assertEquals(query.getHealthVector()[1], 0.5, epsilon);
        assertEquals(query.getHealthVector()[2], 0.7, epsilon);
    }

    public void testHasCanonicalForm() {
        // Try something that does...
        Coordinate yes = new Coordinate(0, 0, Flags.BEYOND_BOUNDS);
        assertTrue(query.hasCanonicalForm(yes));

        // Try something that doesn't
        Coordinate no = new Coordinate(-1, 0, Flags.BEYOND_BOUNDS);
        assertFalse(query.hasCanonicalForm(no));
    }

    public abstract CellLayerContent makeQuery();

    public void testClone() {
        Object clone = query.clone();
        assertEquals(query, clone);
        assertFalse(query == clone);
    }
}
