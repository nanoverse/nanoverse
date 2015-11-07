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
package nanoverse.compiler.pipeline.instantiate.factory.processes.discrete;

import nanoverse.compiler.pipeline.instantiate.factory.Factory;
import nanoverse.runtime.control.arguments.IntegerArgument;
import nanoverse.runtime.geometry.set.CoordinateSet;
import nanoverse.runtime.processes.discrete.AgentProcessArguments;

public class DiscreteProcessArgumentsFactory implements Factory<AgentProcessArguments> {

    private final AgentProcessArgumentsFactoryHelper helper;

    private CoordinateSet activeSites;
    private IntegerArgument maxTargets;

    public DiscreteProcessArgumentsFactory() {
        helper = new AgentProcessArgumentsFactoryHelper();
    }

    public DiscreteProcessArgumentsFactory(AgentProcessArgumentsFactoryHelper helper) {
        this.helper = helper;
    }

    public void setActiveSites(CoordinateSet activeSites) {
        this.activeSites = activeSites;
    }

    public void setMaxTargets(IntegerArgument maxTargets) {
        this.maxTargets = maxTargets;
    }

    @Override
    public AgentProcessArguments build() {
        return helper.build(activeSites, maxTargets);
    }
}