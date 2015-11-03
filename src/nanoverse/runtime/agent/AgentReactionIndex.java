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

package nanoverse.runtime.agent;

import nanoverse.runtime.layers.continuum.*;

import java.util.HashSet;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Created by dbborens on 9/25/2015.
 */
public class AgentReactionIndex {

    private final HashSet<String> reactionIds;
    private final HashSet<Runnable> index;
    private final Agent cell;

    public AgentReactionIndex(HashSet<String> reactionIds,
                              HashSet<Runnable> index,
                              Agent cell) {

        this.reactionIds = reactionIds;
        this.index = index;
        this.cell = cell;
    }

    public AgentReactionIndex(Agent cell) {

        reactionIds = new HashSet<>();
        index = new HashSet<>();
        this.cell = cell;
    }

    public void schedule(ContinuumAgentLinker linker, Supplier<RelationshipTuple> supplier, String id) {
        linker.add(cell, supplier);

        Runnable remover = linker.getRemover(cell);
        index.add(remover);

        reactionIds.add(id);
    }

    public void removeFromAll() {
        index.forEach(Runnable::run);
    }

    public Stream<String> getReactionIds() {
        return reactionIds.stream();
    }
}
