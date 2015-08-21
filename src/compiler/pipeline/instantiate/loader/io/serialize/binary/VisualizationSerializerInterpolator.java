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

package compiler.pipeline.instantiate.loader.io.serialize.binary;

import compiler.pipeline.instantiate.helpers.LoadHelper;
import compiler.pipeline.instantiate.loader.io.visual.VisualizationLoader;
import compiler.pipeline.translate.nodes.MapObjectNode;
import io.visual.Visualization;
import layers.LayerManager;

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
        return load.aString(node, "prefix", defaults.prefix());
    }

    public Visualization visualization(MapObjectNode node, LayerManager layerManager) {
        VisualizationLoader loader = (VisualizationLoader) load.getLoader(node, "visualization", false);
        if (loader == null) {
            return defaults.visualization(layerManager);
        }
        return null;
    }

}
