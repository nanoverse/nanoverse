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

package compiler.pipeline.instantiate.loader.agent.action;

import agent.targets.TargetDescriptor;
import compiler.pipeline.instantiate.helpers.LoadHelper;
import compiler.pipeline.instantiate.loader.agent.targets.TargetLoader;
import compiler.pipeline.translate.nodes.MapObjectNode;
import control.GeneralParameters;
import control.arguments.IntegerArgument;
import layers.LayerManager;

import java.util.Random;

/**
 * Created by dbborens on 8/24/2015.
 */
public class ExpandToInterpolator {
    private final LoadHelper load;
    private final ExpandToDefaults defaults;

    public ExpandToInterpolator() {
        load = new LoadHelper();
        defaults = new ExpandToDefaults();
    }

    public ExpandToInterpolator(LoadHelper load,
                                ExpandToDefaults defaults) {
        this.load = load;
        this.defaults = defaults;
    }

    public TargetDescriptor target(MapObjectNode node, LayerManager lm, GeneralParameters p) {
        TargetLoader loader = (TargetLoader) load.getLoader(node, "target", true);
        MapObjectNode cNode = (MapObjectNode) node.getMember("target");
        return loader.instantiate(cNode, lm, p);
    }


    public IntegerArgument targetHighlight(MapObjectNode node, Random random) {
        return load.anIntegerArgument(node, "targetHighlight", random, defaults::targetHighlight);
    }

    public IntegerArgument selfHighlight(MapObjectNode node, Random random) {
        return load.anIntegerArgument(node, "selfHighlight", random, defaults::selfHighlight);
    }
}
