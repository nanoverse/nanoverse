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

import cells.MockCell;
import control.arguments.ConstantInteger;
import control.identifiers.*;
import geometry.Geometry;
import layers.MockLayerManager;
import layers.cell.CellLayer;
import org.junit.*;
import test.LegacyTest;

import java.util.*;

import static org.junit.Assert.*;

public class DepthFilterTest extends LegacyTest {


    private Geometry geom;
    private MockLayerManager layerManager;
    private CellLayer layer;
    private List<Coordinate> initial;

    @Before
    public void setUp() throws Exception {
        geom = makeLinearGeometry(10);
        layer = new CellLayer(geom);
        layerManager = new MockLayerManager();
        layerManager.setCellLayer(layer);
        placeCells();
    }

    // Position  0 1 2 3 4 5 6 7 8 9
    // Cell          2 3 4 5 6
    // Depth     0 0 0 1 2 1 0 0 0 0
    private void placeCells() throws Exception {
        initial = new ArrayList<>();
        for (int y = 2; y < 7; y++) {
            Coordinate c = new Coordinate2D(0, y, 0);
            MockCell cell = new MockCell(y);
            layerManager.getCellLayer().getUpdateManager().place(cell, c);
            initial.add(c);
        }
    }

    @Test
    public void testSurfaceCase() {
        DepthFilter query = new DepthFilter(layer, new ConstantInteger(0));
        List<Coordinate> actual = query.apply(initial);

        List<Coordinate> expected = new ArrayList<>();
        expected.add(new Coordinate2D(0, 2, 0));
        expected.add(new Coordinate2D(0, 6, 0));

        assertEquals(expected, actual);
    }

    @Test
    public void testDepth1Case() {
        DepthFilter query = new DepthFilter(layer, new ConstantInteger(1));
        List<Coordinate> actual = query.apply(initial);

        List<Coordinate> expected = new ArrayList<>();
        expected.add(new Coordinate2D(0, 2, 0));
        expected.add(new Coordinate2D(0, 3, 0));
        expected.add(new Coordinate2D(0, 5, 0));
        expected.add(new Coordinate2D(0, 6, 0));

        assertEquals(expected, actual);
    }

    public void testOriginalNotMutated() {
        DepthFilter query = new DepthFilter(layer, new ConstantInteger(0));
        List<Coordinate> expected = new ArrayList<>(initial);
        query.apply(initial);
        List<Coordinate> actual = initial;
        assertFalse(expected == actual);
        assertEquals(expected, actual);
    }
}