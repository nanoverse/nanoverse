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

package nanoverse.runtime.agent.action.displacement;

import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.layers.cell.AgentLayer;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Removes out-of-bound coordinates.
 * Created by dbborens on 10/20/2015.
 */
public class ImaginarySiteCleaner {

    private final AgentLayer layer;

    public ImaginarySiteCleaner(AgentLayer layer) {
        this.layer = layer;
    }

    /**
     * Remove all out-of-bounds agents from the system. Useful after a shoving
     * operation.
     */
    public void removeImaginary() {
        // Collect to a new list to avoid concurrent modification
        List<Coordinate> sites = layer.getViewer().getImaginarySites().collect(Collectors.toList());
        sites.stream().forEach(c -> layer.getUpdateManager().banish(c));
    }
}
