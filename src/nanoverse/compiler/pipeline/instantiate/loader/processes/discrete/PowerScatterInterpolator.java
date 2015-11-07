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

package nanoverse.compiler.pipeline.instantiate.loader.processes.discrete;

import nanoverse.compiler.pipeline.instantiate.helpers.LoadHelper;
import nanoverse.compiler.pipeline.instantiate.loader.agent.AgentDescriptorLoader;
import nanoverse.compiler.pipeline.instantiate.loader.processes.BaseProcessArgumentsLoader;
import nanoverse.compiler.pipeline.instantiate.loader.processes.discrete.cluster.ScatterClustersHelperLoader;
import nanoverse.compiler.pipeline.translate.nodes.MapObjectNode;
import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.control.arguments.AgentDescriptor;
import nanoverse.runtime.layers.LayerManager;
import nanoverse.runtime.processes.discrete.cluster.SeparationStrategyManager;

/**
 * Created by dbborens on 8/26/2015.
 */
public class PowerScatterInterpolator extends DiscreteProcessInterpolator {
    private final PowerScatterDefaults defaults;

    public PowerScatterInterpolator() {
        super();
        defaults = new PowerScatterDefaults();
    }

    public PowerScatterInterpolator(LoadHelper load,
                                    BaseProcessArgumentsLoader bpaLoader,
                                    DiscreteProcessArgumentsLoader dpaLoader,
                                    PowerScatterDefaults defaults) {
        super(load, bpaLoader, dpaLoader);
        this.defaults = defaults;
    }

    public SeparationStrategyManager helper(MapObjectNode node, LayerManager lm, GeneralParameters p) {
        ScatterClustersHelperLoader loader = (ScatterClustersHelperLoader) load.getLoader(node, "separation", false);

        if (loader == null) {
            return defaults.helper(lm, p);
        }

        MapObjectNode cNode = (MapObjectNode) node.getMember("separation");
        return loader.instantiate(cNode, lm, p);
    }

    public AgentDescriptor description(MapObjectNode node, LayerManager lm, GeneralParameters p) {
        AgentDescriptorLoader loader = (AgentDescriptorLoader) load.getLoader(node, "description", false);

        if (loader == null) {
            return defaults.description(lm, p);
        }

        MapObjectNode cNode = (MapObjectNode) node.getMember("description");
        return loader.instantiate(cNode, lm, p);
    }
}
