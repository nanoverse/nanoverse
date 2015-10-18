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
import nanoverse.runtime.cells.*;
import nanoverse.runtime.control.halt.HaltCondition;
import nanoverse.runtime.control.identifiers.*;
import nanoverse.runtime.layers.*;
import org.junit.*;
import test.LegacyTest;

import java.util.List;
import java.util.stream.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by dbborens on 3/6/14.
 */
public class CompoundActionTest extends ActionTest {

    private List<Action> actions;
    private CompoundAction query;

    @Before @Override
    public void before() throws Exception {
        super.before();
        actions = makeActions();
        query = new CompoundAction(identity, mapper, highlighter, actions.stream());
    }

    private List<Action> makeActions() {
        Action a = mock(Action.class);
        Action b = mock(Action.class);
        Stream<Action> actionStream = Stream.of(a, b);
        return actionStream.collect(Collectors.toList());
    }

    @Test
    public void run() throws Exception {
        Coordinate caller = mock(Coordinate.class);
        query.run(caller);

        actions.stream()
            .forEach(action -> verifyRan(action, caller));
    }

    private void verifyRan(Action action, Coordinate caller) {
        try {
            verify(action).run(caller);
        } catch (HaltCondition ex) {
            fail();
        }
    }

    @Test
    public void copyIsDeep() throws Exception {
        Agent child = mock(Agent.class);

        Action a = actions.get(0);
        Action aCopy = mock(Action.class);
        when(a.copy(child)).thenReturn(aCopy);

        Action b = actions.get(1);
        Action bCopy = mock(Action.class);
        when(b.copy(child)).thenReturn(bCopy);

        CompoundAction copy = query.copy(child);

        Stream<Action> expected = Stream.of(aCopy, bCopy);
        Stream<Action> actual = copy.getActionSequence();
        
        assertStreamsEqual(expected, actual);
    }
}
