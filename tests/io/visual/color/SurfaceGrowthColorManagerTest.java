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

import cells.Cell;
import cells.MockCell;
import control.arguments.ConstantDouble;
import control.identifiers.Coordinate;
import geometry.Geometry;
import geometry.boundaries.Absorbing;
import geometry.boundaries.Boundary;
import geometry.lattice.Lattice;
import geometry.lattice.LinearLattice;
import geometry.shape.Line;
import geometry.shape.Shape;
import io.visual.HSLColor;
import layers.MockLayerManager;
import layers.MockSystemState;
import layers.cell.CellLayer;
import test.EslimeTestCase;

import java.awt.*;

public class SurfaceGrowthColorManagerTest extends EslimeTestCase {

    private MockSystemState systemState;
    private SurfaceGrowthColorManager query;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        Lattice lattice = new LinearLattice();
        Shape shape = new Line(lattice, 5);
        Boundary boundary = new Absorbing(shape, lattice);
        Geometry geom = new Geometry(lattice, shape, boundary);

        CellLayer layer = new CellLayer(geom);
        MockLayerManager layerManager = new MockLayerManager();
        layerManager.setCellLayer(layer);
        put(layer, 1, 1);
        put(layer, 2, 1);
        put(layer, 3, 1);

        systemState = new MockSystemState();
        systemState.setLayerManager(layerManager);

        DefaultColorManager base = new DefaultColorManager();
        query = new SurfaceGrowthColorManager(base, new ConstantDouble(0.5), new ConstantDouble(1.0));
    }

    public void testGetBorderColor() throws Exception {
        Color actual = query.getBorderColor();
        Color expected = Color.DARK_GRAY;
        assertEquals(expected, actual);
    }

    public void testInteriorPoint() throws Exception {
        Color actual = getColor(2);
        Color expected = scaleColor(Color.BLUE);

        assertEquals(expected, actual);
    }

    public void testExteriorPoint() throws Exception {
        Color actual = getColor(1);
        Color expected = Color.BLUE;

        assertEquals(expected, actual);
    }

    public void testVacantPoint() throws Exception {
        Color actual = getColor(0);
        Color expected = Color.BLACK;

        assertEquals(expected, actual);
    }

    private Color getColor(int pos) {
        Coordinate c = new Coordinate(0, pos, 0);
        Color ret = query.getColor(c, systemState);

        return ret;
    }

    private Color scaleColor(Color original) {
        float[] colorArr = HSLColor.fromRGB(original);
        float h = colorArr[0];
        float s = colorArr[1];
        float l = colorArr[2];
        Color ret = HSLColor.toRGB(h, s, l * 0.5F);
        return ret;
    }

    private void put(CellLayer layer, int pos, int state) throws Exception {
        Coordinate coord = new Coordinate(0, pos, 0);
        Cell cell = new MockCell(state);
        layer.getUpdateManager().place(cell, coord);
    }
}