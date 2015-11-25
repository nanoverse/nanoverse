/*
 * Nanoverse: a declarative agent-based modeling language for natural and
 * social science.
 *
 * Copyright (c) 2015 David Bruce Borenstein and Nanoverse, LLC.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package nanoverse.compiler.pipeline.instantiate.loader.io.visual.color.palette;

import nanoverse.compiler.pipeline.instantiate.loader.InterpolatorTest;
import nanoverse.compiler.pipeline.instantiate.loader.io.visual.color.ColorLoader;
import nanoverse.compiler.pipeline.translate.nodes.DictionaryObjectNode;
import nanoverse.compiler.pipeline.translate.nodes.MapObjectNode;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

/**
 * Created by dbborens on 11/25/2015.
 */
public class CustomPaletteInterpolatorTest extends InterpolatorTest {

    private CustomPaletteInterpolator query;
    private ColorLoader colorLoader;

    @Override
    @Before
    public void before() throws Exception {
        super.before();
        colorLoader = mock(ColorLoader.class);
        query = new CustomPaletteInterpolator(load, colorLoader, null);
    }

    @Test
    public void mappings() throws Exception {
        MapObjectNode node = mock(MapObjectNode.class);
        when(node.hasMember("mappings")).thenReturn(true);

        DictionaryObjectNode cNode = mock(DictionaryObjectNode.class);
        when(node.getMember("mappings")).thenReturn(cNode);

        ColorMapLoader loader = mock(ColorMapLoader.class);
        when(load.getLoader(eq(node), eq("mappings"), anyBoolean())).thenReturn(loader);

        Map expected = mock(Map.class);
        when(loader.instantiate(cNode, p)).thenReturn(expected);

        Map actual = query.mappings(node, p);
        assertSame(expected, actual);
    }

    @Test
    public void noNodeReturnsEmpty() throws Exception {
        when(node.hasMember("mappings")).thenReturn(false);
        Map<String, Color> actual = query.mappings(node, p);
        assertEquals(0, actual.size());
    }

    @Test
    public void noParentReturnsEmpty() throws Exception {
        Map<String, Color> actual = query.mappings(null, p);
        assertEquals(0, actual.size());
    }
}