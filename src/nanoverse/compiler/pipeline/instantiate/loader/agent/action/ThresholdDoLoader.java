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

package nanoverse.compiler.pipeline.instantiate.loader.agent.action;

import nanoverse.runtime.agent.action.*;
import nanoverse.compiler.pipeline.instantiate.factory.agent.action.ThresholdDoFactory;
import nanoverse.compiler.pipeline.translate.nodes.MapObjectNode;
import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.control.arguments.DoubleArgument;
import nanoverse.runtime.layers.LayerManager;

/**
 * Created by dbborens on 8/3/2015.
 */
public class ThresholdDoLoader extends ActionLoader<ThresholdDoDescriptor> {

    private final ThresholdDoFactory factory;
    private final ThresholdDoInterpolator interpolator;

    public ThresholdDoLoader() {
        factory = new ThresholdDoFactory();
        interpolator = new ThresholdDoInterpolator();
    }

    public ThresholdDoLoader(ThresholdDoFactory factory,
                             ThresholdDoInterpolator interpolator) {
        this.factory = factory;
        this.interpolator = interpolator;
    }

    @Override
    public ThresholdDoDescriptor instantiate(MapObjectNode node, LayerManager lm, GeneralParameters p) {
        factory.setLayerManager(lm);

        ActionDescriptor action = interpolator.action(node, lm, p);
        factory.setChildDescriptor(action);

        DoubleArgument minimum = interpolator.minimum(node, p.getRandom());
        factory.setMinimumArg(minimum);

        DoubleArgument maximum = interpolator.maximum(node, p.getRandom());
        factory.setMaximumArg(maximum);

        String layerId = interpolator.layer(node);
        factory.setLayerId(layerId);

        return factory.build();
    }
}
