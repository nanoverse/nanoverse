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

package nanoverse.compiler.pipeline.instantiate.loader.processes.discrete;

import nanoverse.compiler.pipeline.instantiate.loader.InterpolatorTest;
import nanoverse.compiler.pipeline.instantiate.loader.processes.discrete.filter.FilterLoader;
import nanoverse.compiler.pipeline.translate.nodes.MapObjectNode;
import nanoverse.runtime.processes.discrete.filter.Filter;
import org.junit.*;

import java.util.function.Supplier;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

public class TriggerProcessInterpolatorTest extends InterpolatorTest {

    private TriggerProcessDefaults defaults;
    private TriggerProcessInterpolator query;

    @Before
    public void before() throws Exception {
        super.before();
        defaults = mock(TriggerProcessDefaults.class);
        query = new TriggerProcessInterpolator(load, null, null, defaults);
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

    @Test
    public void behavior() throws Exception {
        Supplier<String> trigger = () -> query.behavior(node);
        verifyString("behavior", trigger);
    }

    @Test
    public void requireNeighbors() throws Exception {
        Supplier<Boolean> trigger = () -> query.requireNeighbors(node, random);
        verifyBoolean("requireNeighbors", trigger);
    }

    @Test
    public void requireNeighborsDefault() throws Exception {
        when(defaults.requireNeighbors()).thenReturn(true);
        Runnable trigger = () -> query.requireNeighbors(node, random);
        verifyBooleanDefault("requireNeighbors", true, trigger);
    }

    @Test
    public void skipVacant() throws Exception {
        Supplier<Boolean> trigger = () -> query.skipVacant(node, random);
        verifyBoolean("skipVacant", trigger);
    }

    @Test
    public void skipVacantDefault() throws Exception {
        when(defaults.skipVacant()).thenReturn(true);
        Runnable trigger = () -> query.skipVacant(node, random);
        verifyBooleanDefault("skipVacant", true, trigger);
    }
}