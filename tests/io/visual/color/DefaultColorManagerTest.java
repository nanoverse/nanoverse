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

package io.visual.color;

import cells.MockCell;
import control.identifiers.Coordinate;
import control.identifiers.Coordinate2D;
import geometry.MockGeometry;
import junit.framework.TestCase;
import layers.MockLayerManager;
import layers.MockSystemState;
import layers.cell.CellLayer;

import java.awt.*;

/**
 * Created by dbborens on 4/2/14.
 */
public class DefaultColorManagerTest extends TestCase {
    private MockSystemState systemState;
    private Coordinate coord;
    private ColorManager query;
    private CellLayer layer;

    @Override
    protected void setUp() throws Exception {
        systemState = new MockSystemState();
        coord = new Coordinate2D(0, 0, 0);
        query = new DefaultColorManager();

        MockGeometry geom = new MockGeometry();
        geom.setCanonicalSites(new Coordinate[]{coord});
        MockLayerManager layerManager = new MockLayerManager();
        layer = new CellLayer(geom);
        layerManager.setCellLayer(layer);
        systemState.setLayerManager(layerManager);
    }

    // There are only three supported modes in the default color model,
    // so we can test all of them.
    public void testGetColor() throws Exception {
        // Test dead
//        systemState.setState(coord, 0);
        assertEquals(Color.BLACK, query.getColor(coord, systemState));

        // Test 1
        layer.getUpdateManager().place(new MockCell(1), coord);
        assertEquals(Color.BLUE, query.getColor(coord, systemState));

        // Test 2
        layer.getUpdateManager().banish(coord);
        layer.getUpdateManager().place(new MockCell(2), coord);
        assertEquals(Color.RED, query.getColor(coord, systemState));
    }

    public void testGetBorderColor() throws Exception {
        assertEquals(Color.DARK_GRAY, query.getBorderColor());
    }
}
