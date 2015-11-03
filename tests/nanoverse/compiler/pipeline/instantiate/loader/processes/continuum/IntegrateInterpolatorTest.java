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
import nanoverse.runtime.layers.continuum.*;
import org.junit.*;

import java.util.function.Supplier;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

public class IntegrateInterpolatorTest extends InterpolatorTest {

    private IntegrateInterpolator query;

    @Before
    public void before() throws Exception {
        super.before();
        query = new IntegrateInterpolator(load, null);
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