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
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.geometry.Geometry;
import nanoverse.runtime.layers.cell.AgentLayer;
import nanoverse.runtime.structural.annotations.FactoryTarget;

/**
 * Created by dbborens on 6/14/2015.
 */
public class StrictSeparationClusterHelper extends ScatterClustersHelper {

    @FactoryTarget
    public StrictSeparationClusterHelper(AgentLayer layer) {
        super(layer);
    }

    @Override
    public int attemptPlacement(Coordinate candidate, Agent toPlace, int m) {
        if (layer.getViewer().isOccupied(candidate)) {
            return 0;
        }

        if (hasSelfNeighbors(candidate, toPlace)) {
            return 0;
        }

        if (neighborsHaveSelfNeighbors(candidate, toPlace)) {
            return 0;
        }

        int needed = needed(candidate, toPlace, m);
        if (needed > -1) {
            placeAndColonize(candidate, toPlace, needed);
            return needed + 1;
        }
        return 0;
    }

    private boolean neighborsHaveSelfNeighbors(Coordinate candidate, Agent toPlace) {
        Coordinate[] neighbors = layer.getGeometry().getAnnulus(candidate, 1, Geometry.APPLY_BOUNDARIES);
        for (Coordinate neighbor : neighbors) {
            if (hasSelfNeighbors(neighbor, toPlace)) {
                return true;
            }
        }
        return false;
    }
}
