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

package nanoverse.compiler.pipeline.instantiate.loader.agent.action.stochastic;

import nanoverse.compiler.pipeline.instantiate.helpers.LoadHelper;
import nanoverse.compiler.pipeline.instantiate.loader.agent.action.*;
import nanoverse.compiler.pipeline.translate.nodes.*;
import nanoverse.runtime.agent.action.ActionDescriptor;
import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.control.arguments.ProbabilitySupplierDescriptor;
import nanoverse.runtime.layers.LayerManager;

/**
 * Created by dbborens on 8/25/2015.
 */
public class WeightedOptionInterpolator {

    private final LoadHelper load;
    private final FlexibleActionLoader actionLoader;

    public WeightedOptionInterpolator() {
        load = new LoadHelper();
        actionLoader = new FlexibleActionLoader();
    }

    public WeightedOptionInterpolator(LoadHelper load, FlexibleActionLoader actionLoader) {
        this.load = load;
        this.actionLoader = actionLoader;
    }

    public ActionDescriptor action(MapObjectNode node, LayerManager lm, GeneralParameters p) {
        ObjectNode cNode = node.getMember("action");
        return actionLoader.load(cNode, lm, p);
    }

    public ProbabilitySupplierDescriptor weight(MapObjectNode node, LayerManager lm, GeneralParameters p) {
        ProbabilitySupplierLoader loader = (ProbabilitySupplierLoader) load.getLoader(node, "weight", true);
        MapObjectNode cNode = (MapObjectNode) node.getMember("weight");

        return loader.instantiate(cNode, lm, p);
    }
}
