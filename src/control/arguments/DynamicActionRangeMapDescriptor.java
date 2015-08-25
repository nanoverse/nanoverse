/*
 * Copyright (c) 2014, 2015 David Bruce Borenstein and the
 * Trustees of Princeton University.
 *
 * This file is part of the Nanoverse simulation framework
 * (patent pending).
 *
 * This program is free software: you can redistribute it
 * and/or modify it under the terms of the GNU Affero General
 * Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * This program is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 * PURPOSE.  See the GNU Affero General Public License for
 * more details.
 *
 * You should have received a copy of the GNU Affero General
 * Public License along with this program.  If not, see
 * <http://www.gnu.org/licenses/>.
 */

package control.arguments;

import agent.action.*;
import agent.action.stochastic.*;
import cells.BehaviorCell;
import layers.LayerManager;
import structural.annotations.FactoryTarget;

import java.util.*;
import java.util.function.Function;
import java.util.stream.*;

/**
 * Created by dbborens on 1/26/15.
 */
@Deprecated
public class DynamicActionRangeMapDescriptor {

    private final Function<BehaviorCell, DynamicActionRangeMap> constructor;

    @FactoryTarget(displayName = "DynamicActionRangeMap")
    public DynamicActionRangeMapDescriptor(Stream<WeightedOption> options, LayerManager layerManager) {
        List<WeightedOption> optionList = options.collect(Collectors.toList());

        Function<BehaviorCell, DynamicActionRangeMap> constructor = cell -> {
            Map<Action, ProbabilitySupplier> map = optionList.stream()
                .collect(Collectors.toMap(
                    option -> option.getAction().instantiate(cell),
                    option -> option.getWeight().instantiate(cell)
                ));

            return new DynamicActionRangeMap(map, layerManager);
        };

        this.constructor = constructor;
    }

    public DynamicActionRangeMapDescriptor(Function<BehaviorCell, DynamicActionRangeMap> constructor) {
        this.constructor = constructor;
    }

    public DynamicActionRangeMap instantiate(BehaviorCell cell) {
        return constructor.apply(cell);
    }
}
