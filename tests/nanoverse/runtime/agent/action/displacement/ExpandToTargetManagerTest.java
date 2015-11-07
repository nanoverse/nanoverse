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

import nanoverse.runtime.agent.action.ActionTest;
import nanoverse.runtime.control.identifiers.Coordinate;
import org.junit.*;

import java.util.Random;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

public class ExpandToTargetManagerTest extends ActionTest {

    private Random random;
    private DisplacementManager displacementManager;
    private ExpandToTargetManager query;
    private DisplacementOptionResolver resolver;
    private DisplacementOption oOpt, tOpt;
    private Coordinate target;

    @Override
    @Before
    public void before() throws Exception {
        super.before();
        random = mock(Random.class);
        displacementManager = mock(DisplacementManager.class);
        resolver = mock(DisplacementOptionResolver.class);
        query = new ExpandToTargetManager(identity, random, displacementManager, resolver);
        target = mock(Coordinate.class);
    }

    @Test
    public void getShortestOriginCloser() throws Exception {
        configureOptions(1, 2);
        DisplacementOption actual = query.getShortestOption(target);
        assertSame(oOpt, actual);
    }

    private void configureOptions(int oDist, int tDist) throws Exception {
        oOpt = makeOption(oDist);
        when(resolver.getOption(ownLocation)).thenReturn(oOpt);

        tOpt = makeOption(tDist);
        when(resolver.getOption(target)).thenReturn(tOpt);
    }

    private DisplacementOption makeOption(int distance) {
        DisplacementOption option = mock(DisplacementOption.class);
        when(option.getDistance()).thenReturn(distance);
        return option;
    }

    @Test
    public void getShortestTargetCloser() throws Exception {
        configureOptions(2, 1);
        DisplacementOption actual = query.getShortestOption(target);
        assertSame(tOpt, actual);
    }

    @Test
    public void getShortestEqualDistance() throws Exception {
        when(random.nextBoolean()).thenReturn(true);
        configureOptions(2, 2);
        DisplacementOption actual = query.getShortestOption(target);
        assertSame(oOpt, actual);
    }

    @Test
    public void doShove() throws Exception {
        configureOptions(1, 1);
        Coordinate occupied = mock(Coordinate.class);
        when(oOpt.getOccupied()).thenReturn(occupied);

        Coordinate vacant = mock(Coordinate.class);
        when(oOpt.getVacant()).thenReturn(vacant);

        query.doShove(oOpt);
        verify(displacementManager).shove(occupied, vacant);
    }
}