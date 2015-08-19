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

package compiler.pipeline.instantiate.loader;

import compiler.pipeline.instantiate.helpers.LoadHelper;
import compiler.pipeline.instantiate.loader.primitive.booleans.BooleanArgumentLoader;
import compiler.pipeline.instantiate.loader.primitive.doubles.DoubleArgumentLoader;
import compiler.pipeline.instantiate.loader.primitive.integers.IntegerArgumentLoader;
import compiler.pipeline.instantiate.loader.primitive.strings.StringArgumentLoader;
import compiler.pipeline.translate.nodes.MapObjectNode;
import compiler.pipeline.translate.nodes.ObjectNode;

import java.util.Random;

import static org.mockito.Mockito.*;

/**
 * Created by dbborens on 8/19/2015.
 */
public abstract class InterpolatorTest {

    protected ObjectNode configureStringValue(String member, String expected, LoadHelper loadHelper, MapObjectNode node) {
        ObjectNode cNode = mock(ObjectNode.class);
        when(node.getMember(member)).thenReturn(cNode);

        StringArgumentLoader loader = mock(StringArgumentLoader.class);
        when(loadHelper.getLoader(eq(node), eq(member), anyBoolean())).thenReturn(loader);
        when(loader.instantiateToFirst(cNode)).thenReturn(expected);
        return cNode;
    }

    protected ObjectNode configureDoubleValue(String member, Random random, Double expected, LoadHelper loadHelper, MapObjectNode node) {
        ObjectNode cNode = mock(ObjectNode.class);
        when(node.getMember(member)).thenReturn(cNode);

        DoubleArgumentLoader loader = mock(DoubleArgumentLoader.class);
        when(loadHelper.getLoader(eq(node), eq(member), anyBoolean())).thenReturn(loader);
        when(loader.instantiateToFirst(cNode, random)).thenReturn(expected);
        return cNode;
    }

    protected ObjectNode configureIntegerValue(String member, Random random, Integer expected, LoadHelper loadHelper, MapObjectNode node) {
        ObjectNode cNode = mock(ObjectNode.class);
        when(node.getMember(member)).thenReturn(cNode);

        IntegerArgumentLoader loader = mock(IntegerArgumentLoader.class);
        when(loadHelper.getLoader(eq(node), eq(member), anyBoolean())).thenReturn(loader);
        when(loader.instantiateToFirst(cNode, random)).thenReturn(expected);
        return cNode;
    }

    protected ObjectNode configureBooleanValue(String member, Random random, Boolean expected, LoadHelper loadHelper, MapObjectNode node) {
        ObjectNode cNode = mock(ObjectNode.class);
        when(node.getMember(member)).thenReturn(cNode);

        BooleanArgumentLoader loader = mock(BooleanArgumentLoader.class);
        when(loadHelper.getLoader(eq(node), eq(member), anyBoolean())).thenReturn(loader);
        when(loader.instantiateToFirst(cNode, random)).thenReturn(expected);
        return cNode;
    }

}
