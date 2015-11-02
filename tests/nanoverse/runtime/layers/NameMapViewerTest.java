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

package nanoverse.runtime.layers;

import nanoverse.runtime.layers.cell.NameMapViewer;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class NameMapViewerTest {

    @Test
    public void testGetNames() {
        // Set up
        NameMapViewer viewer = makeViewer();

        // Get name array
        String[] names = viewer.getNames();

        // Sort it
        Arrays.sort(names);

        // Verify elements
        assertEquals(names.length, 2);
        assertEquals(names[0], "1");
        assertEquals(names[1], "2");

    }

    private NameMapViewer makeViewer() {
        Map<String, Integer> stateMap = new HashMap<>();
        stateMap.put("1", 3);
        stateMap.put("2", 5);

        NameMapViewer viewer = new NameMapViewer(stateMap);

        return viewer;
    }

    @Test
    public void testGetCount() {
        // Set up
        NameMapViewer viewer = makeViewer();

        int count = viewer.getCount("2");

        assertEquals(count, 5);
    }
}
