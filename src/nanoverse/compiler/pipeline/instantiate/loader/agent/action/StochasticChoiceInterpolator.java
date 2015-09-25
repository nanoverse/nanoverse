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

package nanoverse.compiler.pipeline.instantiate.loader.agent.action;

import nanoverse.compiler.pipeline.instantiate.helpers.LoadHelper;
import nanoverse.compiler.pipeline.instantiate.loader.agent.action.stochastic.DynamicActionRangeMapLoader;
import nanoverse.compiler.pipeline.translate.nodes.*;
import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.control.arguments.DynamicActionRangeMapDescriptor;
import nanoverse.runtime.layers.LayerManager;

/**
 * Created by dbborens on 8/25/2015.
 */
public class StochasticChoiceInterpolator {

    private final LoadHelper load;

    public StochasticChoiceInterpolator() {
        load = new LoadHelper();
    }

    public DynamicActionRangeMapDescriptor options(MapObjectNode node, LayerManager lm, GeneralParameters p) {

        DynamicActionRangeMapLoader loader = (DynamicActionRangeMapLoader) load.getLoader(node, "options", true);
        ListObjectNode cNode = (ListObjectNode) node.getMember("options");
        return loader.instantiate(cNode, lm, p);
    }
}
