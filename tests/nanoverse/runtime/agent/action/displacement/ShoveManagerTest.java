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

import static org.mockito.Mockito.*;

public class ShoveManagerTest {

    private CardinalShover cardinalShover;
    private ShortestPathShover shortestPathShover;
    private WeightedShover weightedShover;
    private Coordinate origin, target;
    private ShoveManager query;

    @Before
    public void before() throws Exception {
        cardinalShover = mock(CardinalShover.class);
        shortestPathShover = mock(ShortestPathShover.class);
        weightedShover = mock(WeightedShover.class);
        origin = mock(Coordinate.class);
        target = mock(Coordinate.class);

        query = new ShoveManager(cardinalShover, shortestPathShover, weightedShover);
    }

    @Test
    public void shove() throws Exception {
        query.shove(origin, target);
        verify(shortestPathShover).shove(origin, target);
    }

    @Test
    public void shoveRandom() throws Exception {
        query.shoveRandom(origin);
        verify(cardinalShover).shoveRandom(origin);
    }

    @Test
    public void shoveWeighted() throws Exception {
        query.shoveWeighted(origin);
        verify(weightedShover).shoveWeighted(origin);
    }
}