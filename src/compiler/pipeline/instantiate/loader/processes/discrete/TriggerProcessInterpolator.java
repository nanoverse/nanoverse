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

package compiler.pipeline.instantiate.loader.processes.discrete;

import compiler.pipeline.instantiate.helpers.LoadHelper;
import compiler.pipeline.instantiate.loader.agent.AgentDescriptorLoader;
import compiler.pipeline.instantiate.loader.processes.BaseProcessArgumentsLoader;
import compiler.pipeline.instantiate.loader.processes.discrete.filter.FilterLoader;
import compiler.pipeline.translate.nodes.MapObjectNode;
import control.GeneralParameters;
import layers.LayerManager;
import processes.discrete.filter.Filter;

import java.util.Random;

/**
 * Created by dbborens on 8/26/2015.
 */
public class TriggerProcessInterpolator extends DiscreteProcessInterpolator {
    private final TriggerProcessDefaults defaults;

    public TriggerProcessInterpolator() {
        super();
        defaults = new TriggerProcessDefaults();
    }

    public TriggerProcessInterpolator(LoadHelper load,
                               BaseProcessArgumentsLoader bpaLoader,
                               DiscreteProcessArgumentsLoader dpaLoader,
                               TriggerProcessDefaults defaults) {
        super(load, bpaLoader, dpaLoader);
        this.defaults = defaults;
    }

    public Filter filter(MapObjectNode node, LayerManager lm, GeneralParameters p) {
        FilterLoader loader = (FilterLoader) load.getLoader(node, "filter", false);

        if (loader == null) {
            return defaults.filter(lm, p);
        }

        MapObjectNode cNode = (MapObjectNode) node.getMember("filter");
        return loader.instantiate(cNode, lm.getCellLayer(), p);
    }

    public String behavior(MapObjectNode node) {
        return load.aString(node, "behavior");
    }

    public Boolean requireNeighbors(MapObjectNode node, Random random) {
        return load.aBoolean(node, "requireNeighbors", random, defaults::requireNeighbors);
    }

    public Boolean skipVacantSites(MapObjectNode node, Random random) {
        return load.aBoolean(node, "skipVacant", random, defaults::skipVacant);
    }
}
