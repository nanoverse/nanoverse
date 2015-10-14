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

package nanoverse.compiler.pipeline.instantiate.loader.geometry.set;

import nanoverse.compiler.pipeline.instantiate.loader.InterpolatorTest;
import nanoverse.compiler.pipeline.instantiate.loader.control.identifiers.CoordinateLoader;
import nanoverse.compiler.pipeline.translate.nodes.MapObjectNode;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.geometry.Geometry;
import nanoverse.runtime.layers.cell.AgentLayer;
import org.junit.*;

import java.util.function.Supplier;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

public class HLineCoordinateSetInterpolatorTest extends InterpolatorTest {

    private HLineCoordinateSetDefaults defaults;
    private HLineCoordinateSetInterpolator query;

    @Before
    public void before() throws Exception {
        super.before();
        defaults = mock(HLineCoordinateSetDefaults.class);
        query = new HLineCoordinateSetInterpolator(load, defaults);
    }

    @Test
    public void geometry() throws Exception {
        AgentLayer layer = mock(AgentLayer.class);
        when(lm.getAgentLayer()).thenReturn(layer);

        Geometry expected = mock(Geometry.class);
        when(layer.getGeometry()).thenReturn(expected);

        Geometry actual = query.geometry(lm);
        assertSame(expected, actual);
    }

    @Test
    public void origin() throws Exception {
        MapObjectNode cNode = mock(MapObjectNode.class);
        when(node.getMember("origin")).thenReturn(cNode);

        CoordinateLoader loader = mock(CoordinateLoader.class);
        when(load.getLoader(eq(node), eq("origin"), anyBoolean())).thenReturn(loader);

        Coordinate expected = mock(Coordinate.class);
        when(loader.instantiate(cNode, lm, p)).thenReturn(expected);

        Coordinate actual = query.origin(node, lm, p);
        assertSame(expected, actual);
    }

    @Test
    public void originDefault() throws Exception {
        Coordinate expected = mock(Coordinate.class);
        when(defaults.origin(lm)).thenReturn(expected);

        Coordinate actual = query.origin(node, lm, p);
        assertSame(expected, actual);
    }

    @Test
    public void length() throws Exception {
        Supplier<Integer> trigger = () -> query.length(node, random);
        verifyInteger("length", trigger);
    }

    @Test
    public void lengthDefault() throws Exception {
        int expected = 5;
        when(defaults.length()).thenReturn(expected);
        Runnable trigger = () -> query.length(node, random);
        verifyIntegerDefault("length", expected, trigger);
    }

}