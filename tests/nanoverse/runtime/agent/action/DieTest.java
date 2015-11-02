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
import nanoverse.runtime.agent.action.helper.*;
import nanoverse.runtime.agent.control.BehaviorDispatcher;
import nanoverse.runtime.cells.*;
import nanoverse.runtime.control.arguments.IntegerArgument;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.layers.LayerManager;
import org.junit.*;
import test.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by dbborens on 2/10/14.
 */
public class DieTest extends ActionTest {

    private Agent self;
    private Die query;
    private IntegerArgument channel;

    @Override @Before
    public void before() throws Exception {
        super.before();
        channel = mock(IntegerArgument.class);
        query = new Die(identity, mapper, highlighter, channel);

        self = mock(Agent.class);
        when(identity.getSelf()).thenReturn(self);
    }

    @Test
    public void runDoesHighlight() throws Exception {
        Coordinate c = mock(Coordinate.class);
        when(identity.getOwnLocation()).thenReturn(c);
        query.run(null);
        verify(highlighter).doHighlight(channel, c);
    }

    @Test
    public void runDies() throws Exception {
        query.run(null);
        verify(self).die();
    }
}
