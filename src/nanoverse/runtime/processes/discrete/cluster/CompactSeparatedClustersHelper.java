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

package nanoverse.runtime.processes.discrete.cluster;

import nanoverse.runtime.agent.Agent;
import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.control.halt.HaltCondition;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.geometry.Geometry;
import nanoverse.runtime.layers.cell.AgentLayer;
import nanoverse.runtime.structural.annotations.FactoryTarget;

import java.util.stream.Stream;

/**
 * Created by dbborens on 6/14/2015.
 */
public class CompactSeparatedClustersHelper extends SeparationStrategyManager {

    private final GeneralParameters p;

    @FactoryTarget
    public CompactSeparatedClustersHelper(AgentLayer layer, GeneralParameters p) {
        super(layer);
        this.p = p;
    }

    @Override
    public int attemptPlacement(Coordinate candidate, Agent toPlace, int m) {
        if (layer.getViewer().isOccupied(candidate)) {
            return 0;
        }

        if (hasAnyNeighbors(candidate)) {
            return 0;
        }

        if (neighborsHaveAnyNeighbors(candidate)) {
            return 0;
        }

        doAttempt(candidate, toPlace, m);

        return m + 1;
    }

    /**
     * If m=1, just place the nanoverse.runtime.agent at the specified candidate location.
     * Otherwise, choose a random neighboring coordinate and place m-1 neighbors
     * clockwise through the neighborhood.
     */
    private void doAttempt(Coordinate candidate,
                           Agent toPlace,
                           int m) {

        place(candidate, toPlace);

        if (m == 0) {
            return;
        }

        Coordinate[] vacancies = layer.getLookupManager().getNearestVacancies(candidate, 1);
        int start = p.getRandom().nextInt(vacancies.length);

        for (int i = 0; i < m; i++) {

            int iWrapped = (i + start) % vacancies.length;
            Coordinate c = vacancies[iWrapped];
            Agent clone;

            try {
                clone = toPlace.copy();
                layer.getUpdateManager().place(clone, c);
            } catch (HaltCondition ex) {
                throw new RuntimeException("Unexpected halt condition", ex);
            }
        }
    }

    private void place(Coordinate c, Agent cell) {
        try {
            layer.getUpdateManager().place(cell, c);
        } catch (HaltCondition ex) {
            throw new RuntimeException("Unexpected halt condition", ex);
        }
    }

    private boolean neighborsHaveAnyNeighbors(Coordinate candidate) {
        Coordinate[] neighbors = layer.getGeometry().getAnnulus(candidate, 1, Geometry.APPLY_BOUNDARIES);
        for (Coordinate neighbor : neighbors) {
            if (hasAnyNeighbors(neighbor)) {
                return true;
            }
        }
        return false;
    }

    protected boolean hasAnyNeighbors(Coordinate candidate) {
        // Get neighborhood state.
        Stream<String> neighborNames = layer.getLookupManager().getNeighborNames(candidate, false);

        // Count any non-vacant neighbors.
        int numVacant = getMatchCount(neighborNames, null);

        return (numVacant < neighborNames.count());
    }
}
