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

package nanoverse.runtime.layers.continuum;

import nanoverse.runtime.agent.Agent;

import java.util.IdentityHashMap;
import java.util.function.*;
import java.util.stream.Stream;

/**
 * Agents can directly manipulate the continuum value at the coordinate where
 * they are located. This class tracks the set of all agents capable of
 * manipulating the state of this continuum in a particular way, such as by
 * exponentiating the local concentration, or directly adding to it.
 */
public class ContinuumAgentIndex {

    private IdentityHashMap<Agent, Supplier<RelationshipTuple>> map;

    public ContinuumAgentIndex() {
        map = new IdentityHashMap<>();
    }

    public ContinuumAgentIndex(IdentityHashMap<Agent, Supplier<RelationshipTuple>> map) {
        this.map = map;
    }

    public void reset() {
        map.clear();
    }

    public Stream<RelationshipTuple> getRelationships() {
        return map.values()
            .stream()
            .map(Supplier::get);
    }

    public ContinuumAgentNotifier getNotifier() {
        BiConsumer<Agent, Supplier<RelationshipTuple>> adder = (cell, supplier) -> add(cell, supplier);
        Consumer<Agent> remover = cell -> remove(cell);
        return new ContinuumAgentNotifier(adder, remover);
    }

    private void add(Agent cell, Supplier<RelationshipTuple> supplier) {
        if (map.containsKey(cell)) {
            throw new IllegalStateException("Attempted to add existing cell to relationship index");
        }
        map.put(cell, supplier);
    }

    private void remove(Agent cell) {
        if (!map.containsKey(cell)) {
            throw new IllegalStateException("Attempted to remove non-existent key from relationship index");
        }
        map.remove(cell);
    }

}
