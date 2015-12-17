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

package nanoverse.compiler.pipeline.instantiate.loader.agent.action;

import nanoverse.compiler.pipeline.instantiate.loader.InterpolatorTest;
import nanoverse.compiler.pipeline.instantiate.loader.agent.AgentDescriptorLoader;
import nanoverse.compiler.pipeline.translate.nodes.MapObjectNode;
import nanoverse.runtime.control.arguments.*;
import org.junit.*;

import java.util.function.Supplier;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

public class MakeInterpolatorTestCaseSensitivityIssue extends InterpolatorTest {

    private MakeDefaults defaults;
    private MakeInterpolator query;

    @Before
    public void before() throws Exception {
        super.before();
        defaults = mock(MakeDefaults.class);
        query = new MakeInterpolator(load, defaults);
    }

    @Test
    public void targetHighlight() throws Exception {
        Supplier<IntegerArgument> trigger = () -> query.targetHighlight(node, random);
        verifyIntegerArgument("targetHighlight", trigger);
    }

    @Test
    public void targetHighlightDefault() throws Exception {
        IntegerArgument expected = mock(IntegerArgument.class);
        when(defaults.targetHighlight()).thenReturn(expected);
        Runnable trigger = () -> query.targetHighlight(node, random);
        verifyIntegerArgumentDefault("targetHighlight", expected, trigger);
    }

    @Test
    public void selfHighlight() throws Exception {
        Supplier<IntegerArgument> trigger = () -> query.selfHighlight(node, random);
        verifyIntegerArgument("selfHighlight", trigger);
    }

    @Test
    public void selfHighlightDefault() throws Exception {
        IntegerArgument expected = mock(IntegerArgument.class);
        when(defaults.selfHighlight()).thenReturn(expected);
        Runnable trigger = () -> query.selfHighlight(node, random);
        verifyIntegerArgumentDefault("selfHighlight", expected, trigger);
    }

    @Test
    public void description() throws Exception {
        MapObjectNode cNode = mock(MapObjectNode.class);
        when(node.getMember("description")).thenReturn(cNode);

        AgentDescriptorLoader loader = mock(AgentDescriptorLoader.class);
        when(load.getLoader(eq(node), eq("description"), anyBoolean())).thenReturn(loader);

        AgentDescriptor expected = mock(AgentDescriptor.class);
        when(loader.instantiate(cNode, lm, p)).thenReturn(expected);

        AgentDescriptor actual = query.description(node, lm, p);
        assertSame(expected, actual);
    }

    @Test
    public void descriptionDefault() throws Exception {
        AgentDescriptor expected = mock(AgentDescriptor.class);
        when(defaults.description(lm, p)).thenReturn(expected);

        AgentDescriptor actual = query.description(node, lm, p);
        assertSame(expected, actual);
    }

}