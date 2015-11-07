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

package nanoverse.compiler.pipeline.instantiate.loader.io.serialize.binary;

import nanoverse.compiler.pipeline.instantiate.factory.io.serialize.binary.VisualizationSerializerFactory;
import nanoverse.compiler.pipeline.instantiate.loader.io.serialize.OutputLoader;
import nanoverse.compiler.pipeline.translate.nodes.MapObjectNode;
import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.io.serialize.Serializer;
import nanoverse.runtime.io.serialize.binary.VisualizationSerializer;
import nanoverse.runtime.io.visual.Visualization;
import nanoverse.runtime.layers.LayerManager;

/**
 * Created by dbborens on 8/10/2015.
 */
public class VisualizationSerializerLoader extends OutputLoader<VisualizationSerializer> {
    private final VisualizationSerializerFactory factory;
    private final VisualizationSerializerInterpolator interpolator;

    public VisualizationSerializerLoader() {
        factory = new VisualizationSerializerFactory();
        interpolator = new VisualizationSerializerInterpolator();
    }

    public VisualizationSerializerLoader(VisualizationSerializerFactory factory,
                                         VisualizationSerializerInterpolator interpolator) {
        this.factory = factory;
        this.interpolator = interpolator;
    }

    @Override
    public Serializer instantiate(MapObjectNode node, GeneralParameters p, LayerManager layerManager) {
        factory.setP(p);
        factory.setLm(layerManager);

        String prefix = interpolator.prefix(node);
        factory.setPrefix(prefix);

        Visualization visualization = interpolator.visualization(node, layerManager, p);
        factory.setVisualization(visualization);

        return factory.build();
    }
}
