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

package layers.cell;

import java.util.Map;

/**
 * @author David Bruce Borenstein
 * @test StateMapViewerTest
 */
public class StateMapViewer {

    private Map<Integer, Integer> stateMap;

    public StateMapViewer(Map<Integer, Integer> stateMap) {
        this.stateMap = stateMap;
    }

    public Integer[] getStates() {
        Integer[] states = stateMap.keySet().toArray(new Integer[0]);
        return states;
    }

    public Integer getCount(Integer state) {

        // If it doesn't have the key, there are no cells of that state
        if (!stateMap.containsKey(state)) {
            return 0;
        }

        return stateMap.get(state);
    }
}
