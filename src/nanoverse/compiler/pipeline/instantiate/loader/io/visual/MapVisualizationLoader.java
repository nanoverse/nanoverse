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

package nanoverse.compiler.pipeline.instantiate.loader.io.visual;

import nanoverse.compiler.pipeline.instantiate.factory.io.visual.map.MapVisualizationFactory;
import nanoverse.compiler.pipeline.translate.nodes.MapObjectNode;
import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.io.visual.VisualizationProperties;
import nanoverse.runtime.io.visual.map.MapVisualization;

/**
 * Created by dbborens on 8/4/2015.
 */
public class MapVisualizationLoader extends VisualizationLoader<MapVisualization> {
    private final MapVisualizationFactory factory;
    private final MapVisualizationInterpolator interpolator;

    public MapVisualizationLoader() {
        factory = new MapVisualizationFactory();
        interpolator = new MapVisualizationInterpolator();
    }

    public MapVisualizationLoader(MapVisualizationFactory factory,
                                  MapVisualizationInterpolator interpolator) {

        this.factory = factory;
        this.interpolator = interpolator;
    }

    public MapVisualization instantiate(GeneralParameters p) {
        return instantiate(null, p);
    }

    @Override
    public MapVisualization instantiate(MapObjectNode node, GeneralParameters p) {
        VisualizationProperties properties = interpolator.properties(node, p);
        factory.setProperties(properties);

        return factory.build();
    }
}
