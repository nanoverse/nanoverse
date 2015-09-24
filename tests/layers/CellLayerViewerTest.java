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
import test.EslimeTestCase;

import static org.junit.Assert.*;
public class CellLayerViewerTest extends EslimeTestCase {

    CellLayerViewer query;
    MockCellLayerContent content;
    MockCellLayerIndices indices;
    MockGeometry geom;
    Coordinate c1, c2;

    @Before
    public void setUp() throws Exception {
        indices = new MockCellLayerIndices();

        c1 = new Coordinate2D(1, 0, 0);
        c2 = new Coordinate2D(5, 0, 0);

        geom = new MockGeometry();
        Coordinate[] cc = new Coordinate[]{c1, c2};
        geom.setCanonicalSites(cc);
        content = new MockCellLayerContent(geom, indices);

        query = new CellLayerViewer(content);
    }

    @Test
    public void testGetOccupiedSites() {
        CellIndex expected = new CellIndex();

        expected.add(c1);
        expected.add(c2);

        indices.setOccupiedSites(expected);

        assertEquals(query.getOccupiedSites().size(), expected.size());
        assertTrue(query.getOccupiedSites().contains(c1));
        assertTrue(query.getOccupiedSites().contains(c2));
    }

    @Test
    public void testGetDivisibleSites() {
        CellIndex expected = new CellIndex();

        expected.add(c1);
        expected.add(c2);

        indices.setDivisibleSites(expected);

        assertEquals(query.getDivisibleSites().size(), expected.size());
        assertTrue(query.getDivisibleSites().contains(c1));
        assertTrue(query.getDivisibleSites().contains(c2));
    }


    @Test
    public void testGetCell() throws Exception {

        Cell cell = new MockCell();
        content.put(c1, cell);
        assertEquals(cell, query.getCell(c1));
    }

    @Test
    public void testGetHealthVector() {
        double[] healthes = new double[]{0.1, 0.2};
        content.setHealthVector(healthes);
        assertArraysEqual(healthes, query.getHealthVector(), false);
    }

    @Test
    public void testGetStateVector() {
        int[] states = new int[]{1, 2};
        content.setStateVector(states);
        assertArraysEqual(states, query.getStateVector(), false);
    }

    @Test
    public void testIsOccupied() {
        CellIndex occupiedSites = new CellIndex();
        Coordinate c1 = new Coordinate2D(1, 0, 0);
        Coordinate c2 = new Coordinate2D(5, 0, 0);

        occupiedSites.add(c1);

        indices.setOccupiedSites(occupiedSites);
        assertTrue(query.isOccupied(c1));
        assertFalse(query.isOccupied(c2));
    }

    @Test
    public void testIsDivisible() {
        CellIndex divisibleSites = new CellIndex();
        Coordinate c1 = new Coordinate2D(1, 0, 0);
        Coordinate c2 = new Coordinate2D(5, 0, 0);

        divisibleSites.add(c1);

        indices.setDivisibleSites(divisibleSites);
        assertTrue(query.isDivisible(c1));
        assertFalse(query.isDivisible(c2));
    }

    @Test
    public void getStateVacant() {
        fail("Not yet implemented");
    }

    @Test
    public void getStateNonVacant() {
        fail("Not yet implemented");
    }
}
