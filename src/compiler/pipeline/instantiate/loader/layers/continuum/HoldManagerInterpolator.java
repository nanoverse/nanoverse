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

package compiler.pipeline.instantiate.loader.layers.continuum;

import compiler.pipeline.instantiate.helpers.LoadHelper;
import compiler.pipeline.instantiate.loader.layers.continuum.solvers.ContinuumSolverLoader;
import compiler.pipeline.translate.nodes.MapObjectNode;
import layers.continuum.*;
import layers.continuum.solvers.ContinuumSolver;

import java.util.Random;

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
