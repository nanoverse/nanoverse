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

import java.util.Random;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

public class RandomNeighborChooserTest extends LayerMocks {

    private Random random;
    private RandomNeighborChooser query;

    @Override
    @Before
    public void before() throws Exception {
        super.before();
        random = mock(Random.class);
        query = new RandomNeighborChooser(geometry, random);
    }

    @Test
    public void chooseRandomNeighbor() throws Exception {
        Coordinate c = mock(Coordinate.class);
        Coordinate o = mock(Coordinate.class);
        Coordinate[] options = new Coordinate[]{null, c};

        when(geometry.getNeighbors(o, Geometry.APPLY_BOUNDARIES)).thenReturn(options);
        when(random.nextInt(2)).thenReturn(1);

        Coordinate actual = query.chooseRandomNeighbor(o);
        assertSame(c, actual);
    }
}