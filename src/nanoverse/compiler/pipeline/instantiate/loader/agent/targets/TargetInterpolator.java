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

package nanoverse.compiler.pipeline.instantiate.loader.agent.targets;

import nanoverse.compiler.pipeline.instantiate.helpers.LoadHelper;
import nanoverse.compiler.pipeline.instantiate.loader.processes.discrete.filter.FilterLoader;
import nanoverse.compiler.pipeline.translate.nodes.MapObjectNode;
import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.layers.LayerManager;
import nanoverse.runtime.processes.discrete.filter.Filter;

import java.util.Random;

/**
 * Created by dbborens on 8/24/2015.
 */
public class TargetInterpolator {

    private final LoadHelper load;
    private final TargetDefaults defaults;

    public TargetInterpolator() {
        load = new LoadHelper();
        defaults = new TargetDefaults();
    }

    public TargetInterpolator(LoadHelper load, TargetDefaults defaults) {
        this.load = load;
        this.defaults = defaults;
    }

    public int maximum(MapObjectNode node, Random random) {
        return load.anInteger(node, "maximum", random, defaults::maximum);
    }

    public Filter filter(MapObjectNode node, LayerManager lm, GeneralParameters p) {
        FilterLoader loader = (FilterLoader) load.getLoader(node, "filter", false);

        if (loader == null) {
            return defaults.filter(lm, p);
        }

        MapObjectNode cNode = (MapObjectNode) node.getMember("filter");
        return loader.instantiate(cNode, lm, p);
    }
}
