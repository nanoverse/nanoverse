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

package nanoverse.runtime.processes.discrete.filter;

import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.layers.cell.*;
import nanoverse.runtime.structural.annotations.FactoryTarget;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by dbborens on 5/5/14.
 */
public class AgentNameFilter extends Filter {

    private final AgentLayer layer;
    private final String toChoose;

    @FactoryTarget
    public AgentNameFilter(AgentLayer layer, String toChoose) {
        this.toChoose = toChoose;
        this.layer = layer;
    }

    @Override
    public List<Coordinate> apply(List<Coordinate> toFilter) {

        return toFilter.stream()
            .filter(this::isOccupied)
            .filter(this::hasExpectedName)
            .collect(Collectors.toList());
    }

    private boolean hasExpectedName(Coordinate coordinate) {
        String name = layer.getViewer().getName(coordinate);
        return name.equals(toChoose);
    }

    private boolean isOccupied(Coordinate coordinate) {
        AgentLayerViewer viewer = layer.getViewer();
        return viewer.isOccupied(coordinate);
    }
}
