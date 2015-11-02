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

import nanoverse.runtime.agent.Agent;
import nanoverse.runtime.control.arguments.IntegerArgument;
import nanoverse.runtime.control.identifiers.Coordinate;
import org.junit.*;

import java.util.List;
import java.util.stream.*;

import static org.mockito.Mockito.*;

/**
 * Created by dbborens on 2/11/14.
 */
public class TriggerTest extends ActionTest {

    private String behaviorName = "test";

    private IntegerArgument selfChannel;
    private IntegerArgument targetChannel;

    private Agent aAgent, bAgent;
    private Coordinate aCoord, bCoord;
    private List<Coordinate> targets;

    private Trigger query;

    @Override
    @Before
    public void before() throws Exception {
        super.before();
        selfChannel = mock(IntegerArgument.class);
        targetChannel = mock(IntegerArgument.class);

        query = new Trigger(identity, mapper, highlighter, behaviorName,
            targetRule, selfChannel, targetChannel);

        configureTargets();
        when(targetRule.report(callerAgent)).thenReturn(targets);
    }

    private void configureTargets() {
        aAgent = mock(Agent.class);
        aCoord = mock(Coordinate.class);
        when(mapper.resolveAgent(aCoord)).thenReturn(aAgent);

        bAgent = mock(Agent.class);
        bCoord = mock(Coordinate.class);
        when(mapper.resolveAgent(bCoord)).thenReturn(bAgent);

        targets = Stream.of(aCoord, bCoord).collect(Collectors.toList());
    }

    @Test
    public void runTriggersAllTargets() throws Exception {
        query.run(caller);
        verify(aAgent).trigger(behaviorName, ownLocation);
        verify(bAgent).trigger(behaviorName, ownLocation);
    }

    @Test
    public void missingSelfLocationSkips() throws Exception {
        when(identity.getOwnLocation()).thenReturn(null);
        query.run(caller);

        verifyNoMoreInteractions(aAgent, bAgent);
    }

    @Test
    public void runHighlightsAllTargets() throws Exception {
        query.run(caller);
        verify(highlighter).doHighlight(targetChannel, aCoord);
        verify(highlighter).doHighlight(targetChannel, bCoord);
    }

    @Test
    public void runHighlightsSelf() throws Exception {
        query.run(caller);
        verify(highlighter).doHighlight(selfChannel, ownLocation);
    }

    @Test
    public void missingTargetOK() throws Exception {
        when(mapper.resolveAgent(aCoord)).thenReturn(null);
        query.run(caller);
        verifyNoMoreInteractions(aAgent);
        verify(bAgent).trigger(behaviorName, ownLocation);
    }
}
