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

import nanoverse.runtime.cells.MockAgent;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by dbborens on 4/27/14.
 */
public class ActionRangeMapTest {

    @Test
    public void testClone() throws Exception {
        MockAgent originalAgent = new MockAgent(1);
        MockAgent cloneAgent = new MockAgent(2);

        ActionRangeMap original = makeActionRangeMap(originalAgent);
        ActionRangeMap expected = makeActionRangeMap(cloneAgent);
        ActionRangeMap actual = original.clone(cloneAgent);

        assertEquals(expected, actual);
        assertFalse(expected == actual);

        checkCallbacks(actual, cloneAgent);
        checkCallbacks(original, originalAgent);
    }

    private void checkCallbacks(ActionRangeMap map, MockAgent cell) {
        for (Action key : map.getKeys()) {
            assertEquals(cell, key.getCallback());
        }
    }

    private ActionRangeMap makeActionRangeMap(MockAgent cell) {
        ActionRangeMap ret = new ActionRangeMap(3);
        loadMockAction(ret, cell, 0.5, 1);
        loadMockAction(ret, cell, 0.75, 2);
        loadMockAction(ret, cell, 0.01, 3);
        return ret;
    }

    private void loadMockAction(ActionRangeMap map, MockAgent cell, double weight, int identifier) {
        MockAction action = new MockAction();
        action.setIdentifier(identifier);
        action.setCallback(cell);
        map.add(action, weight);
    }
}
