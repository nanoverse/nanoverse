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

import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.geometry.set.CoordinateSet;
import nanoverse.runtime.processes.BaseProcessArguments;
import nanoverse.runtime.processes.discrete.filter.Filter;
import org.junit.*;
import org.mockito.InOrder;
import test.LayerMocks;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;

public class ScatterClustersTest extends LayerMocks {

    private BaseProcessArguments arguments;
    private AgentProcessArguments cpArguments;
    private Filter targetFilter;
    private ScatterClustersCountHelper ceilingHelper;
    private ScatterClustersPlacementHelper placementHelper;
    private Consumer<List<Coordinate>> shuffler;
    private ScatterClusters query;

    @Override
    @Before
    public void before() throws Exception {
        super.before();
        arguments = mock(BaseProcessArguments.class);
        cpArguments = mock(AgentProcessArguments.class);
        targetFilter = mock(Filter.class);
        ceilingHelper = mock(ScatterClustersCountHelper.class);
        placementHelper = mock(ScatterClustersPlacementHelper.class);
        shuffler = mock(Consumer.class);
        query = new ScatterClusters(arguments, cpArguments, targetFilter, ceilingHelper, placementHelper, shuffler);
    }

    @Test
    public void lifeCycle() throws Exception {
        CoordinateSet cc = mock(CoordinateSet.class);
        when(cc.stream()).thenReturn(Stream.empty());

        when(cpArguments.getActiveSites()).thenReturn(cc);
        List<Coordinate> candidates = mock(List.class);
        when(targetFilter.apply(any())).thenReturn(candidates);

        int n = 1;
        when(ceilingHelper.getCeiling()).thenReturn(n);

        int m = 2;
        when(ceilingHelper.getNeighborCount()).thenReturn(m);

        Iterator<Coordinate> cIter = mock(Iterator.class);
        when(candidates.iterator()).thenReturn(cIter);

        query.target(null);
        query.fire(null);

        InOrder inOrder = inOrder(shuffler, placementHelper);
        inOrder.verify(shuffler).accept(candidates);
        inOrder.verify(placementHelper).doPlacement(n, m, cIter);
    }
}