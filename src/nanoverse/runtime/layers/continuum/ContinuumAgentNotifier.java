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

package nanoverse.runtime.layers.continuum;

import nanoverse.runtime.agent.Agent;

import java.util.function.*;

/**
 * Notifies a ContinuumAgentIndex that a related cell has been added or
 * removed.
 * <p>
 * Created by dbborens on 12/31/14.
 */
public class ContinuumAgentNotifier {

    private BiConsumer<Agent, Supplier<RelationshipTuple>> adder;
    private Consumer<Agent> remover;

    public ContinuumAgentNotifier(BiConsumer<Agent, Supplier<RelationshipTuple>> adder, Consumer<Agent> remover) {
        this.adder = adder;
        this.remover = remover;
    }

    public void add(Agent cell, Supplier<RelationshipTuple> supplier) {
        adder.accept(cell, supplier);
    }

    public void remove(Agent cell) {
        remover.accept(cell);
    }
}
