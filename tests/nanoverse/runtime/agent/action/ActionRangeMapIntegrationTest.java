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
import nanoverse.runtime.cells.MockAgent;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by dbborens on 4/27/14.
 */
public class ActionRangeMapIntegrationTest {

    @Test
    public void copy() {
        ActionRangeMap toCopy = new ActionRangeMap();
        Agent child = mock(Agent.class);

        Action a = mock(Action.class);
        Action aCopy = mock(Action.class);
        when(a.copy(child)).thenReturn(aCopy);

        Action b = mock(Action.class);
        Action bCopy = mock(Action.class);
        when(b.copy(child)).thenReturn(bCopy);

        toCopy.add(a, 1.0);
        toCopy.add(b, 0.5);

        ActionRangeMap copy = toCopy.copy(child);
        assertSame(aCopy, copy.selectTarget(0.25));
        assertEquals(bCopy, copy.selectTarget(1.25));
    }

}
