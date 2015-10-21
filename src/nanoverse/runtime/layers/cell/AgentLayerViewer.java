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

import java.util.*;

/**
 * @author David Bruce Borenstein
 * @tested AgentLayerViewerTest
 */
public class AgentLayerViewer {

    private AgentLayerContent content;

    public AgentLayerViewer(AgentLayerContent content) {
        this.content = content;
    }

    /**
     * Returns a vector containing the canonical coordinate of each
     * active site on the lattice.
     *
     * @return
     */
    public HashSet<Coordinate> getOccupiedSites() {
        // Construct a copy of internal state
        HashSet<Coordinate> res = new HashSet<>(content.getOccupiedSites());

        // Return it
        return res;
    }

    public String[] getNames() {
        return content.getNames();
    }

    public NameMapViewer getStateMapViewer() {
        return new NameMapViewer(content.getNameMap());
    }

    public boolean exists(Agent agent) {
        return content.isIndexed(agent);
    }

    public Set<Coordinate> getImaginarySites() {
        return content.getImaginarySites();
    }

    /**
     * Returns 0 for vacant agents; otherwise, returns the cell's state.
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
        return content.getOccupiedSites().contains(c);
    }
}
