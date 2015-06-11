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

import cells.BehaviorCell;
import control.arguments.*;
import control.halt.*;
import control.identifiers.Coordinate;
import processes.*;
import processes.gillespie.GillespieState;

import java.util.*;
import java.util.stream.*;

public class ScatterClusters extends CellProcess {

    private List<Coordinate> candidates;
    private final Argument<Integer> neighborCount;
    private final CellDescriptor cellDescriptor;

    public ScatterClusters(BaseProcessArguments arguments,
                           CellProcessArguments cpArguments,
                           Argument<Integer> neighborCount,
                           CellDescriptor cellDescriptor) {

        super(arguments, cpArguments);
        this.cellDescriptor = cellDescriptor;
        this.neighborCount = neighborCount;
    }

    @Override
    public void init() {
        candidates = null;
    }

    public void target(GillespieState gs) throws HaltCondition {
        // Construct initial set of candidates

        candidates = getActiveSites().stream()
                        .filter(c -> !getLayer()
                                .getViewer()
                                .isOccupied(c))
                        .collect(Collectors.toList());

        if (gs != null) {
            gs.add(this.getID(), candidates.size(), candidates.size() * 1.0D);
        }
    }

    private int getCeiling() {
        int n;

        try {
            n = getMaxTargets().next();
            if (n < 0) {
                throw new IllegalArgumentException("Scatter cluster process requires >= 0 max targets.");
            }

            return n;
        } catch (HaltCondition ex) {
            throw new RuntimeException("Unexpected halt condition", ex);
        }
    }

    public int getNeighborCount() {
        int m;

        try {
            m = neighborCount.next();
        } catch (HaltCondition ex) {
            throw new RuntimeException("Unexpected halt condition", ex);
        }

        return m;
    }

    public void fire(StepState state) throws HaltCondition {
        Collections.shuffle(candidates);
        int placed = 0;
        int n = getCeiling();
        int m = getNeighborCount();

        Iterator<Coordinate> cIter = candidates.iterator();
        BehaviorCell toPlace = cellDescriptor.next();

        while (placed < n) {
            if (!cIter.hasNext()) {
                throw new LatticeFullEvent();
            }

            // Get next candidate coordinate.
            Coordinate current = cIter.next();

            // Place cell at this site, if it is acceptable
            int needed = needed(current, toPlace, m);
            if (needed > -1) {
                placeAndColonize(current, toPlace, needed);
                placed++;
                toPlace = cellDescriptor.next();
            }
        }

    }

    private void placeAndColonize(Coordinate current, BehaviorCell toPlace, int needed) {
        try {
            getLayer().getUpdateManager().place(toPlace, current);
        } catch (HaltCondition ex) {
            throw new RuntimeException("Unexpected halt condition", ex);
        }

        List<Coordinate> vacancies = Arrays.asList(getLayer().getLookupManager().getNearestVacancies(current, 1));
        Collections.shuffle(vacancies);

        IntStream.range(0, needed).mapToObj(vacancies::get).forEach(c -> {
            int state = toPlace.getState();
            BehaviorCell clone;
            try {
                clone = toPlace.clone(state);
                getLayer().getUpdateManager().place(clone, c);
            } catch (HaltCondition ex) {
                throw new RuntimeException("Unexpected halt condition", ex);
            }
        });
    }

    /**
     * Examines a coordinate to determine whether its neighborhood has room
     * for m self-similar cells. If there is no room for enough self-similar
     * neighbors to satisfy this requirement, returns -1. Otherwise, returns
     * the number of self-similar neighbors still needed to have m self-similar
     * neighbors.
     *
     * @param current
     * @param toPlace
     * @param m
     * @return
     */
    private int needed(Coordinate current, BehaviorCell toPlace, int m) {
        // Get new cell's state.
        int state = toPlace.getState();

        // Get neighborhood state.
        int[] neighborStates = getLayer().getLookupManager().getNeighborStates(current, false);

        // Count self-similar neighbors.
        int numSelfSimilar = getMatchCount(neighborStates, state);

        // Count adjacent vacancies
        int numVacant = getMatchCount(neighborStates, 0);

        if (numSelfSimilar + numVacant < m) {
            return -1;
        } else if (numSelfSimilar >= m) {
            return 0;
        } else {
            return m - numSelfSimilar;
        }


    }

    int getMatchCount(int[] toMatch, int expected) {
        return (int) IntStream
                .range(0, toMatch.length)
                .map(i -> toMatch[i])
                .filter(neighborState -> neighborState == expected)
                .count();
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ScatterClusters scatter = (ScatterClusters) o;

        if (cellDescriptor != null ? !cellDescriptor.equals(scatter.cellDescriptor) : scatter.cellDescriptor != null)
            return false;

        if (getActiveSites() != null ? !getActiveSites().equals(scatter.getActiveSites()) : scatter.getActiveSites() != null)
            return false;

        if (getMaxTargets() != null ? !getMaxTargets().equals(scatter.getMaxTargets()) : scatter.getMaxTargets() != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return cellDescriptor != null ? cellDescriptor.hashCode() : 0;
    }
}
