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

package nanoverse.runtime.control.arguments;

import nanoverse.runtime.agent.Agent;
import nanoverse.runtime.agent.action.stochastic.ProbabilitySupplier;

import java.util.function.Function;

/**
 * Created by dbborens on 1/26/15.
 */
public class ProbabilitySupplierDescriptor<T extends ProbabilitySupplier> {

    private Function<Agent, T> constructor;

    protected ProbabilitySupplierDescriptor() {
    }

    public ProbabilitySupplierDescriptor(Function<Agent, T> constructor) {
        this.constructor = constructor;
    }

    protected void setConstructor(Function<Agent, T> constructor) {
        this.constructor = constructor;
    }

    public T instantiate(Agent cell) {
        return constructor.apply(cell);
    }
}
