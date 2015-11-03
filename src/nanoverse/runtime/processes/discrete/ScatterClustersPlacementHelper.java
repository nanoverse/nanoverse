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

package nanoverse.runtime.processes.discrete;

import nanoverse.runtime.agent.Agent;
import nanoverse.runtime.control.arguments.AgentDescriptor;
import nanoverse.runtime.control.halt.*;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.processes.discrete.cluster.SeparationStrategyManager;

import java.util.Iterator;

/**
 * Created by dbborens on 10/21/2015.
 */
public class ScatterClustersPlacementHelper {

    private final SeparationStrategyManager strategy;
    private final AgentDescriptor descriptor;

    public ScatterClustersPlacementHelper(SeparationStrategyManager strategy, AgentDescriptor descriptor) {
        this.strategy = strategy;
        this.descriptor = descriptor;
    }

    public void doPlacement(int n, int m, Iterator<Coordinate> cIter) throws HaltCondition {
        Agent toPlace = descriptor.next();

        int placed = 0;

        while (placed < n) {
            if (!cIter.hasNext()) {
                throw new LatticeFullEvent();
            }

            // Get next candidate coordinate.
            Coordinate current = cIter.next();

            // Place cell at this site, if it is acceptable
            int curPlaced = strategy.attemptPlacement(current, toPlace, m);
            if (curPlaced > 0) {
                placed += curPlaced;
                toPlace = descriptor.next();
            }
        }
    }
}
