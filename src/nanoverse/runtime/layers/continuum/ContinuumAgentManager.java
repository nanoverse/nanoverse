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

import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.structural.annotations.FactoryTarget;

import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Created by dbborens on 12/31/14.
 */
public class ContinuumAgentManager {

    private final ContinuumAgentIndex index;
    private final ReactionLinker loader;
    private final String id;

    @FactoryTarget
    public ContinuumAgentManager(ReactionLinker loader, ContinuumAgentIndex index, String id) {
        this.index = index;
        this.id = id;
        this.loader = loader;
    }

    public ContinuumAgentManager(ReactionLinker loader, String id) {
        index = new ContinuumAgentIndex();
        this.id = id;
        this.loader = loader;
    }

    public void apply() {
        Stream<RelationshipTuple> relationships = index.getRelationships();
        loader.apply(relationships);
    }

    public void reset() {
        index.reset();
    }

    public ContinuumAgentLinker getLinker(Function<Coordinate, Double> stateLookup) {
        ContinuumAgentNotifier notifier = index.getNotifier();
        return new ContinuumAgentLinker(notifier, stateLookup);
    }

    public String getId() {
        return id;
    }
}
