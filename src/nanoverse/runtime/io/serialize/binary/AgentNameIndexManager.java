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

package nanoverse.runtime.io.serialize.binary;

import nanoverse.runtime.layers.cell.AgentLayerViewer;

import java.util.HashMap;
import java.util.stream.Stream;

/**
 * Created by dbborens on 10/23/2015.
 */
public class AgentNameIndexManager {
    private final HashMap<String, Integer> nameToIndexMap;

    public AgentNameIndexManager() {
        nameToIndexMap = new HashMap<>();
    }

    public AgentNameIndexManager(HashMap<String, Integer> nameToIndexMap) {
        this.nameToIndexMap = nameToIndexMap;
    }

    public void init() {
        nameToIndexMap.clear();
    }

    public Stream<String> getNameStream() {
        return nameToIndexMap.keySet().stream();
    }

    public Integer getIndex(String name) {
        if (name == null) {
            return 0;
        }
        createIndexKeyIfNew(name);
        return nameToIndexMap.get(name);
    }

    private void createIndexKeyIfNew(String name) {
        if (!nameToIndexMap.containsKey(name)) {
            int next = nameToIndexMap.size() + 1;
            nameToIndexMap.put(name, next);
        }
    }

    public Stream<Integer> getIndexStream(AgentLayerViewer viewer) {
        return viewer.getNames()
            .map(this::getIndex);
    }
}
