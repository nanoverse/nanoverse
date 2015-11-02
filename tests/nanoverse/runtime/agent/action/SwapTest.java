/*
 * Copyright (c) 2014, 2015 David Bruce Borenstein and the
 * Trustees of Princeton University.
 *
 * This file is part of the Nanoverse simulation framework
 * (patent pending).
 *
 * This program is free software: you can redistribute it
 * and/or modify it under the terms of the GNU Affero General
 * Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * This program is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 * PURPOSE.  See the GNU Affero General Public License for
 * more details.
 *
 * You should have received a copy of the GNU Affero General
 * Public License along with this program.  If not, see
 * <http://www.gnu.org/licenses/>.
 */

package nanoverse.runtime.agent.action;

import nanoverse.runtime.control.arguments.IntegerArgument;
import nanoverse.runtime.control.identifiers.Coordinate;
import org.junit.*;

import java.util.List;

import static org.mockito.Mockito.*;

public class SwapTest extends ActionTest {

    private IntegerArgument selfChannel;
    private IntegerArgument targetChannel;
    private Swap query;
    private List<Coordinate> targets;

    @Before
    @Override
    public void before() throws Exception {
        super.before();
        selfChannel = mock(IntegerArgument.class);
        targetChannel = mock(IntegerArgument.class);
        targets = makeTargets();

        query = new Swap(identity, mapper, highlighter, targetRule,
            selfChannel, targetChannel);

    }

    private List<Coordinate> makeTargets() {
        Coordinate c = mock(Coordinate.class);
        List<Coordinate> targets = mock(List.class);
        when(targets.size()).thenReturn(1);
        when(targets.get(0)).thenReturn(c);
        when(targetRule.report(callerAgent)).thenReturn(targets);
        return targets;
    }

    @Test
    public void runCallsSwap() throws Exception {
        Coordinate other = targets.get(0);
        query.run(caller);
        verify(update).swap(ownLocation, other);
    }

    @Test(expected = IllegalStateException.class)
    public void multipleTargetsThrows() throws Exception {
        when(targets.size()).thenReturn(4);
        query.run(caller);
    }

    @Test(expected = IllegalStateException.class)
    public void zeroTargetsThrows() throws Exception {
        when(targets.size()).thenReturn(0);
        query.run(caller);
    }

    @Test
    public void runDoesHighlight() throws Exception {
        Coordinate other = targets.get(0);

        query.run(caller);
        verify(highlighter).doHighlight(selfChannel, ownLocation);
        verify(highlighter).doHighlight(targetChannel, other);
    }
}