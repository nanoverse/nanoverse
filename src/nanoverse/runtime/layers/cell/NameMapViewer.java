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

package nanoverse.runtime.layers.cell;

import java.util.Map;

/**
 * @author David Bruce Borenstein
 * @test StateMapViewerTest
 */
public class NameMapViewer {

    private Map<String, Integer> nameMap;

    public NameMapViewer(Map<String, Integer> nameMap) {
        this.nameMap = nameMap;
    }

    public String[] getNames() {
        String[] names = nameMap.keySet().toArray(new String[0]);
        return names;
    }

    public Integer getCount(String name) {

        // If it doesn't have the key, there are no agents of that state
        if (!nameMap.containsKey(name)) {
            return 0;
        }

        return nameMap.get(name);
    }
}
