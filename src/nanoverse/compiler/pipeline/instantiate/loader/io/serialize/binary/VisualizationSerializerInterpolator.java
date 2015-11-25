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

import nanoverse.compiler.pipeline.instantiate.helpers.LoadHelper;
import nanoverse.compiler.pipeline.instantiate.loader.io.visual.VisualizationLoader;
import nanoverse.compiler.pipeline.translate.nodes.MapObjectNode;
import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.io.visual.Visualization;
import nanoverse.runtime.layers.LayerManager;

/**
 * Created by dbborens on 8/21/2015.
 */
public class VisualizationSerializerInterpolator {

    private final LoadHelper load;
    private final VisualizationSerializerDefaults defaults;

    public VisualizationSerializerInterpolator() {
        load = new LoadHelper();
        defaults = new VisualizationSerializerDefaults();
    }

    public VisualizationSerializerInterpolator(LoadHelper load,
                                               VisualizationSerializerDefaults defaults) {

        this.load = load;
        this.defaults = defaults;
    }

    public String prefix(MapObjectNode node) {
        return load.aString(node, "prefix", defaults::prefix);
    }

    public Visualization visualization(MapObjectNode node, LayerManager layerManager, GeneralParameters p) {
        VisualizationLoader loader = (VisualizationLoader) load.getLoader(node, "visualization", false);
        if (loader == null) {
            return defaults.visualization(layerManager, p);
        }

        MapObjectNode cNode = (MapObjectNode) node.getMember("visualization");

        return loader.instantiate(cNode, layerManager, p);
    }

}
