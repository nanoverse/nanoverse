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

import java.util.*;

/**
 * Created by dbborens on 4/27/14.
 */
public class DynamicActionRangeMap {

    private final Map<Action, ProbabilitySupplier> functionMap;
    private final LayerManager layerManager;

    protected ActionRangeMap valueMap;

    public DynamicActionRangeMap(Map<Action, ProbabilitySupplier> functionMap,
                                 LayerManager layerManager) {

        this.functionMap = functionMap;
        this.layerManager = layerManager;
    }

    public DynamicActionRangeMap(LayerManager layerManager) {
        functionMap = new HashMap<>();
        this.layerManager = layerManager;
    }

    public void refresh() {
        valueMap = new ActionRangeMap(functionMap.size());
        functionMap.forEach((action, supplier) -> {
            double value = supplier.get();
            valueMap.add(action, value);
        });
    }

    public Action selectTarget(double x) {
        return valueMap.selectTarget(x);
    }

    public double getTotalWeight() {
        return valueMap.getTotalWeight();
    }

    public DynamicActionRangeMap clone(Agent child) {
        DynamicActionRangeMap cloned = new DynamicActionRangeMap(layerManager);

        functionMap.forEach((action, supplier) -> {
            Action clonedKey = action.copy(child);
            ProbabilitySupplier clonedValue = supplier.clone(child);
            cloned.add(clonedKey, clonedValue);
        });

        return cloned;
    }

    public void add(Action action, ProbabilitySupplier supplier) {
        functionMap.put(action, supplier);
    }
}
