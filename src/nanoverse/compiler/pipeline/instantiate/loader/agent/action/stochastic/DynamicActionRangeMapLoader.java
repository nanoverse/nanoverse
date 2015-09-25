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

import nanoverse.compiler.pipeline.instantiate.factory.agent.action.stochastic.DynamicActionRangeMapFactory;
import nanoverse.compiler.pipeline.instantiate.loader.Loader;
import nanoverse.compiler.pipeline.translate.nodes.*;
import nanoverse.runtime.agent.action.stochastic.WeightedOption;
import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.control.arguments.DynamicActionRangeMapDescriptor;
import nanoverse.runtime.layers.LayerManager;

import java.util.stream.Stream;

/**
 * Created by dbborens on 8/25/2015.
 */
public class DynamicActionRangeMapLoader extends Loader<DynamicActionRangeMapDescriptor> {

    private final DynamicActionRangeMapFactory factory;
    private final DynamicActionRangeMapChildLoader interpolator;

    public DynamicActionRangeMapLoader() {
        factory = new DynamicActionRangeMapFactory();
        interpolator = new DynamicActionRangeMapChildLoader();
    }

    public DynamicActionRangeMapLoader(DynamicActionRangeMapFactory factory,
                                       DynamicActionRangeMapChildLoader interpolator) {

        this.factory = factory;
        this.interpolator = interpolator;
    }

    public DynamicActionRangeMapDescriptor instantiate(ListObjectNode node,
                                                       LayerManager lm,
                                                       GeneralParameters p) {

        factory.setLayerManager(lm);

        Stream<WeightedOption> options = node.getMemberStream()
            .map(o -> (MapObjectNode) o)
            .map(m -> interpolator.weightedOption(m, lm, p));

        factory.setOptions(options);

        return factory.build();
    }
}
