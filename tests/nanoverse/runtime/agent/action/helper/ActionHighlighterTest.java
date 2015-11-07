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

package nanoverse.runtime.agent.action.helper;

import nanoverse.runtime.control.arguments.IntegerArgument;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.geometry.Geometry;
import nanoverse.runtime.processes.StepState;
import org.junit.*;
import test.LayerMocks;

import static org.mockito.Mockito.*;

public class ActionHighlighterTest extends LayerMocks {

    private StepState stepState;
    private Coordinate toHighlight;
    private Geometry geometry;

    private IntegerArgument channelArg;
    private Integer channel = 1;

    private ActionHighlighter query;

    @Before
    public void before() throws Exception {
        super.before();

        stepState = mock(StepState.class);
        when(layerManager.getStepState()).thenReturn(stepState);


        query = new ActionHighlighter(layerManager);

        channelArg = mock(IntegerArgument.class);
        when(channelArg.next()).thenReturn(channel);

        geometry = mock(Geometry.class);
        when(agentLayer.getGeometry()).thenReturn(geometry);

        toHighlight = mock(Coordinate.class);
        when(geometry.isInBounds(toHighlight)).thenReturn(true);

    }

    @Test
    public void highlightNotifiesStepState() throws Exception {
        query.doHighlight(channelArg, toHighlight);
        verify(stepState).highlight(toHighlight, channel);
    }

    @Test
    public void noChannelSkips() throws Exception {
        query.doHighlight(null, toHighlight);
        verifyNoMoreInteractions(stepState);
    }

    @Test
    public void outOfBoundsSkips() throws Exception {
        when(geometry.isInBounds(toHighlight)).thenReturn(false);
        query.doHighlight(channelArg, toHighlight);
        verifyNoMoreInteractions(stepState);
    }
}