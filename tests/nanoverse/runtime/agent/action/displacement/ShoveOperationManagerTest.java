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

import java.util.HashSet;
import java.util.function.BiFunction;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

public class ShoveOperationManagerTest {

    private ShoveHelper helper;
    private BiFunction<Coordinate, Coordinate, Boolean> isBaseCase;
    private ShoveOperationManager query;
    private Coordinate c, d;
    private HashSet<Coordinate> sites;

    @Before
    public void before() throws Exception {
        helper = mock(ShoveHelper.class);
        isBaseCase = mock(BiFunction.class);
        query = new ShoveOperationManager(helper, isBaseCase);
        c = mock(Coordinate.class);
        d = mock(Coordinate.class);
        sites = new HashSet<>();
    }

    @Test
    public void baseCase() throws Exception {
        when(isBaseCase.apply(any(), any())).thenReturn(true);
        query.doShove(c, d, sites);
        verifyZeroInteractions(helper);
    }

    // Hello, technical debt!
//    @Test
//    public void recursiveCase() throws Exception {
//        when(isBaseCase.apply(any(), any())).thenReturn(false, false, true);
//        CoordinateTuple tuple = new CoordinateTuple(c, d);
//        when(helper.getNextTuple(c, d)).thenReturn(tuple);
//        when(helper.getNextTuple(d, c)).thenReturn(tuple);
//        query.doShove(c, d, sites);
//        verify(helper).swap(c, d);
//        verify(helper).swap(d, c);
//    }
}