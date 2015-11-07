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
import nanoverse.compiler.pipeline.instantiate.loader.processes.BaseProcessArgumentsLoader;
import nanoverse.compiler.pipeline.translate.nodes.MapObjectNode;

/**
 * Created by dbborens on 8/27/2015.
 */
public class ManualHaltInterpolator extends DiscreteProcessInterpolator {
    private final ManualHaltDefaults defaults;

    public ManualHaltInterpolator() {
        super();
        defaults = new ManualHaltDefaults();
    }

    public ManualHaltInterpolator(LoadHelper load,
                                  BaseProcessArgumentsLoader bpaLoader,
                                  DiscreteProcessArgumentsLoader dpaLoader,
                                  ManualHaltDefaults defaults) {
        super(load, bpaLoader, dpaLoader);
        this.defaults = defaults;
    }

    public String message(MapObjectNode node) {
        return load.aString(node, "message", defaults::message);
    }
}
