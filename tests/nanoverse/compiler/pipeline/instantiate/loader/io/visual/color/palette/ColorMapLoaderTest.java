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

import nanoverse.compiler.pipeline.instantiate.loader.io.visual.color.ColorLoader;
import nanoverse.compiler.pipeline.instantiate.loader.primitive.strings.StringArgumentLoader;
import nanoverse.compiler.pipeline.translate.nodes.DictionaryObjectNode;
import nanoverse.compiler.pipeline.translate.nodes.ObjectNode;
import nanoverse.compiler.pipeline.translate.symbol.InstantiableSymbolTable;
import nanoverse.runtime.control.GeneralParameters;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by dbborens on 11/25/2015.
 */
public class ColorMapLoaderTest {

    private ColorMapLoader query;
    private ColorLoader colorLoader;
    private DictionaryObjectNode cNode;
    private GeneralParameters p;

    @Before
    public void before() throws Exception {
        colorLoader = mock(ColorLoader.class);
        query = new ColorMapLoader(colorLoader);
        cNode = mock(DictionaryObjectNode.class);
        p = mock(GeneralParameters.class);
    }

    @Test
    public void instantiate() throws Exception {
        ObjectNode colorNode = mock(ObjectNode.class);
        when(cNode.getMember("TEST")).thenReturn(colorNode);
        InstantiableSymbolTable ist = mock(InstantiableSymbolTable.class);
        when(colorNode.getSymbolTable()).thenReturn(ist);
        StringArgumentLoader loader = mock(StringArgumentLoader.class);
        when(ist.getLoader()).thenReturn(loader);
        when(loader.instantiateToFirst(colorNode)).thenReturn("COLOR");

        Color color = mock(Color.class);
        when(colorLoader.instantiate("COLOR", p)).thenReturn(color);

        Stream<String> inStream = Stream.of("TEST");
        when(cNode.getMemberIdentifiers()).thenReturn(inStream);

        Map<String, Color> actual = query.instantiate(cNode, p);
        assertEquals(1, actual.size());
        assertSame(color, actual.get("TEST"));
    }
}