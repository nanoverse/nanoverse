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

package nanoverse.runtime.agent.action.displacement;

import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.geometry.Geometry;
import org.junit.*;
import test.LayerMocks;

import java.util.*;
import java.util.stream.*;

import static org.mockito.Mockito.*;

public class WeightedShoverTest extends LayerMocks {

    private ShoveOperationManager operationManager;
    private ShoveHelper shoveHelper;
    private WeightedShoveTargetChooser chooser;
    private WeightedShover query;

    @Override
    @Before
    public void before() throws Exception {
        super.before();
        operationManager = mock(ShoveOperationManager.class);
        shoveHelper = mock(ShoveHelper.class);
        chooser = mock(WeightedShoveTargetChooser.class);
        query = new WeightedShover(agentLayer, operationManager, shoveHelper, chooser);
    }

    @Test
    public void shoveWeighted() throws Exception {
        Coordinate origin, target, displacement;
        origin = mock(Coordinate.class);
        target = mock(Coordinate.class);
        displacement = mock(Coordinate.class);

        when(geometry.getDisplacement(origin, target, Geometry.APPLY_BOUNDARIES))
            .thenReturn(displacement);

        when(chooser.choose(origin)).thenReturn(target);
        doAnswer(invocation -> {
            HashSet<Coordinate> affectedSites = (HashSet<Coordinate>) invocation.getArguments()[2];
            affectedSites.add(origin);
            return null;
        }).when(operationManager).doShove(eq(origin), eq(displacement), any(HashSet.class));

        Set<Coordinate> expected = Stream.of(origin).collect(Collectors.toSet());
        HashSet<Coordinate> actual = query.shoveWeighted(origin);
        assertSetsEqual(expected, actual);
    }
}