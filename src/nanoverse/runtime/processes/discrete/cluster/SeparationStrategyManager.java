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

import nanoverse.runtime.agent.Agent;
import nanoverse.runtime.control.halt.HaltCondition;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.layers.cell.AgentLayer;

import java.util.*;
import java.util.stream.*;

/**
 * Created by dbborens on 6/14/2015.
 */
public abstract class SeparationStrategyManager {
    protected final AgentLayer layer;

    public SeparationStrategyManager(AgentLayer layer) {
        this.layer = layer;
    }

    public abstract int attemptPlacement(Coordinate candidate, Agent toPlace, int m);

    /**
     * Examines a coordinate to determine whether its neighborhood has room
     * for m self-similar agents. If there is no room for enough self-similar
     * neighbors to satisfy this requirement, returns -1. Otherwise, returns
     * the number of self-similar neighbors still needed to have m self-similar
     * neighbors.
     *
     * @param current
     * @param toPlace
     * @param m
     * @return
     */
    protected int needed(Coordinate current, Agent toPlace, int m) {
        // Get new cell's name.
        String name = toPlace.getName();

        // Get neighborhood state.
        List<String> neighborNames = layer.getLookupManager()
            .getNeighborNames(current, false)
            .collect(Collectors.toList());

        // Count self-similar neighbors.
        int numSelfSimilar = getMatchCount(neighborNames.stream(), name);

        // Count adjacent vacancies
        int numVacant = getMatchCount(neighborNames.stream(), null);

        if (numSelfSimilar + numVacant < m) {
            return -1;
        } else if (numSelfSimilar >= m) {
            return 0;
        } else {
            return m - numSelfSimilar;
        }
    }

    protected int getMatchCount(Stream<String> toMatch, String expected) {
        int ret = (int) toMatch
            .filter(name -> name.equals(expected))
            .count();

        return ret;
    }

    protected void placeAndColonize(Coordinate current, Agent toPlace, int needed) {
        try {
            layer.getUpdateManager().place(toPlace, current);
        } catch (HaltCondition ex) {
            throw new RuntimeException("Unexpected halt condition", ex);
        }

        List<Coordinate> vacancies = Arrays.asList(layer.getLookupManager().getNearestVacancies(current, 1));
        Collections.shuffle(vacancies);

        IntStream.range(0, needed).mapToObj(vacancies::get).forEach(c -> {
            Agent clone;
            try {
                clone = toPlace.copy();
                layer.getUpdateManager().place(clone, c);
            } catch (HaltCondition ex) {
                throw new RuntimeException("Unexpected halt condition", ex);
            }
        });
    }

    protected boolean hasSelfNeighbors(Coordinate candidate, Agent toPlace) {
        // Get new cell's state.
        String name = toPlace.getName();

        // Get neighborhood state.
        Stream<String> neighborNames = layer.getLookupManager().getNeighborNames(candidate, false);

        // Count self-similar neighbors.
        int numSelfSimilar = getMatchCount(neighborNames, name);

        return (numSelfSimilar > 0);
    }
}
