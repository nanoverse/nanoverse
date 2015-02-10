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

package cells;

import control.identifiers.Coordinate;
import factory.cell.Reaction;
import layers.continuum.ContinuumAgentLinker;
import layers.continuum.RelationshipTuple;

import java.util.HashSet;
import java.util.function.Function;
import java.util.function.Supplier;
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

    private RemoverIndex index;
    private BehaviorCell cell;
    private Supplier<Coordinate> locator;
    private Function<String, ContinuumAgentLinker> linkerLookup;
    private HashSet<String> reactionIds;

    public AgentContinuumManager(BehaviorCell cell,
                                 RemoverIndex index,
                                 Supplier<Coordinate> locator,
                                 Function<String, ContinuumAgentLinker> linkerLookup) {

        this.index = index;
        this.cell = cell;
        this.locator = locator;
        this.linkerLookup = linkerLookup;
        reactionIds = new HashSet<>();
    }

    public void schedule(Reaction reaction) {
        String id = reaction.getId();
        ContinuumAgentLinker linker = linkerLookup.apply(id);
        Supplier<RelationshipTuple> supplier = () -> getRelationshipTuple(reaction);
        linker.getNotifier().add(cell, supplier);
        index.add(() -> linker.getNotifier().remove(cell));
        reactionIds.add(id);
    }

    private RelationshipTuple getRelationshipTuple(Reaction reaction) {
        Coordinate c = locator.get();
        return new RelationshipTuple(c, reaction);
    }

    public void removeFromAll() {
        index.removeFromAll();
    }

    public Stream<String> getReactionIds() {
        return reactionIds.stream();
    }
}
