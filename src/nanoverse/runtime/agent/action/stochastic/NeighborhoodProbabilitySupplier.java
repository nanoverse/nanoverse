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

package nanoverse.runtime.agent.action.stochastic;

import nanoverse.runtime.agent.Agent;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.layers.cell.AgentLayer;

/**
 * This is part of a cloodge to make it so that StochasticChoice can read the state
 * of solute fields. As such, it is not very carefully written. This will be replaced
 * when more flexible state objects are made available.
 * <p>
 * Created by dbborens on 1/9/15.
 */
public class NeighborhoodProbabilitySupplier extends ProbabilitySupplier {

    private final double coefficient;
    private final double offset;
    private NeighborhoodCountHelper helper;

    public NeighborhoodProbabilitySupplier(AgentLayer layer, Agent agent, double coefficient, double offset) {
        this.coefficient = coefficient;
        this.offset = offset;
        helper = new NeighborhoodCountHelper(layer, agent);
    }

    public NeighborhoodProbabilitySupplier(NeighborhoodCountHelper helper, double coefficient, double offset) {
        this.helper = helper;
        this.coefficient = coefficient;
        this.offset = offset;
    }

    @Override
    public NeighborhoodProbabilitySupplier clone(Agent child) {
        NeighborhoodCountHelper helperClone = helper.clone(child);
        return new NeighborhoodProbabilitySupplier(helperClone, coefficient, offset);
    }

    @Override
    public Double get() {
        double neighbors = helper.getNeighborCount();
        return (coefficient * neighbors) + offset;
    }
}
