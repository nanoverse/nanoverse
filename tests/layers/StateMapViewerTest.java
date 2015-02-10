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

package layers;

import junit.framework.TestCase;
import layers.cell.StateMapViewer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class StateMapViewerTest extends TestCase {

    public void testGetStates() {
        // Set up
        StateMapViewer viewer = makeViewer();

        // Get state array
        Integer[] states = viewer.getStates();

        // Sort it
        Arrays.sort(states);

        // Verify elements
        assertEquals(states.length, 2);
        assertEquals((int) states[0], 1);
        assertEquals((int) states[1], 2);

    }

    public void testGetCount() {
        // Set up
        StateMapViewer viewer = makeViewer();

        int count = viewer.getCount(2);

        assertEquals(count, 5);
    }

    private StateMapViewer makeViewer() {
        Map<Integer, Integer> stateMap = new HashMap<Integer, Integer>();
        stateMap.put(1, 3);
        stateMap.put(2, 5);

        StateMapViewer viewer = new StateMapViewer(stateMap);

        return viewer;
    }
}
