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

package nanoverse.compiler.pipeline.instantiate.loader.io.visual.highlight;

import nanoverse.compiler.pipeline.instantiate.loader.InterpolatorTest;
import nanoverse.compiler.pipeline.translate.nodes.MapObjectNode;
import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.io.visual.highlight.Glyph;
import org.junit.*;

import java.util.function.Supplier;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

public class HighlightInterpolatorTest extends InterpolatorTest {

    private HighlightInterpolator query;

    @Before
    public void before() throws Exception {
        super.before();
        query = new HighlightInterpolator(load);
    }

    @Test
    public void channel() throws Exception {
        Supplier<Integer> trigger = () -> query.channel(node, random);
        verifyInteger("channel", trigger);
    }

    @Test
    public void glyph() throws Exception {
        MapObjectNode cNode = mock(MapObjectNode.class);
        when(node.getMember("glyph")).thenReturn(cNode);

        GlyphLoader loader = mock(GlyphLoader.class);
        when(load.getLoader(eq(node), eq("glyph"), anyBoolean())).thenReturn(loader);

        GeneralParameters p = mock(GeneralParameters.class);
        Glyph expected = mock(Glyph.class);
        when(loader.instantiate(cNode, p)).thenReturn(expected);

        Glyph actual = query.glyph(node, p);
        assertSame(expected, actual);
    }
}