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

package compiler.pipeline.instantiate.loader.agent.action.stochastic;

import agent.action.ActionDescriptor;
import compiler.pipeline.instantiate.helpers.LoadHelper;
import compiler.pipeline.instantiate.loader.agent.action.ActionLoader;
import compiler.pipeline.translate.nodes.MapObjectNode;
import control.GeneralParameters;
import control.arguments.ProbabilitySupplierDescriptor;
import layers.LayerManager;

/**
 * Created by dbborens on 8/25/2015.
 */
public class WeightedOptionInterpolator {

    private final LoadHelper load;

    public WeightedOptionInterpolator() {
        load = new LoadHelper();
    }

    public WeightedOptionInterpolator(LoadHelper load) {
        this.load = load;
    }

    public ActionDescriptor action(MapObjectNode node, LayerManager lm, GeneralParameters p) {
        ActionLoader loader = (ActionLoader) load.getLoader(node, "action", true);
        MapObjectNode cNode = (MapObjectNode) node.getMember("action");

        return loader.instantiate(cNode, lm, p);
    }

    public ProbabilitySupplierDescriptor weight(MapObjectNode node, LayerManager lm, GeneralParameters p) {
        ProbabilitySupplierLoader loader = (ProbabilitySupplierLoader) load.getLoader(node, "weight", true);
        MapObjectNode cNode = (MapObjectNode) node.getMember("weight");

        return loader.instantiate(cNode, lm, p);
    }
}
