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

import nanoverse.compiler.pipeline.instantiate.loader.agent.AgentDescriptorLoader;
import nanoverse.compiler.pipeline.instantiate.loader.processes.discrete.cluster.StrictSeparationClusterHelperLoader;
import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.control.arguments.*;
import nanoverse.runtime.layers.LayerManager;
import nanoverse.runtime.processes.discrete.cluster.SeparationStrategyManager;

/**
 * Created by dbborens on 8/27/2015.
 */
public class ScatterClustersDefaults {
    public AgentDescriptor description(LayerManager lm, GeneralParameters p) {
        AgentDescriptorLoader loader = new AgentDescriptorLoader();
        return loader.instantiate(lm, p);
    }

    public SeparationStrategyManager helper(LayerManager lm, GeneralParameters p) {
        StrictSeparationClusterHelperLoader loader = new StrictSeparationClusterHelperLoader();
        return loader.instantiate(lm, p);
    }

    public IntegerArgument neighbors() {
        return new ConstantInteger(2);
    }
}
