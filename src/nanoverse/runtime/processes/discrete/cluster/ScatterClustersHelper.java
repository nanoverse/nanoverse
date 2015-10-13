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

package nanoverse.runtime.processes.discrete.cluster;

import nanoverse.runtime.agent.BehaviorCell;
import nanoverse.runtime.control.halt.HaltCondition;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.layers.cell.CellLayer;

import java.util.*;
import java.util.stream.IntStream;

/**
 * Created by dbborens on 6/14/2015.
 */
public abstract class ScatterClustersHelper {
    protected final CellLayer layer;

    public ScatterClustersHelper(CellLayer layer) {
        this.layer = layer;
    }

    public abstract int attemptPlacement(Coordinate candidate, BehaviorCell toPlace, int m);

    /**
     * Examines a coordinate to determine whether its neighborhood has room
     * for m self-similar nanoverse.runtime.cells. If there is no room for enough self-similar
     * neighbors to satisfy this requirement, returns -1. Otherwise, returns
     * the number of self-similar neighbors still needed to have m self-similar
     * neighbors.
     *
     * @param current
     * @param toPlace
     * @param m
     * @return
     */
    protected int needed(Coordinate current, BehaviorCell toPlace, int m) {
        // Get new cell's state.
        int state = toPlace.getState();

        // Get neighborhood state.
        int[] neighborStates = layer.getLookupManager().getNeighborStates(current, false);

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

    protected int getMatchCount(int[] toMatch, int expected) {
        return (int) IntStream
            .range(0, toMatch.length)
            .map(i -> toMatch[i])
            .filter(neighborState -> neighborState == expected)
            .count();
    }

    protected void placeAndColonize(Coordinate current, BehaviorCell toPlace, int needed) {
        try {
            layer.getUpdateManager().place(toPlace, current);
        } catch (HaltCondition ex) {
            throw new RuntimeException("Unexpected halt condition", ex);
        }

        List<Coordinate> vacancies = Arrays.asList(layer.getLookupManager().getNearestVacancies(current, 1));
        Collections.shuffle(vacancies);

        IntStream.range(0, needed).mapToObj(vacancies::get).forEach(c -> {
            int state = toPlace.getState();
            BehaviorCell clone;
            try {
                clone = toPlace.clone(state);
                layer.getUpdateManager().place(clone, c);
            } catch (HaltCondition ex) {
                throw new RuntimeException("Unexpected halt condition", ex);
            }
        });
    }

    protected boolean hasSelfNeighbors(Coordinate candidate, BehaviorCell toPlace) {
        // Get new cell's state.
        int state = toPlace.getState();

        // Get neighborhood state.
        int[] neighborStates = layer.getLookupManager().getNeighborStates(candidate, false);

        // Count self-similar neighbors.
        int numSelfSimilar = getMatchCount(neighborStates, state);

        return (numSelfSimilar > 0);
    }
}
