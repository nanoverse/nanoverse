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

package nanoverse.compiler.pipeline.instantiate.loader.processes.continuum;

import nanoverse.compiler.pipeline.instantiate.loader.InterpolatorTest;
import nanoverse.compiler.pipeline.instantiate.loader.processes.ProcessLoader;
import nanoverse.compiler.pipeline.translate.nodes.*;
import nanoverse.compiler.pipeline.translate.symbol.MapSymbolTable;
import nanoverse.runtime.layers.continuum.*;
import nanoverse.runtime.processes.continuum.ContinuumProcess;
import org.junit.*;

import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

public class CompositeContinuumProcessInterpolatorTest extends InterpolatorTest {

    private CompositeContinuumProcessInterpolator query;
    private CompositeContinuumProcessDefaults defaults;

    @Before
    @Override
    public void before() throws Exception {
        super.before();
        defaults = mock(CompositeContinuumProcessDefaults.class);
        query = new CompositeContinuumProcessInterpolator(load, null, defaults);
    }

    @Test
    public void processes() throws Exception {
        MapObjectNode parent = mock(MapObjectNode.class);
        ListObjectNode node = mock(ListObjectNode.class);
        when(parent.hasMember("children")).thenReturn(true);
        when(parent.getMember("children")).thenReturn(node);
        MapObjectNode cNode = mock(MapObjectNode.class);
        when(node.getMemberStream()).thenReturn(Stream.of(cNode));

        MapSymbolTable ist = mock(MapSymbolTable.class);
        when(cNode.getSymbolTable()).thenReturn(ist);

        ProcessLoader loader = mock(ProcessLoader.class);
        when(ist.getLoader()).thenReturn(loader);

        ContinuumProcess process = mock(ContinuumProcess.class);
        when(loader.instantiate(cNode, lm, p)).thenReturn(process);

        Stream<ContinuumProcess> expected = Stream.of(process);
        Stream<ContinuumProcess> actual = query.processes(parent, lm, p);

        assertStreamsEqual(expected, actual);
    }

    @Test
    public void processesDefault() throws Exception {
        Stream<ContinuumProcess> expected = mock(Stream.class);
        when(defaults.processes()).thenReturn(expected);
        Stream<ContinuumProcess> actual = query.processes(null, lm, p);
        assertSame(expected, actual);
    }

    @Test
    public void layer() throws Exception {
        Supplier<String> trigger = () -> query.layer(node);
        verifyString("layer", trigger);
    }

    @Test
    public void scheduler() throws Exception {
        ContinuumLayer cl = mock(ContinuumLayer.class);
        when(lm.getContinuumLayer("test")).thenReturn(cl);

        ContinuumLayerScheduler expected = mock(ContinuumLayerScheduler.class);
        when(cl.getScheduler()).thenReturn(expected);

        ContinuumLayerScheduler actual = query.scheduler(lm, "test");

        assertSame(expected, actual);
    }
}