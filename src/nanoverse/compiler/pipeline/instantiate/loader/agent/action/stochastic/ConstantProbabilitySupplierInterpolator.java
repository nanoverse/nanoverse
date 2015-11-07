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

package nanoverse.compiler.pipeline.instantiate.loader.agent.action.stochastic;

import nanoverse.compiler.pipeline.instantiate.helpers.LoadHelper;
import nanoverse.compiler.pipeline.translate.nodes.MapObjectNode;

import java.util.Random;

/**
 * Created by dbborens on 8/25/2015.
 */
public class ConstantProbabilitySupplierInterpolator {

    private final LoadHelper load;

    public ConstantProbabilitySupplierInterpolator() {
        load = new LoadHelper();
    }

    public ConstantProbabilitySupplierInterpolator(LoadHelper load) {
        this.load = load;
    }

    public double value(MapObjectNode node, Random random) {
        return load.aDouble(node, "value", random);
    }
}
