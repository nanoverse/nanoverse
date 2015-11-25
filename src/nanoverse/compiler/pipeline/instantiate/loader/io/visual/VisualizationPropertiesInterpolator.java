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

import nanoverse.compiler.pipeline.instantiate.helpers.LoadHelper;
import nanoverse.compiler.pipeline.instantiate.loader.io.visual.color.ColorModelLoader;
import nanoverse.compiler.pipeline.instantiate.loader.io.visual.highlight.HighlightManagerLoader;
import nanoverse.compiler.pipeline.translate.nodes.*;
import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.io.visual.color.ColorManager;
import nanoverse.runtime.io.visual.highlight.HighlightManager;
import nanoverse.runtime.layers.LayerManager;

import java.util.Random;

/**
 * Created by dbborens on 8/21/2015.
 */
public class VisualizationPropertiesInterpolator {

    private final LoadHelper load;
    private final VisualizationPropertiesDefaults defaults;

    public VisualizationPropertiesInterpolator() {
        load = new LoadHelper();
        defaults = new VisualizationPropertiesDefaults();
    }

    public VisualizationPropertiesInterpolator(LoadHelper load,
                                               VisualizationPropertiesDefaults defaults) {

        this.load = load;
        this.defaults = defaults;
    }

    public Integer edge(MapObjectNode node, Random random) {
        return load.anInteger(node, "edge", random, defaults::edge);
    }

    public Integer outline(MapObjectNode node, Random random) {
        return load.anInteger(node, "outline", random, defaults::outline);
    }

    public ColorManager colorModel(MapObjectNode node, LayerManager lm, GeneralParameters p) {
        ColorModelLoader loader = (ColorModelLoader) load.getLoader(node, "color", false);

        if (loader == null) {
            return defaults.color(p);
        }

        MapObjectNode cNode = (MapObjectNode) node.getMember("color");
        return loader.instantiate(cNode, lm, p);
    }

    public HighlightManager highlights(MapObjectNode node, GeneralParameters p) {
        HighlightManagerLoader loader = (HighlightManagerLoader) load.getLoader(node, "highlights", false);

        if (loader == null) {
            return defaults.highlights(p);
        }

        ListObjectNode cNode = (ListObjectNode) node.getMember("highlights");
        return loader.instantiate(cNode, p);
    }
}
