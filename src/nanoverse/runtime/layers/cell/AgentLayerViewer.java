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

import nanoverse.runtime.agent.Agent;
import nanoverse.runtime.control.identifiers.Coordinate;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * @author David Bruce Borenstein
 * @tested AgentLayerViewerTest
 */
public class AgentLayerViewer {

    private final AgentLayerContent content;

    public AgentLayerViewer(AgentLayerContent content) {
        this.content = content;
    }

    /**
     * Returns a vector containing the canonical coordinate of each
     * active site on the lattice.
     *
     * @return
     */
    public Stream<Coordinate> getOccupiedSites() {
        return content.getOccupiedSites().stream();
    }

    public NameMapViewer getNameMapViewer() {
        return content.getNameMap();
    }

    public boolean exists(Agent agent) {
        return content.isIndexed(agent);
    }

    public Stream<Coordinate> getImaginarySites() {
        return content.getImaginarySites();
    }

    /**
     * Returns null for vacant agents; otherwise, returns the cell's name.
     *
     * @param coord
     * @return
     */
    public String getName(Coordinate coord) {
        if (!isOccupied(coord)) {
            return null;
        }

        return getAgent(coord).getName();
    }

    public Agent getAgent(Coordinate coord) {
        return content.get(coord);
    }

    public boolean isOccupied(Coordinate c) {
        return content.isOccupied(c);
    }

    public Stream<String> getNames() {
        String[] nameArr = content.getNames();
        return Arrays.asList(nameArr).stream();
    }
}
