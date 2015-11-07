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
import nanoverse.compiler.pipeline.instantiate.loader.agent.action.stochastic.DynamicActionRangeMapLoader;
import nanoverse.compiler.pipeline.instantiate.loader.agent.action.stochastic.NormalizedDynamicActionRangeMapLoader;
import nanoverse.compiler.pipeline.translate.nodes.ListObjectNode;
import nanoverse.compiler.pipeline.translate.nodes.MapObjectNode;
import nanoverse.runtime.agent.action.stochastic.DynamicActionRangeMapDescriptor;
import nanoverse.runtime.agent.action.stochastic.NormalizedDynamicActionRangeMapDescriptor;
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
        // TODO
        // The loader is supposed to be specified by the symbol table. If this code runs into
        // trouble, the first thing to do is:
        //
        // (1) Create a DynamicActionRangeMapLoadAdapter class, which resolves which loader to use and
        //     delegates to that, based on the value of the "normalized" parameter.
        //
        // (2) Set the Loader property of the "options" argument on StochasticChoice's IST to this
        //     new adapter.
        //
        // (3) Replace the normalizedCase and weightedCase here with a call to the adapter.
        NormalizedDynamicActionRangeMapLoader loader = new NormalizedDynamicActionRangeMapLoader();
        ListObjectNode cNode = (ListObjectNode) node.getMember("options");
        return loader.instantiate(cNode, lm, p);
    }

    private DynamicActionRangeMapDescriptor weightedCase(MapObjectNode node, LayerManager lm, GeneralParameters p) {
        DynamicActionRangeMapLoader loader = (DynamicActionRangeMapLoader) load.getLoader(node, "options", true);
        ListObjectNode cNode = (ListObjectNode) node.getMember("options");
        return loader.instantiate(cNode, lm, p);
    }

    public Boolean normalized(MapObjectNode node, Random random) {
        return load.aBoolean(node, "normalized", random, defaults::normalized);
    }
}
