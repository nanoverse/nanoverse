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
import nanoverse.runtime.control.arguments.IntegerArgument;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.geometry.Geometry;
import nanoverse.runtime.layers.cell.CellLayer;
import org.junit.*;

import java.util.function.Supplier;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

public class DiscCoordinateSetInterpolatorTest extends InterpolatorTest {

    private DiscCoordinateSetDefaults defaults;
    private DiscCoordinateSetInterpolator query;

    @Before
    public void before() throws Exception {
        super.before();
        defaults = mock(DiscCoordinateSetDefaults.class);
        query = new DiscCoordinateSetInterpolator(load, defaults);
    }

    @Test
    public void geometry() throws Exception {
        CellLayer layer = mock(CellLayer.class);
        when(lm.getCellLayer()).thenReturn(layer);

        Geometry expected = mock(Geometry.class);
        when(layer.getGeometry()).thenReturn(expected);

        Geometry actual = query.geometry(lm);
        assertSame(expected, actual);
    }

    @Test
    public void offset() throws Exception {
        MapObjectNode cNode = mock(MapObjectNode.class);
        when(node.getMember("offset")).thenReturn(cNode);

        CoordinateLoader loader = mock(CoordinateLoader.class);
        when(load.getLoader(eq(node), eq("offset"), anyBoolean())).thenReturn(loader);

        Coordinate expected = mock(Coordinate.class);
        when(loader.instantiate(cNode, lm, p)).thenReturn(expected);

        Coordinate actual = query.offset(node, lm, p);
        assertSame(expected, actual);
    }

    @Test
    public void offsetDefault() throws Exception {
        Coordinate expected = mock(Coordinate.class);
        when(defaults.offset(lm)).thenReturn(expected);

        Coordinate actual = query.offset(node, lm, p);
        assertSame(expected, actual);
    }

    @Test
    public void radiusArg() throws Exception {
        Supplier<IntegerArgument> trigger = () -> query.radiusArg(node, random);
        verifyIntegerArgument("radius", trigger);
    }

    @Test
    public void radiusArgDefault() throws Exception {
        IntegerArgument expected = mock(IntegerArgument.class);
        when(defaults.radius()).thenReturn(expected);
        Runnable trigger = () -> query.radiusArg(node, random);
        verifyIntegerArgumentDefault("radius", expected, trigger);
    }
}