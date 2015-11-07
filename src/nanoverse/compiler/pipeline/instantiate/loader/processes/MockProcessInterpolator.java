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

package nanoverse.compiler.pipeline.instantiate.loader.processes;

import nanoverse.compiler.pipeline.instantiate.helpers.LoadHelper;
import nanoverse.compiler.pipeline.translate.nodes.MapObjectNode;

import java.util.Random;

/**
 * Created by dbborens on 8/26/2015.
 */
public class MockProcessInterpolator extends ProcessInterpolator {

    private final MockProcessDefaults defaults;

    public MockProcessInterpolator() {
        super();
        defaults = new MockProcessDefaults();
    }

    public MockProcessInterpolator(LoadHelper load,
                                   BaseProcessArgumentsLoader bpaLoader,
                                   MockProcessDefaults defaults) {

        super(load, bpaLoader);
        this.defaults = defaults;
    }

    public String identifier(MapObjectNode node) {
        return load.aString(node, "identifier");
    }

    public int count(MapObjectNode node, Random random) {
        return load.anInteger(node, "count", random, defaults::count);
    }

    public double weight(MapObjectNode node, Random random) {
        return load.aDouble(node, "weight", random, defaults::weight);
    }
}
