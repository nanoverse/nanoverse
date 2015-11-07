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

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

public class CardinalShoverTargetHelperTest extends LayerMocks {

    private RandomNeighborChooser neighborChooser;
    private CardinalShoverTargetHelper query;

    @Override
    @Before
    public void before() throws Exception {
        super.before();
        neighborChooser = mock(RandomNeighborChooser.class);
        query = new CardinalShoverTargetHelper(agentLayer, neighborChooser);
    }

    @Test
    public void getDisplacementToRandomTarget() throws Exception {
        Coordinate origin = mock(Coordinate.class);
        Coordinate target = mock(Coordinate.class);
        Coordinate expected = mock(Coordinate.class);
        when(neighborChooser.chooseRandomNeighbor(origin)).thenReturn(target);
        when(geometry.getDisplacement(origin, target, Geometry.APPLY_BOUNDARIES))
            .thenReturn(expected);

        Coordinate actual = query.getDisplacementToRandomTarget(origin);
        assertSame(expected, actual);
    }
}