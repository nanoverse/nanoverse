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

package nanoverse.runtime.agent.action;

import nanoverse.runtime.agent.Agent;
import nanoverse.runtime.agent.action.helper.*;
import nanoverse.runtime.control.arguments.IntegerArgument;
import nanoverse.runtime.control.identifiers.Coordinate;
import org.junit.*;
import test.LayerMocks;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CloneToChildPlacerTest extends LayerMocks {

    private SelfTargetHighlighter highlighter;
    private CoordAgentMapper mapper;
    private boolean noReplace;
    private Coordinate self, target;
    private Agent child;

    private CloneToChildPlacer query;

    @Override
    @Before
    public void before() throws Exception {
        super.before();
        highlighter = mock(SelfTargetHighlighter.class);

        mapper = mock(CoordAgentMapper.class);
        when(mapper.getLayerManager()).thenReturn(layerManager);

        noReplace = false;
        self = mock(Coordinate.class);
        target = mock(Coordinate.class);
        child = mock(Agent.class);

        query = new CloneToChildPlacer(highlighter, mapper, noReplace);
    }

    @Test
    public void placeHighlights() throws Exception {
        query.place(self, target, child);
        verify(highlighter).highlight(target, self);
    }

    @Test
    public void placeUnoccupied() throws Exception {
        query.place(self, target, child);
        verify(update).place(child, target);
    }

    @Test
    public void placeOccupiedLegal() throws Exception {
        when(viewer.isOccupied(target)).thenReturn(true);
        query.place(self, target, child);
        verify(update).banish(target);
        verify(update).place(child, target);
    }

    @Test(expected = IllegalStateException.class)
    public void placeOccupiedIllegalThrows() throws Exception {
        query = new CloneToChildPlacer(highlighter, mapper, true);
        when(viewer.isOccupied(target)).thenReturn(true);
        query.place(self, target, child);
    }

    @Test
    public void isNoReplace() throws Exception {
        query = new CloneToChildPlacer(highlighter, mapper, true);
        assertTrue(query.isNoReplace());
    }

    @Test
    public void getSelfChannel() throws Exception {
        IntegerArgument expected = mock(IntegerArgument.class);
        when(highlighter.getSelfChannel()).thenReturn(expected);
        assertSame(expected, query.getSelfChannel());
    }

    @Test
    public void getTargetChannel() throws Exception {
        IntegerArgument expected = mock(IntegerArgument.class);
        when(highlighter.getTargetChannel()).thenReturn(expected);
        assertSame(expected, query.getTargetChannel());
    }
}