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

package nanoverse.runtime.agent;

import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.layers.continuum.*;

import java.util.function.*;
import java.util.stream.Stream;

/**
 * Created by dbborens on 12/31/14.
 * <p>
 * Provides continuum access to cell reaction index.
 * <p>
 * This class is a candidate for redesign. It is too complicated.
 * Perhaps the continuum should just provide a single object that
 * defines this entire interface, and which can be passed right
 * through?
 */
public class AgentContinuumManager {

    private final AgentReactionIndex index;
    private final RelationshipResolver resolver;
    private final Function<String, ContinuumAgentLinker> layerResolver;

    public AgentContinuumManager(BehaviorCell cell,
                                 Supplier<Coordinate> locator,
                                 Function<String, ContinuumAgentLinker> layerResolver) {

        index = new AgentReactionIndex(cell);
        resolver = new RelationshipResolver(locator);
        this.layerResolver = layerResolver;
    }

    public AgentContinuumManager(AgentReactionIndex index,
                                 RelationshipResolver resolver,
                                 Function<String, ContinuumAgentLinker> layerResolver) {

        this.index = index;
        this.resolver = resolver;
        this.layerResolver = layerResolver;
    }

    public void schedule(Reaction reaction) {
        String id = reaction.getId();
        ContinuumAgentLinker linker = layerResolver.apply(id);
        Supplier<RelationshipTuple> supplier = resolver.resolve(reaction);
        index.schedule(linker, supplier, id);
    }

    public void removeFromAll() {
        index.removeFromAll();
    }

    public Stream<String> getReactionIds() {
        return index.getReactionIds();
    }
}
