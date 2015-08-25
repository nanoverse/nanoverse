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

import compiler.pipeline.instantiate.helpers.LoadHelper;
import compiler.pipeline.translate.nodes.MapObjectNode;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Random;

/**
 * Created by dbborens on 8/25/2015.
 */
public class DependentProbabilitySupplierInterpolator {

    private final LoadHelper load;

    public DependentProbabilitySupplierInterpolator() {
        load = new LoadHelper();
    }

    public DependentProbabilitySupplierInterpolator(LoadHelper load) {
        this.load = load;
    }

    public double coefficient(MapObjectNode node, Random random) {
        return load.aDouble(node, "coefficient", random);
    }

    public double offset(MapObjectNode node, Random random) {
        return load.aDouble(node, "offset", random);
    }

    public String layer(MapObjectNode node) {
        return load.aString(node, "layer");
    }
}
