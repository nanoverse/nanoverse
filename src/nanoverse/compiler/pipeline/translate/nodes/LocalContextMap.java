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

package nanoverse.compiler.pipeline.translate.nodes;

import nanoverse.compiler.error.IllegalAssignmentError;

import java.util.*;
import java.util.stream.Stream;

/**
 * Created by dbborens on 2/22/15.
 */
public class LocalContextMap {
    private Map<String, ObjectNode> members;

    public LocalContextMap() {
        members = new HashMap<>();
    }

    public Stream<String> getMemberIdentifiers() {
        return members.keySet().stream();
    }

    public ObjectNode getMember(String name) {
        if (!members.containsKey(name)) {
            throw new IllegalStateException("Retrieval of undefined member '" + name + "'");
        }

        return members.get(name);
    }

    public void loadMember(String identifier, ObjectNode value) {
        if (members.containsKey(identifier)) {
            throw new IllegalAssignmentError("Double assignment to member '" + identifier + "'");
        }
        members.put(identifier, value);
    }

    public boolean hasMember(String name) {
        return members.containsKey(name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LocalContextMap that = (LocalContextMap) o;

        if (!members.equals(that.members)) return false;

        return true;
    }

    public int size() {
        return members.size();
    }
}
