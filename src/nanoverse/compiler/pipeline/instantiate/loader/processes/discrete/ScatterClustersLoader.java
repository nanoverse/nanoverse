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

package nanoverse.compiler.pipeline.instantiate.loader.processes.discrete;

import nanoverse.compiler.pipeline.instantiate.factory.processes.discrete.ScatterClustersFactory;
import nanoverse.compiler.pipeline.instantiate.loader.processes.ProcessLoader;
import nanoverse.compiler.pipeline.translate.nodes.MapObjectNode;
import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.control.arguments.*;
import nanoverse.runtime.layers.LayerManager;
import nanoverse.runtime.processes.BaseProcessArguments;
import nanoverse.runtime.processes.discrete.*;
import nanoverse.runtime.processes.discrete.cluster.ScatterClustersHelper;

/**
 * Created by dbborens on 8/3/2015.
 */
public class ScatterClustersLoader extends ProcessLoader<ScatterClusters> {
    private final ScatterClustersFactory factory;
    private final ScatterClustersInterpolator interpolator;

    public ScatterClustersLoader() {
        factory = new ScatterClustersFactory();
        interpolator = new ScatterClustersInterpolator();
    }

    public ScatterClustersLoader(ScatterClustersFactory factory,
                                 ScatterClustersInterpolator interpolator) {
        this.factory = factory;
        this.interpolator = interpolator;
    }

    @Override
    public ScatterClusters instantiate(MapObjectNode node, LayerManager lm, GeneralParameters p) {
        BaseProcessArguments arguments = interpolator.arguments(node, lm, p);
        factory.setArguments(arguments);

        AgentProcessArguments cpArguments = interpolator.cpArguments(node, lm, p);
        factory.setCpArguments(cpArguments);

        AgentDescriptor description = interpolator.description(node, lm, p);
        factory.setAgentDescriptor(description);

        ScatterClustersHelper helper = interpolator.helper(node, lm, p);
        factory.setClustersHelper(helper);

        IntegerArgument neighbors = interpolator.neighbors(node, p.getRandom());
        factory.setNeighborCount(neighbors);

        return factory.build();
    }
}
