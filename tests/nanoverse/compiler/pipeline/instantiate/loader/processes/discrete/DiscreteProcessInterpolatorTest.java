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
import nanoverse.runtime.processes.discrete.AgentProcessArguments;
import org.junit.*;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

public class DiscreteProcessInterpolatorTest extends InterpolatorTest {

    private DiscreteProcessArgumentsLoader dpaLoader;
    private DiscreteProcessInterpolator query;

    @Before
    public void before() throws Exception {
        super.before();
        dpaLoader = mock(DiscreteProcessArgumentsLoader.class);
        query = new DiscreteProcessInterpolator(load, null, dpaLoader);
    }

    @Test
    public void cpArguments() throws Exception {
        AgentProcessArguments expected = mock(AgentProcessArguments.class);
        when(dpaLoader.instantiate(node, lm, p)).thenReturn(expected);

        AgentProcessArguments actual = query.cpArguments(node, lm, p);
        assertSame(expected, actual);
    }
}