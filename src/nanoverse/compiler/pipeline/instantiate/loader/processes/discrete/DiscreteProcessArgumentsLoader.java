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

import nanoverse.compiler.pipeline.instantiate.factory.processes.discrete.DiscreteProcessArgumentsFactory;
import nanoverse.compiler.pipeline.instantiate.loader.Loader;
import nanoverse.compiler.pipeline.translate.nodes.MapObjectNode;
import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.control.arguments.IntegerArgument;
import nanoverse.runtime.geometry.set.CoordinateSet;
import nanoverse.runtime.layers.LayerManager;
import nanoverse.runtime.processes.discrete.AgentProcessArguments;

/**
 * Created by dbborens on 8/12/15.
 */
public class DiscreteProcessArgumentsLoader extends Loader<AgentProcessArguments> {

    private final DiscreteProcessArgumentsFactory factory;
    private final DiscreteProcessArgumentsInterpolator interpolator;

    public DiscreteProcessArgumentsLoader() {
        factory = new DiscreteProcessArgumentsFactory();
        interpolator = new DiscreteProcessArgumentsInterpolator();
    }

    public DiscreteProcessArgumentsLoader(DiscreteProcessArgumentsFactory factory,
                                          DiscreteProcessArgumentsInterpolator interpolator) {
        this.factory = factory;
        this.interpolator = interpolator;
    }

    public AgentProcessArguments instantiate(MapObjectNode node, LayerManager lm, GeneralParameters p) {
        CoordinateSet activeSites = interpolator.activeSites(node, lm, p);
        factory.setActiveSites(activeSites);

        IntegerArgument maxTargets = interpolator.maxTargets(node, p.getRandom());
        factory.setMaxTargets(maxTargets);

        return factory.build();
    }


}
