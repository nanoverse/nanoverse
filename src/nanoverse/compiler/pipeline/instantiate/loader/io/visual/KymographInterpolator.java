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

import nanoverse.compiler.pipeline.translate.nodes.MapObjectNode;
import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.io.visual.VisualizationProperties;
import nanoverse.runtime.layers.LayerManager;

/**
 * Created by dbborens on 8/23/2015.
 */
public class KymographInterpolator {
    private final KymographDefaults defaults;
    private final VisualizationPropertiesLoader vpLoader;

    public KymographInterpolator() {
        defaults = new KymographDefaults();
        vpLoader = new VisualizationPropertiesLoader();
    }

    public KymographInterpolator(KymographDefaults defaults,
                                 VisualizationPropertiesLoader vpLoader) {
        this.defaults = defaults;
        this.vpLoader = vpLoader;
    }

    public VisualizationProperties properties(MapObjectNode node, LayerManager lm, GeneralParameters p) {
        if (node == null) {
            return defaults.properties(lm, p);
        }

        return vpLoader.instantiate(node, lm, p);
    }
}
