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
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.layers.cell.AgentLayer;
import nanoverse.runtime.structural.annotations.FactoryTarget;

/**
 * Created by dbborens on 6/13/2015.
 */
public class ContactClustersHelper extends SeparationStrategyManager {

    @FactoryTarget
    public ContactClustersHelper(AgentLayer layer) {
        super(layer);
    }

    /**
     * Place a cell if the candidate site is a valid site for placement.
     * Returns total number of agents placed.
     *
     * @param candidate
     * @param toPlace
     * @param m
     * @return
     */
    @Override
    public int attemptPlacement(Coordinate candidate, Agent toPlace, int m) {
        if (layer.getViewer().isOccupied(candidate)) {
            return 0;
        }
        int needed = needed(candidate, toPlace, m);
        if (needed > -1) {
            placeAndColonize(candidate, toPlace, needed);
            return needed + 1;
        }
        return 0;
    }

}
