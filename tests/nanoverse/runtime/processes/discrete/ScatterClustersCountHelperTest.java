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

package nanoverse.runtime.processes.discrete;

import nanoverse.runtime.control.arguments.IntegerArgument;
import org.junit.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ScatterClustersCountHelperTest {

    private IntegerArgument maxTargets;
    private IntegerArgument neighborCount;
    private ScatterClustersCountHelper query;

    @Before
    public void before() throws Exception {
        maxTargets = mock(IntegerArgument.class);
        neighborCount = mock(IntegerArgument.class);
        query = new ScatterClustersCountHelper(maxTargets, neighborCount);
    }

    @Test
    public void getCeiling() throws Exception {
        when(maxTargets.next()).thenReturn(2);
        assertEquals(2, query.getCeiling());
    }

    @Test(expected = IllegalArgumentException.class)
    public void nBelowZeroThrows() throws Exception {
        when(maxTargets.next()).thenReturn(-1);
        query.getCeiling();
    }

    @Test
    public void getNeighborCount() throws Exception {
        when(neighborCount.next()).thenReturn(3);
        assertEquals(3, query.getNeighborCount());
    }
}