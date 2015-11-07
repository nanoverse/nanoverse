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
import nanoverse.runtime.control.arguments.ProbabilitySupplierDescriptor;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.layers.LayerManager;
import nanoverse.runtime.layers.cell.AgentLayer;
import nanoverse.runtime.structural.annotations.FactoryTarget;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Created by dbborens on 8/25/2015.
 */
public class NeighborhoodProbabilitySupplierDescriptor extends ProbabilitySupplierDescriptor<NeighborhoodProbabilitySupplier> {

    @FactoryTarget(displayName = "NeighborhoodProbabilitySupplier")
    public NeighborhoodProbabilitySupplierDescriptor(double coefficient,
                                                     double offset,
                                                     AgentLayer layer) {

        Function<Agent, NeighborhoodProbabilitySupplier> constructor = cell -> new NeighborhoodProbabilitySupplier(layer,
            cell, coefficient, offset);

        super.setConstructor(constructor);
    }
}
