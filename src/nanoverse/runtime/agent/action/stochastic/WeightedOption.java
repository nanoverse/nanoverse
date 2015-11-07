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

import nanoverse.runtime.agent.action.ActionDescriptor;
import nanoverse.runtime.control.arguments.ProbabilitySupplierDescriptor;
import nanoverse.runtime.structural.annotations.FactoryTarget;

/**
 * A lightweight tuple consisting of an action descriptor and a probability
 * weighting.
 * <p>
 * Created by dbborens on 7/23/2015.
 */
public class WeightedOption {

    private final ProbabilitySupplierDescriptor weight;
    private final ActionDescriptor action;

    @FactoryTarget
    public WeightedOption(ProbabilitySupplierDescriptor weight, ActionDescriptor action) {
        this.weight = weight;
        this.action = action;
    }

    public ProbabilitySupplierDescriptor getWeight() {
        return weight;
    }

    public ActionDescriptor getAction() {
        return action;
    }
}
