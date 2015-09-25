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

package nanoverse.compiler.pipeline.instantiate.loader.io.serialize.binary;

import nanoverse.compiler.pipeline.instantiate.loader.InterpolatorTest;
import nanoverse.compiler.pipeline.instantiate.loader.io.visual.highlight.IntegerStreamLoader;
import nanoverse.compiler.pipeline.translate.nodes.ListObjectNode;
import nanoverse.runtime.control.GeneralParameters;
import org.junit.*;

import java.util.stream.Stream;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class HighlightWriterInterpolatorTest extends InterpolatorTest {

    private HighlightWriterInterpolator query;
    private GeneralParameters p;

    @Before
    @Override
    public void before() throws Exception {
        super.before();
        p = mock(GeneralParameters.class);
        query = new HighlightWriterInterpolator(load);
    }

    @Test
    public void channels() throws Exception {
        IntegerStreamLoader loader = mock(IntegerStreamLoader.class);
        when(load.getLoader(eq(node), eq("channels"), anyBoolean()))
            .thenReturn(loader);

        ListObjectNode cNode = mock(ListObjectNode.class);
        when(node.getMember("channels")).thenReturn(cNode);

        Stream<Integer> expected = mock(Stream.class);
        when(loader.instantiate(cNode, p)).thenReturn(expected);

        Stream<Integer> actual = query.channels(node, p);
        assertSame(expected, actual);
    }
}