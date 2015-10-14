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

package nanoverse.compiler.pipeline.instantiate.loader.agent.action.stochastic;

import nanoverse.compiler.pipeline.instantiate.factory.agent.action.stochastic.NeighborhoodProbabilitySupplierFactory;
import nanoverse.compiler.pipeline.translate.nodes.MapObjectNode;
import nanoverse.runtime.agent.action.stochastic.NeighborhoodProbabilitySupplierDescriptor;
import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.layers.LayerManager;

/**
 * Created by dbborens on 8/25/2015.
 */
public class NeighborhoodProbabilitySupplierLoader
    extends ProbabilitySupplierLoader<NeighborhoodProbabilitySupplierDescriptor> {

    private final NeighborhoodProbabilitySupplierFactory factory;
    private final NeighborhoodProbabilitySupplierInterpolator interpolator;

    public NeighborhoodProbabilitySupplierLoader() {
        factory = new NeighborhoodProbabilitySupplierFactory();
        interpolator = new NeighborhoodProbabilitySupplierInterpolator();
    }

    public NeighborhoodProbabilitySupplierLoader(
            NeighborhoodProbabilitySupplierFactory factory,
            NeighborhoodProbabilitySupplierInterpolator interpolator) {

        this.factory = factory;
        this.interpolator = interpolator;
    }

    @Override
    public NeighborhoodProbabilitySupplierDescriptor instantiate(MapObjectNode node,
                                                              LayerManager lm,
                                                              GeneralParameters p) {


        double offset = interpolator.offset(node, p.getRandom());
        factory.setOffset(offset);

        double coefficient = interpolator.coefficient(node, p.getRandom());
        factory.setCoefficient(coefficient);

        return factory.build();
    }
}
