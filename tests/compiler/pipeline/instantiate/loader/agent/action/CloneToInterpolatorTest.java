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

package compiler.pipeline.instantiate.loader.agent.action;

import agent.targets.TargetDescriptor;
import compiler.pipeline.instantiate.loader.InterpolatorTest;
import compiler.pipeline.instantiate.loader.agent.targets.TargetLoader;
import compiler.pipeline.instantiate.loader.control.ProcessManagerLoader;
import compiler.pipeline.translate.nodes.*;
import control.ProcessManager;
import control.arguments.IntegerArgument;
import layers.LayerManager;
import org.junit.*;

import java.util.function.Supplier;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CloneToInterpolatorTest extends InterpolatorTest {

    private CloneToDefaults defaults;
    private CloneToInterpolator query;

    @Before
    public void before() throws Exception {
        super.before();
        defaults = mock(CloneToDefaults.class);
        query = new CloneToInterpolator(load, defaults);
    }

    @Test
    public void noReplacement() throws Exception {
        Supplier<Boolean> trigger = () -> query.noReplacement(node, random);
        verifyBoolean("noReplacement", trigger);
    }

    @Test
    public void noReplacementDefault() throws Exception {
        when(defaults.noReplacement()).thenReturn(true);
        Runnable trigger = () -> query.noReplacement(node, random);
        verifyBooleanDefault("noReplacement", true, trigger);
    }

    @Test
    public void target() throws Exception {
        MapObjectNode cNode = mock(MapObjectNode.class);
        when(node.getMember("target")).thenReturn(cNode);

        TargetLoader loader = mock(TargetLoader.class);
        when(load.getLoader(eq(node), eq("target"), anyBoolean())).thenReturn(loader);

        TargetDescriptor expected = mock(TargetDescriptor.class);
        when(loader.instantiate(cNode, lm, p)).thenReturn(expected);

        TargetDescriptor actual = query.target(node, lm, p);
        assertSame(expected, actual);
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
}