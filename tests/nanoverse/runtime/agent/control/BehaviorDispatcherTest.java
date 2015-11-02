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

package nanoverse.runtime.agent.control;

import nanoverse.runtime.agent.Agent;
import nanoverse.runtime.agent.action.Action;
import nanoverse.runtime.control.identifiers.Coordinate;
import org.junit.*;
import test.TestBase;

import java.util.HashMap;
import java.util.stream.Stream;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by David B Borenstein on 1/21/14.
 */
public class BehaviorDispatcherTest extends TestBase {

    private HashMap<String, Action> behaviors;
    private Action action;
    private Coordinate caller;

    private BehaviorDispatcher query;

    @Before
    public void before() throws Exception {
        behaviors = new HashMap<>();

        action = mock(Action.class);
        behaviors.put("test", action);

        query = new BehaviorDispatcher(behaviors);

        caller = mock(Coordinate.class);
    }

    @Test
    public void triggerRunsBehavior() throws Exception {
        query.trigger("test", caller);
        verify(action).run(caller);
    }

    @Test(expected = IllegalStateException.class)
    public void triggerMissingThrows() throws Exception {
        query.trigger("undefined", caller);
    }

    @Test
    public void copyIsDeep() throws Exception {
        Action actionCopy = mock(Action.class);
        Agent child = mock(Agent.class);
        when(action.copy(child)).thenReturn(actionCopy);

        BehaviorDispatcher copy = query.copy(child);

        // Check content is a deep copy of query
        long count = copy.getBehaviorNames().count();
        assertEquals(1, count);
        assertSame(actionCopy, copy.getMappedBehavior("test"));
    }

    @Test
    public void mapGetMapped() throws Exception {
        Action expected = mock(Action.class);
        query.map("expected", expected);

        Action actual = query.getMappedBehavior("expected");
        assertSame(expected, actual);
    }

    @Test
    public void getBehaviorNames() throws Exception {
        Stream<String> expected = Stream.of("test");
        Stream<String> actual = query.getBehaviorNames();
        assertStreamsEqual(expected, actual);
    }
}
