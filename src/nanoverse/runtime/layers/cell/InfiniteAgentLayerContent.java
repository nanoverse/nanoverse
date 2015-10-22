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

package nanoverse.runtime.layers.cell;

import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.geometry.Geometry;
import nanoverse.runtime.structural.CanonicalAgentMap;

import java.util.HashSet;
import java.util.stream.Stream;

/**
 * Created by David B Borenstein on 4/10/14.
 */
public class InfiniteAgentLayerContent extends AgentLayerContent {

    public InfiniteAgentLayerContent(Geometry geom, AgentLayerIndices indices) {
        super(geom, indices);
    }

    @Override
    public void sanityCheck(Coordinate coord) {
    }

    @Override
    public Stream<Coordinate> getImaginarySites() {
        return getOccupiedSites().stream()
            .filter(c -> !hasCanonicalForm(c));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        return true;
    }

    @Override
    public InfiniteAgentLayerContent clone() {
        CanonicalAgentMap clonedMap = new CanonicalAgentMap(map);
        HashSet<Coordinate> clonedSites = new HashSet<>(canonicalSites);
        AgentLayerIndices clonedIndices = indices.clone(clonedMap);
        InfiniteAgentLayerContent clone = new InfiniteAgentLayerContent(geom, clonedIndices);
        clone.map = clonedMap;
        clone.canonicalSites = clonedSites;
        return clone;
    }
}
