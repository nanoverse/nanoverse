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
import nanoverse.runtime.agent.action.*;
import nanoverse.runtime.layers.LayerManager;
import nanoverse.runtime.structural.annotations.FactoryTarget;

import java.util.*;
import java.util.function.Function;
import java.util.stream.*;

/**
 * Created by dbborens on 1/26/15.
 */
public class WeightedDynamicActionRangeMapDescriptor implements DynamicActionRangeMapDescriptor {

    private final Function<Agent, DynamicActionRangeMap> constructor;

    @FactoryTarget(displayName = "DynamicActionRangeMap")
    public WeightedDynamicActionRangeMapDescriptor(Stream<WeightedOption> options, LayerManager layerManager) {
        List<WeightedOption> optionList = options.collect(Collectors.toList());

        Function<Agent, DynamicActionRangeMap> constructor = cell -> {
            Map<Action, ProbabilitySupplier> map = optionList.stream()
                .collect(Collectors.toMap(
                    option -> option.getAction().instantiate(cell),
                    option -> option.getWeight().instantiate(cell)
                ));

            return new DynamicActionRangeMap(map, layerManager);
        };

        this.constructor = constructor;
    }

    public WeightedDynamicActionRangeMapDescriptor(Function<Agent, DynamicActionRangeMap> constructor) {
        this.constructor = constructor;
    }

    @Override
    public DynamicActionRangeMap instantiate(Agent cell) {
        return constructor.apply(cell);
    }
}
