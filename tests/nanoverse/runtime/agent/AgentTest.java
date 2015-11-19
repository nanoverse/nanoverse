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

package nanoverse.runtime.agent;

import nanoverse.runtime.agent.control.BehaviorDispatcher;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.layers.continuum.Reaction;
import org.junit.*;
import test.TestBase;

import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by David B Borenstein on 1/25/14.
 */
public class AgentTest extends TestBase {
    private static final String NAME = "test";

    private CallbackManager callbackManager;
    private AgentContinuumManager reactionManager;
    private Supplier<Agent> supplier;
    private BehaviorDispatcher dispatcher;

    private Agent query;

    @Before
    public void before() throws Exception {
        callbackManager = mock(CallbackManager.class);
        reactionManager = mock(AgentContinuumManager.class);
        supplier = mock(Supplier.class);
        dispatcher = mock(BehaviorDispatcher.class);

        query = new Agent(NAME, callbackManager, reactionManager, supplier);
        query.setDispatcher(dispatcher);
    }

    @Test
    public void copy() throws Exception {
        Agent child = mock(Agent.class);
        when(supplier.get()).thenReturn(child);

        BehaviorDispatcher childDispatcher = mock(BehaviorDispatcher.class);
        when(dispatcher.copy(child)).thenReturn(childDispatcher);

        Agent actual = query.copy();
        assertSame(child, actual);
        verify(child).setName(NAME);
        verify(child).setDispatcher(childDispatcher);
    }

    @Test
    public void trigger() throws Exception {
        String behaviorName = "bn";
        Coordinate caller = mock(Coordinate.class);
        query.trigger(behaviorName, caller);
        verify(dispatcher).trigger(behaviorName, caller, NAME);
    }

    @Test
    public void die() throws Exception {
        query.die();
        verify(reactionManager).removeFromAll();
        verify(callbackManager).die();
    }

    @Test
    public void getReactionIds() throws Exception {
        Stream<String> expected = mock(Stream.class);
        when(reactionManager.getReactionIds()).thenReturn(expected);

        Stream<String> actual = query.getReactionIds();
        assertSame(expected, actual);
    }

    @Test
    public void getBehaviorNames() throws Exception {
        Stream<String> expected = mock(Stream.class);
        when(dispatcher.getBehaviorNames()).thenReturn(expected);

        Stream<String> actual = query.getBehaviorNames();
        assertSame(expected, actual);
    }

    @Test
    public void load() throws Exception {
        Reaction reaction = mock(Reaction.class);
        query.load(reaction);
        verify(reactionManager).schedule(reaction);
    }

    @Test
    public void getSetName() throws Exception {
        String expected = "expected";
        query.setName(expected);
        String actual = query.getName();
        assertEquals(expected, actual);
    }
}
