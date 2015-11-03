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

package nanoverse.compiler.pipeline.instantiate.loader.layers.continuum;

import nanoverse.compiler.pipeline.instantiate.helpers.LoadHelper;
import nanoverse.compiler.pipeline.instantiate.loader.layers.continuum.solvers.ContinuumSolverLoader;
import nanoverse.compiler.pipeline.translate.nodes.MapObjectNode;
import nanoverse.runtime.layers.continuum.*;
import nanoverse.runtime.layers.continuum.solvers.ContinuumSolver;

/**
 * Created by dbborens on 8/20/2015.
 */
public class HoldManagerInterpolator {
    private final LoadHelper load;
    private final HoldManagerDefaults defaults;

    public HoldManagerInterpolator() {
        load = new LoadHelper();
        defaults = new HoldManagerDefaults();
    }

    public HoldManagerInterpolator(LoadHelper load,
                                   HoldManagerDefaults defaults) {

        this.load = load;
        this.defaults = defaults;
    }

    public ContinuumSolver solver(MapObjectNode node, ContinuumLayerContent content, ScheduledOperations so) {
        ContinuumSolverLoader loader = (ContinuumSolverLoader) load.getLoader(node, "solver", false);

        if (loader == null) {
            return defaults.solver(content, so);
        }

        MapObjectNode cNode = (MapObjectNode) node.getMember("solver");

        return loader.instantiate(cNode, content, so);
    }

    public String id(MapObjectNode node) {
        return load.aString(node, "id");
    }

}
