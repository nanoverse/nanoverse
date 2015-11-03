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
import nanoverse.runtime.agent.targets.TargetRule;
import nanoverse.runtime.control.identifiers.Coordinate;
import org.junit.Before;
import test.LayerMocks;

import static org.mockito.Mockito.*;

/**
 * Standard mocks used to test actions.
 *
 * Created by dbborens on 10/18/2015.
 */
public class ActionTest extends LayerMocks {

    protected ActionIdentityManager identity;
    protected CoordAgentMapper mapper;
    protected ActionHighlighter highlighter;
    protected TargetRule targetRule;

    protected Agent callerAgent;
    protected Agent selfAgent;
    protected Coordinate caller;
    protected Coordinate ownLocation;

    @Before @Override
    public void before() throws Exception {
        super.before();
        identity = mock(ActionIdentityManager.class);
        mapper = mock(CoordAgentMapper.class);
        highlighter = mock(ActionHighlighter.class);
        targetRule = mock(TargetRule.class);

        when(mapper.getLayerManager()).thenReturn(layerManager);

        caller = mock(Coordinate.class);
        callerAgent = mock(Agent.class);
        when(mapper.resolveCaller(caller)).thenReturn(callerAgent);

        selfAgent = mock(Agent.class);
        when(identity.getSelf()).thenReturn(selfAgent);

        ownLocation = mock(Coordinate.class);
        when(identity.getOwnLocation()).thenReturn(ownLocation);
    }
}
