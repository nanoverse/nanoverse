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

package nanoverse.runtime.io.visual.color;

import nanoverse.runtime.control.arguments.ConstantDouble;
import nanoverse.runtime.control.identifiers.*;
import nanoverse.runtime.geometry.Geometry;
import nanoverse.runtime.geometry.boundaries.*;
import nanoverse.runtime.geometry.lattice.*;
import nanoverse.runtime.geometry.shape.*;
import nanoverse.runtime.geometry.shape.Shape;
import nanoverse.runtime.io.visual.HSLColor;
import nanoverse.runtime.layers.*;
import nanoverse.runtime.layers.cell.AgentLayer;
import org.junit.*;
import test.LegacyTest;

import java.awt.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class SurfaceGrowthColorManagerTest extends LegacyTest {

    private MockSystemState systemState;
    private SurfaceGrowthColorManager query;

    @Before
    public void setUp() throws Exception {

        Lattice lattice = new LinearLattice();
        Shape shape = new Line(lattice, 5);
        Boundary boundary = new Absorbing(shape, lattice);
        Geometry geom = new Geometry(lattice, shape, boundary);

        AgentLayer layer = new AgentLayer(geom);
        MockLayerManager layerManager = new MockLayerManager();
        layerManager.setAgentLayer(layer);
        put(layer, 1, 1);
        put(layer, 2, 1);
        put(layer, 3, 1);

        systemState = new MockSystemState();
        systemState.setLayerManager(layerManager);

        DefaultColorManager base = new DefaultColorManager();
        query = new SurfaceGrowthColorManager(base, new ConstantDouble(0.5), new ConstantDouble(1.0));
    }

    private void put(AgentLayer layer, int pos, int state) throws Exception {
//        Coordinate coord = new Coordinate2D(0, pos, 0);
//        Agent agent = new MockAgent(state);
//        layer.getUpdateManager().place(agent, coord);
    }

    @Test
    public void testGetBorderColor() throws Exception {
        fail("Rewrite as a modern test");
        Color actual = query.getBorderColor();
        Color expected = Color.DARK_GRAY;
        assertEquals(expected, actual);
    }

    @Test
    public void testInteriorPoint() throws Exception {
        fail("Rewrite as a modern test");
        Color actual = getColor(2);
        Color expected = scaleColor(Color.BLUE);

        assertEquals(expected, actual);
    }

    private Color getColor(int pos) {
        Coordinate c = new Coordinate2D(0, pos, 0);
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

    @Test
    public void testExteriorPoint() throws Exception {
        fail("Rewrite as a modern test");
        Color actual = getColor(1);
        Color expected = Color.BLUE;

        assertEquals(expected, actual);
    }

    @Test
    public void testVacantPoint() throws Exception {
        fail("Rewrite as a modern test");
        Color actual = getColor(0);
        Color expected = Color.BLACK;

        assertEquals(expected, actual);
    }
}