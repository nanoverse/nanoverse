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

import nanoverse.compiler.pipeline.instantiate.factory.agent.action.stochastic.WeightedOptionFactory;
import nanoverse.compiler.pipeline.instantiate.loader.Loader;
import nanoverse.compiler.pipeline.translate.nodes.MapObjectNode;
import nanoverse.runtime.agent.action.ActionDescriptor;
import nanoverse.runtime.agent.action.stochastic.WeightedOption;
import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.control.arguments.ProbabilitySupplierDescriptor;
import nanoverse.runtime.layers.LayerManager;

/**
 * Created by dbborens on 8/6/15.
 */
public class WeightedOptionLoader extends Loader<WeightedOption> {

    private final WeightedOptionFactory factory;
    private final WeightedOptionInterpolator interpolator;

    public WeightedOptionLoader() {
        factory = new WeightedOptionFactory();
        interpolator = new WeightedOptionInterpolator();
    }

    public WeightedOptionLoader(WeightedOptionFactory factory,
                                WeightedOptionInterpolator interpolator) {
        this.factory = factory;
        this.interpolator = interpolator;
    }

    public WeightedOption instantiate(MapObjectNode node, LayerManager lm, GeneralParameters p) {

        ActionDescriptor action = interpolator.action(node, lm, p);
        factory.setAction(action);

        ProbabilitySupplierDescriptor weight = interpolator.weight(node, lm, p);
        factory.setWeight(weight);
        return factory.build();
    }
}
