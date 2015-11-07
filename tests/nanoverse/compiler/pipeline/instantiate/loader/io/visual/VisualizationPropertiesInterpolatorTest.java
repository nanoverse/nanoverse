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

package nanoverse.compiler.pipeline.instantiate.loader.io.visual;

import nanoverse.compiler.pipeline.instantiate.loader.InterpolatorTest;
import nanoverse.compiler.pipeline.instantiate.loader.io.visual.color.ColorModelLoader;
import nanoverse.compiler.pipeline.instantiate.loader.io.visual.highlight.HighlightManagerLoader;
import nanoverse.compiler.pipeline.translate.nodes.*;
import nanoverse.runtime.io.visual.color.ColorManager;
import nanoverse.runtime.io.visual.highlight.HighlightManager;
import org.junit.*;

import java.util.function.Supplier;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

public class VisualizationPropertiesInterpolatorTest extends InterpolatorTest {

    private VisualizationPropertiesDefaults defaults;
    private VisualizationPropertiesInterpolator query;

    @Before
    public void before() throws Exception {
        super.before();
        defaults = mock(VisualizationPropertiesDefaults.class);
        query = new VisualizationPropertiesInterpolator(load, defaults);
    }

    @Test
    public void edge() throws Exception {
        Supplier<Integer> trigger = () -> query.edge(node, random);
        verifyInteger("edge", trigger);
    }

    @Test
    public void edgeDefault() throws Exception {
        when(defaults.edge()).thenReturn(7);
        verifyIntegerDefault("edge", 7, () -> query.edge(node, random));
    }

    @Test
    public void outline() throws Exception {
        Supplier<Integer> trigger = () -> query.outline(node, random);
        verifyInteger("outline", trigger);
    }

    @Test
    public void outlineDefault() throws Exception {
        when(defaults.outline()).thenReturn(7);
        verifyIntegerDefault("outline", 7, () -> query.outline(node, random));
    }

    @Test
    public void colorModel() throws Exception {
        ColorModelLoader loader = mock(ColorModelLoader.class);
        when(load.getLoader(eq(node), eq("color"), anyBoolean()))
            .thenReturn(loader);

        MapObjectNode cNode = mock(MapObjectNode.class);
        when(node.getMember("color")).thenReturn(cNode);

        ColorManager expected = mock(ColorManager.class);
        when(loader.instantiate(cNode, p)).thenReturn(expected);

        ColorManager actual = query.colorModel(node, p);

        assertSame(expected, actual);
    }

    @Test
    public void colorModelDefault() throws Exception {
        ColorManager expected = mock(ColorManager.class);
        when(defaults.color(p)).thenReturn(expected);
        ColorManager actual = query.colorModel(node, p);
        assertSame(expected, actual);
    }

    @Test
    public void highlights() throws Exception {
        HighlightManagerLoader loader = mock(HighlightManagerLoader.class);
        when(load.getLoader(eq(node), eq("highlights"), anyBoolean()))
            .thenReturn(loader);

        ListObjectNode cNode = mock(ListObjectNode.class);
        when(node.getMember("highlights")).thenReturn(cNode);

        HighlightManager expected = mock(HighlightManager.class);
        when(loader.instantiate(cNode, p)).thenReturn(expected);

        HighlightManager actual = query.highlights(node, p);

        assertSame(expected, actual);
    }

    @Test
    public void highlightsDefault() throws Exception {
        HighlightManager expected = mock(HighlightManager.class);
        when(defaults.highlights(p)).thenReturn(expected);
        HighlightManager actual = query.highlights(node, p);
        assertSame(expected, actual);
    }
}