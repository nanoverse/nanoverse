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

package nanoverse.compiler.pipeline.instantiate.loader.processes;

import nanoverse.compiler.pipeline.instantiate.loader.InterpolatorTest;
import nanoverse.runtime.processes.BaseProcessArguments;
import org.junit.*;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

public class ProcessInterpolatorTest extends InterpolatorTest {

    private BaseProcessArgumentsLoader bpaLoader;
    private ProcessInterpolator query;

    @Before
    public void before() throws Exception {
        bpaLoader = mock(BaseProcessArgumentsLoader.class);
        query = new ProcessInterpolator(load, bpaLoader);
    }

    @Test
    public void baseArguments() throws Exception {
        BaseProcessArguments expected = mock(BaseProcessArguments.class);
        when(bpaLoader.instantiate(node, lm, p)).thenReturn(expected);

        BaseProcessArguments actual = query.arguments(node, lm, p);
        assertSame(expected, actual);
    }
}