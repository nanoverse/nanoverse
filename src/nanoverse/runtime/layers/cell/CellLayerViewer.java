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

import nanoverse.runtime.agent.AbstractAgent;
import nanoverse.runtime.control.identifiers.Coordinate;

import java.util.*;

/**
 * @author David Bruce Borenstein
 * @tested CellLayerViewerTest
 */
public class CellLayerViewer {

    private CellLayerContent content;

    public CellLayerViewer(CellLayerContent content) {
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

    /**
     * Returns a list of divisible sites on the lattice.
     *
     * @return
     */
    public HashSet<Coordinate> getDivisibleSites() {
        // Construct a copy of internal state
        HashSet<Coordinate> res = new HashSet<>(content.getDivisibleSites());

        // Return it
        return res;
    }

    public int[] getStateVector() {
        return content.getStateVector();
    }

    /**
     * Returns a vector of health values, in canonical site order.
     *
     * @return
     */
    public double[] getHealthVector() {
        return content.getHealthVector();
    }

    public StateMapViewer getStateMapViewer() {
        return new StateMapViewer(content.getStateMap());
    }

    public boolean isDivisible(Coordinate c) {
        return content.getDivisibleSites().contains(c);
    }

    public boolean exists(AbstractAgent agent) {
        return content.isIndexed(agent);
    }

    public Set<Coordinate> getImaginarySites() {
        return content.getImaginarySites();
    }

    /**
     * Returns 0 for vacant nanoverse.runtime.cells; otherwise, returns the cell's state.
     *
     * @param coord
     * @return
     */
    public int getState(Coordinate coord) {
        if (!isOccupied(coord)) {
            return 0;
        }

        return getCell(coord).getState();
    }

    public AbstractAgent getCell(Coordinate coord) {
        return content.get(coord);
    }

    public boolean isOccupied(Coordinate c) {
        return content.getOccupiedSites().contains(c);
    }
}
