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

import nanoverse.compiler.pipeline.instantiate.factory.agent.action.stochastic.ConstantProbabilitySupplierFactory;
import nanoverse.compiler.pipeline.translate.nodes.MapObjectNode;
import nanoverse.runtime.agent.action.stochastic.ConstantProbabilitySupplierDescriptor;
import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.layers.LayerManager;

/**
 * Created by dbborens on 8/25/2015.
 */
public class ConstantProbabilitySupplierLoader
    extends ProbabilitySupplierLoader<ConstantProbabilitySupplierDescriptor> {

    private final ConstantProbabilitySupplierFactory factory;
    private final ConstantProbabilitySupplierInterpolator interpolator;

    public ConstantProbabilitySupplierLoader() {
        factory = new ConstantProbabilitySupplierFactory();
        interpolator = new ConstantProbabilitySupplierInterpolator();
    }

    public ConstantProbabilitySupplierLoader(
        ConstantProbabilitySupplierFactory factory,
        ConstantProbabilitySupplierInterpolator interpolator) {

        this.factory = factory;
        this.interpolator = interpolator;
    }

    @Override
    public ConstantProbabilitySupplierDescriptor instantiate(
        MapObjectNode node,
        LayerManager lm,
        GeneralParameters p) {

        double value = interpolator.value(node, p.getRandom());
        factory.setValue(value);

        return factory.build();
    }
}
