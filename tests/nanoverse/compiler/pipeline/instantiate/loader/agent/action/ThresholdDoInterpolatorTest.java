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
import nanoverse.compiler.pipeline.translate.nodes.MapObjectNode;
import nanoverse.runtime.agent.action.ActionDescriptor;
import nanoverse.runtime.control.arguments.DoubleArgument;
import org.junit.*;

import java.util.function.Supplier;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

public class ThresholdDoInterpolatorTest extends InterpolatorTest {

    private ThresholdDoDefaults defaults;
    private ThresholdDoInterpolator query;

    @Before
    public void before() throws Exception {
        super.before();
        defaults = mock(ThresholdDoDefaults.class);
        query = new ThresholdDoInterpolator(load, defaults);
    }

    @Test
    public void action() throws Exception {
        MapObjectNode cNode = mock(MapObjectNode.class);
        when(node.getMember("action")).thenReturn(cNode);

        ActionLoader loader = mock(ActionLoader.class);
        when(load.getLoader(eq(node), eq("action"), anyBoolean())).thenReturn(loader);

        ActionDescriptor expected = mock(ActionDescriptor.class);
        when(loader.instantiate(cNode, lm, p)).thenReturn(expected);

        ActionDescriptor actual = query.action(node, lm, p);
        assertSame(expected, actual);
    }

    @Test
    public void minimum() throws Exception {
        Supplier<DoubleArgument> trigger = () -> query.minimum(node, random);
        verifyDoubleArgument("minimum", trigger);
    }

    @Test
    public void minimumDefault() throws Exception {
        DoubleArgument expected = mock(DoubleArgument.class);
        when(defaults.minimum()).thenReturn(expected);
        Runnable trigger = () -> query.minimum(node, random);
        verifyDoubleArgumentDefault("minimum", expected, trigger);
    }

    @Test
    public void maximum() throws Exception {
        Supplier<DoubleArgument> trigger = () -> query.maximum(node, random);
        verifyDoubleArgument("maximum", trigger);
    }

    @Test
    public void maximumDefault() throws Exception {
        DoubleArgument expected = mock(DoubleArgument.class);
        when(defaults.maximum()).thenReturn(expected);
        Runnable trigger = () -> query.maximum(node, random);
        verifyDoubleArgumentDefault("maximum", expected, trigger);
    }

    @Test
    public void layer() throws Exception {
        Supplier<String> trigger = () -> query.layer(node);
        verifyString("layer", trigger);
    }
}