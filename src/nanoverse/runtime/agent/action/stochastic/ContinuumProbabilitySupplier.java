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

import java.util.function.Function;
/**
 * This is part of a cloodge to make it so that StochasticChoice can read the state
 * of solute fields. As such, it is not very carefully written. This will be replaced
 * when more flexible state objects are made available.
 * <p>
 * Created by dbborens on 1/9/15.
 */
public class ContinuumProbabilitySupplier extends ProbabilitySupplier {

    private final double coefficient;
    private final double offset;
    private final Agent cell;
    private final Function<Agent, Double> valueLookup;

    public ContinuumProbabilitySupplier(Function<Agent, Double> valueLookup, Agent cell, double coefficient, double offset) {
        this.coefficient = coefficient;
        this.offset = offset;
        this.cell = cell;
        this.valueLookup = valueLookup;
    }

    @Override
    public ContinuumProbabilitySupplier clone(Agent child) {
        return new ContinuumProbabilitySupplier(valueLookup, child, coefficient, offset);
    }

    @Override
    public Double get() {
        double value = valueLookup.apply(cell);
        double probability = (coefficient * value) + offset;
        return probability;
    }
}
