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

package compiler.pipeline.instantiate.loader.processes.discrete;

import compiler.pipeline.instantiate.helpers.LoadHelper;
import compiler.pipeline.instantiate.loader.agent.AgentDescriptorLoader;
import compiler.pipeline.instantiate.loader.processes.BaseProcessArgumentsLoader;
import compiler.pipeline.instantiate.loader.processes.discrete.cluster.ScatterClustersHelperLoader;
import compiler.pipeline.translate.nodes.MapObjectNode;
import control.GeneralParameters;
import control.arguments.*;
import layers.LayerManager;
import processes.discrete.cluster.ScatterClustersHelper;

import java.util.Random;

/**
 * Created by dbborens on 8/26/2015.
 */
public class ScatterClustersInterpolator extends DiscreteProcessInterpolator {
    private final ScatterClustersDefaults defaults;

    public ScatterClustersInterpolator() {
        super();
        defaults = new ScatterClustersDefaults();
    }

    public ScatterClustersInterpolator(LoadHelper load,
                                    BaseProcessArgumentsLoader bpaLoader,
                                    DiscreteProcessArgumentsLoader dpaLoader,
                                    ScatterClustersDefaults defaults) {
        super(load, bpaLoader, dpaLoader);
        this.defaults = defaults;
    }

    public IntegerArgument neighbors(MapObjectNode node, Random random) {
        return load.anIntegerArgument(node, "neighbors", random, defaults::neighbors);
    }

    public CellDescriptor description(MapObjectNode node, LayerManager lm, GeneralParameters p) {
        AgentDescriptorLoader loader = (AgentDescriptorLoader) load.getLoader(node, "description", false);

        if (loader == null) {
            return defaults.description(lm, p);
        }

        MapObjectNode cNode = (MapObjectNode) node.getMember("description");
        return loader.instantiate(cNode, lm, p);
    }

    public ScatterClustersHelper helper(MapObjectNode node, LayerManager lm, GeneralParameters p) {
        ScatterClustersHelperLoader loader = (ScatterClustersHelperLoader) load.getLoader(node, "separation", false);

        if (loader == null) {
            return defaults.helper(lm, p);
        }

        MapObjectNode cNode = (MapObjectNode) node.getMember("separation");
        return loader.instantiate(cNode, lm, p);
    }
}
