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

import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.geometry.Geometry;
import nanoverse.runtime.structural.CanonicalAgentMap;

import java.util.HashSet;
import java.util.stream.Stream;

/**
 * Created by David B Borenstein on 4/10/14.
 */
public class FiniteAgentLayerContent extends AgentLayerContent {
    public FiniteAgentLayerContent(Geometry geom, AgentLayerIndices indices) {
        super(geom, indices);
    }

    @Override
    public void sanityCheck(Coordinate coord) {

        // Otherwise, it had better be in the coordinate system.
        if (!hasCanonicalForm(coord)) {
            StringBuilder ss = new StringBuilder();
            ss.append("Consistency failure: coordinate ");
            ss.append(coord.stringForm());
            ss.append(" does not exist in this nanoverse.runtime.geometry.\n");
            String str = ss.toString();
            throw new IllegalStateException(str);
        }
    }

    @Override
    public Stream<Coordinate> getImaginarySites() {
        return Stream.empty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        return true;
    }

    @Override
    public FiniteAgentLayerContent clone() {
        CanonicalAgentMap clonedMap = new CanonicalAgentMap(map);
        HashSet<Coordinate> clonedSites = new HashSet<>(canonicalSites);
        AgentLayerIndices clonedIndices = indices.clone(clonedMap);
        FiniteAgentLayerContent clone = new FiniteAgentLayerContent(geom, clonedIndices);
        clone.map = clonedMap;
        clone.canonicalSites = clonedSites;
        return clone;
    }
}
