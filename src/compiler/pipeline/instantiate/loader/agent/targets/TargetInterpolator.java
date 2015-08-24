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

package compiler.pipeline.instantiate.loader.agent.targets;

import compiler.pipeline.instantiate.helpers.LoadHelper;
import compiler.pipeline.instantiate.loader.processes.discrete.filter.FilterLoader;
import compiler.pipeline.translate.nodes.MapObjectNode;
import control.GeneralParameters;
import layers.cell.CellLayer;
import processes.discrete.filter.Filter;

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

    public Filter filter(MapObjectNode node, CellLayer layer, GeneralParameters p) {
        FilterLoader loader = (FilterLoader) load.getLoader(node, "filter", false);

        if (loader == null) {
            return defaults.filter(layer, p);
        }

        MapObjectNode cNode = (MapObjectNode) node.getMember("filter");
        return loader.instantiate(cNode, layer, p);
    }
}
