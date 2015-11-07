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
import org.junit.*;
import test.TestBase;

import java.util.*;
import java.util.stream.*;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class CardinalShoverTest extends TestBase {

    private CardinalShoverTargetHelper targetHelper;
    private ShoveHelper shoveHelper;
    private ShoveOperationManager operationManager;
    private Coordinate origin, displacement;

    private CardinalShover query;

    @Before
    public void before() throws Exception {
        targetHelper = mock(CardinalShoverTargetHelper.class);
        shoveHelper = mock(ShoveHelper.class);
        operationManager = mock(ShoveOperationManager.class);
        query = new CardinalShover(targetHelper, shoveHelper, operationManager);

        origin = mock(Coordinate.class);
        displacement = mock(Coordinate.class);
    }

    @Test
    public void shoveRandom() throws Exception {
        when(targetHelper.getDisplacementToRandomTarget(origin))
            .thenReturn(displacement);

        query.shoveRandom(origin);
        verify(operationManager).doShove(eq(origin), eq(displacement), any());
    }

    @Test
    public void doShove() throws Exception {
        doAnswer(invocation -> {
            HashSet<Coordinate> affectedSites = (HashSet<Coordinate>) invocation.getArguments()[2];
            affectedSites.add(origin);
            return null;
        }).when(operationManager).doShove(eq(origin), eq(displacement), any(HashSet.class));

        Set<Coordinate> expected = Stream.of(origin).collect(Collectors.toSet());
        HashSet<Coordinate> actual = query.doShove(origin, displacement);
        assertSetsEqual(expected, actual);
    }
}