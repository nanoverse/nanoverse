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

package agent.action;

import cells.MockCell;
import junit.framework.TestCase;

/**
 * Created by dbborens on 4/27/14.
 */
public class ActionRangeMapTest extends TestCase {
    public void testClone() throws Exception {
        MockCell originalCell = new MockCell(1);
        MockCell cloneCell = new MockCell(2);

        ActionRangeMap original = makeActionRangeMap(originalCell);
        ActionRangeMap expected = makeActionRangeMap(cloneCell);
        ActionRangeMap actual = original.clone(cloneCell);

        assertEquals(expected, actual);
        assertFalse(expected == actual);

        checkCallbacks(actual, cloneCell);
        checkCallbacks(original, originalCell);
    }

    private void checkCallbacks(ActionRangeMap map, MockCell cell) {
        for (Action key : map.getKeys()) {
            assertEquals(cell, key.getCallback());
        }
    }

    private ActionRangeMap makeActionRangeMap(MockCell cell) {
        ActionRangeMap ret = new ActionRangeMap(3);
        loadMockAction(ret, cell, 0.5, 1);
        loadMockAction(ret, cell, 0.75, 2);
        loadMockAction(ret, cell, 0.01, 3);
        return ret;
    }

    private void loadMockAction(ActionRangeMap map, MockCell cell, double weight, int identifier) {
        MockAction action = new MockAction();
        action.setIdentifier(identifier);
        action.setCallback(cell);
        map.add(action, weight);
    }
}
