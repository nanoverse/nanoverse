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

package nanoverse.compiler.pipeline.instantiate.loader.agent.action;

import nanoverse.compiler.pipeline.instantiate.helpers.LoadHelper;
import nanoverse.compiler.pipeline.instantiate.loader.agent.action.stochastic.*;
import nanoverse.compiler.pipeline.translate.nodes.*;
import nanoverse.runtime.agent.action.StochasticChoice;
import nanoverse.runtime.agent.action.stochastic.*;
import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.layers.LayerManager;

import java.util.Random;

/**
 * Created by dbborens on 8/25/2015.
 */
public class StochasticChoiceInterpolator {

    private final LoadHelper load;
    private final StochasticChoiceDefaults defaults;

    public StochasticChoiceInterpolator() {
        load = new LoadHelper();
        defaults = new StochasticChoiceDefaults();
    }

    public StochasticChoiceInterpolator(LoadHelper load, StochasticChoiceDefaults defaults) {
        this.load = load;
        this.defaults = defaults;
    }

    public DynamicActionRangeMapDescriptor options(MapObjectNode node, LayerManager lm, GeneralParameters p, boolean normalized) {
        if (normalized) {
            return normalizedCase(node, lm, p);
        } else {
            return weightedCase(node, lm, p);
        }
    }

    private NormalizedDynamicActionRangeMapDescriptor normalizedCase(MapObjectNode node, LayerManager lm, GeneralParameters p) {
        NormalizedDynamicActionRangeMapLoader loader = (NormalizedDynamicActionRangeMapLoader) load.getLoader(node, "options", true);
        ListObjectNode cNode = (ListObjectNode) node.getMember("options");
        return loader.instantiate(cNode, lm, p);
    }

    private DynamicActionRangeMapDescriptor weightedCase(MapObjectNode node, LayerManager lm, GeneralParameters p) {
        DynamicActionRangeMapLoader loader = (DynamicActionRangeMapLoader) load.getLoader(node, "options", true);
        ListObjectNode cNode = (ListObjectNode) node.getMember("options");
        return loader.instantiate(cNode, lm, p);
    }

    public Boolean normalized(MapObjectNode node, Random random) {
        return load.aBoolean(node, "normalized", random, defaults.normalized());
    }
}
