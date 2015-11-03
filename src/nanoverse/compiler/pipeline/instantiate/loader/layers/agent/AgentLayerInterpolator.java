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

package nanoverse.compiler.pipeline.instantiate.loader.layers.agent;

import nanoverse.compiler.pipeline.instantiate.helpers.LoadHelper;
import nanoverse.compiler.pipeline.instantiate.loader.geometry.boundary.BoundaryLoader;
import nanoverse.compiler.pipeline.translate.nodes.MapObjectNode;
import nanoverse.runtime.control.arguments.GeometryDescriptor;
import nanoverse.runtime.geometry.boundaries.Boundary;

/**
 * Created by dbborens on 8/20/2015.
 */
public class AgentLayerInterpolator {

    private final LoadHelper load;
    private final AgentLayerDefaults defaults;

    public AgentLayerInterpolator() {
        load = new LoadHelper();
        defaults = new AgentLayerDefaults();
    }

    public AgentLayerInterpolator(LoadHelper load, AgentLayerDefaults defaults) {
        this.load = load;
        this.defaults = defaults;
    }

    public Boundary boundary(MapObjectNode node, GeometryDescriptor geom) {
        BoundaryLoader loader = (BoundaryLoader) load.getLoader(node, "boundary", false);

        if (loader == null) {
            return defaults.boundary(geom);
        }

        return loader.instantiate(geom);
    }
}
