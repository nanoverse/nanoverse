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

package processes.discrete;

import control.halt.HaltCondition;
import control.identifiers.Coordinate;
import geometry.Geometry;
import processes.BaseProcessArguments;
import processes.MaxTargetHelper;
import processes.StepState;
import processes.gillespie.GillespieState;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Neighbor swap -- switches a randomly chosen cell with one of
 * its neighbors.
 *
 * @author dbborens
 */
public class OccupiedNeighborSwap extends CellProcess {

    private List<Object> candidates;
    private Geometry geom;

    public OccupiedNeighborSwap(BaseProcessArguments arguments, CellProcessArguments cpArguments) {

        super(arguments, cpArguments);
        geom = getLayer().getGeometry();
    }

    @Override
    public void init() {
        candidates = null;
    }

    @Override
    public void fire(StepState state) throws HaltCondition {

        if (candidates == null) {
            throw new IllegalStateException("Attempted to call fire() before calling target().");
        }

        if (candidates.size() == 0) {
            return;
        }

        //System.out.println("In NeighborSwap::iterate().");

        Object[] targets = selectTargets();

        if (targets.length == 0) {
            return;
        }

        for (Object tObj : targets) {
            SwapTuple target = (SwapTuple) tObj;
            System.out.println("Swapping" + target.p + " with " + target.q);
            getLayer().getUpdateManager().swap(target.p, target.q);
        }
        this.candidates = null;
    }

    private Object[] selectTargets() throws HaltCondition {

        Object[] selectedCoords = MaxTargetHelper.respectMaxTargets(candidates, getMaxTargets().next(), getGeneralParameters().getRandom());


        return selectedCoords;
    }

    @Override
    public void target(GillespieState gs) throws HaltCondition {

        // Create an ArrayList of SwapTuples
        candidates = new ArrayList<>();

        // Get a list of occupied sites
        Set<Coordinate> coords = getLayer().getViewer().getOccupiedSites();

        // For each occupied site...
        for (Coordinate coord : coords) {

            // See how many occupied neighbors it has
            Coordinate[] neighbors = geom.getNeighbors(coord, Geometry.APPLY_BOUNDARIES);

            // Add each possible swap as a candidate
            for (Coordinate neighbor : neighbors) {
                if (getLayer().getViewer().isOccupied(neighbor)) {
                    SwapTuple sw = new SwapTuple(coord, neighbor);
                    candidates.add(sw);
                }
            }

        }

        // Weight is defined in terms of number of swappable cells,
        // whereas event count is defined in terms of number of possible
        // swaps. Since every swap can occur in one of two directions,
        // we halve the number of swaps.
        if (gs != null) {
            gs.add(getID(), candidates.size() / 2, coords.size() * 1.0D);
        }
    }

    /**
     * Convenience class for pairs of coordinates to swap.
     */
    private class SwapTuple {
        public Coordinate p;
        public Coordinate q;

        public SwapTuple(Coordinate p, Coordinate q) {
            this.p = p;
            this.q = q;
        }
    }
}
