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

package nanoverse.compiler.pipeline.instantiate.loader.agent.targets;

import nanoverse.compiler.pipeline.instantiate.loader.InterpolatorTest;
import nanoverse.compiler.pipeline.instantiate.loader.processes.discrete.filter.FilterLoader;
import nanoverse.compiler.pipeline.translate.nodes.MapObjectNode;
import nanoverse.runtime.layers.cell.AgentLayer;
import nanoverse.runtime.processes.discrete.filter.Filter;
import org.junit.*;

import java.util.function.Supplier;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

public class TargetInterpolatorTest extends InterpolatorTest {

    private TargetDefaults defaults;
    private AgentLayer layer;
    private TargetInterpolator query;

    @Before
    @Override
    public void before() throws Exception {
        super.before();
        defaults = mock(TargetDefaults.class);
        layer = mock(AgentLayer.class);
        query = new TargetInterpolator(load, defaults);
    }

    @Test
    public void maximum() throws Exception {
        Supplier<Integer> trigger = () -> query.maximum(node, random);
        verifyInteger("maximum", trigger);
    }

    @Test
    public void maximumDefault() throws Exception {
        int expected = TargetDefaults.DEFAULT_MAXIMUM;
        when(defaults.maximum()).thenReturn(expected);
        Runnable trigger = () -> query.maximum(node, random);
        verifyIntegerDefault("maximum", expected, trigger);
    }

    @Test
    public void filter() throws Exception {
        MapObjectNode cNode = mock(MapObjectNode.class);
        when(node.getMember("filter")).thenReturn(cNode);

        FilterLoader loader = mock(FilterLoader.class);
        when(load.getLoader(eq(node), eq("filter"), anyBoolean())).thenReturn(loader);

        Filter expected = mock(Filter.class);
        when(loader.instantiate(cNode, lm, p)).thenReturn(expected);

        Filter actual = query.filter(node, lm, p);
        assertSame(expected, actual);
    }

    @Test
    public void filterDefault() throws Exception {
        Filter expected = mock(Filter.class);
        when(defaults.filter(lm, p)).thenReturn(expected);
        Filter actual = query.filter(node, lm, p);
        assertSame(expected, actual);
    }
}