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

import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.layers.continuum.*;

import java.util.function.Supplier;

/**
 * Created by dbborens on 9/25/2015.
 */
public class RelationshipResolver {

    private final Supplier<Coordinate> locator;

    public RelationshipResolver(Supplier<Coordinate> locator) {
        this.locator = locator;
    }

    public Supplier<RelationshipTuple> resolve(Reaction reaction) {
        Supplier<RelationshipTuple> supplier = () -> getRelationshipTuple(reaction);
        return supplier;
    }

    private RelationshipTuple getRelationshipTuple(Reaction reaction) {
        Coordinate c = locator.get();
        return new RelationshipTuple(c, reaction);
    }
}
