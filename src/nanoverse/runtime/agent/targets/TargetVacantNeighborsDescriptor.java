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

package nanoverse.runtime.agent.targets;

import nanoverse.runtime.agent.Agent;
import nanoverse.runtime.layers.LayerManager;
import nanoverse.runtime.processes.discrete.filter.Filter;
import nanoverse.runtime.structural.annotations.FactoryTarget;

import java.util.Random;
import java.util.function.Function;

/**
 * Created by dbborens on 8/4/2015.
 */
public class TargetVacantNeighborsDescriptor extends TargetDescriptor<TargetVacantNeighbors> {
    private final Function<Agent, TargetVacantNeighbors> constructor;

    @FactoryTarget(displayName = "TargetVacantNeighbors")
    public TargetVacantNeighborsDescriptor(LayerManager layerManager, Filter filter, Random random) {
        constructor = cell -> new TargetVacantNeighbors(cell, layerManager, filter, random);
    }

    @Override
    protected Function<Agent, TargetVacantNeighbors> getConstructor() {
        return constructor;
    }
}
