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

package nanoverse.compiler.pipeline.instantiate.loader.processes.discrete;

import nanoverse.compiler.pipeline.instantiate.helpers.LoadHelper;
import nanoverse.compiler.pipeline.instantiate.loader.geometry.set.CoordinateSetLoader;
import nanoverse.compiler.pipeline.translate.nodes.*;
import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.control.arguments.IntegerArgument;
import nanoverse.runtime.geometry.set.CoordinateSet;
import nanoverse.runtime.layers.LayerManager;

import java.util.Random;

/**
 * Created by dbborens on 8/26/2015.
 */
public class DiscreteProcessArgumentsInterpolator {

    private final DiscreteProcessArgumentsDefaults defaults;
    private final LoadHelper load;

    public DiscreteProcessArgumentsInterpolator() {
        load = new LoadHelper();
        defaults = new DiscreteProcessArgumentsDefaults();
    }

    public DiscreteProcessArgumentsInterpolator(LoadHelper load,
                                                DiscreteProcessArgumentsDefaults defaults) {
        this.load = load;
        this.defaults = defaults;
    }

    public CoordinateSet activeSites(MapObjectNode node, LayerManager lm, GeneralParameters p) {
        CoordinateSetLoader loader = (CoordinateSetLoader) load.getLoader(node, "activeSites", false);
        if (loader == null) {
            return defaults.activeSites(lm, p);
        }

        ObjectNode childNode = node.getMember("activeSites");
        return loader.instantiate(childNode, lm, p);
    }

    public IntegerArgument maxTargets(MapObjectNode node, Random random) {
        return load.anIntegerArgument(node, "maxTargets", random, defaults::maxTargets);
    }
}
