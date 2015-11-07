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

package nanoverse.compiler.pipeline.instantiate.loader.processes.continuum;

import nanoverse.compiler.pipeline.instantiate.helpers.LoadHelper;
import nanoverse.compiler.pipeline.instantiate.loader.geometry.set.CoordinateSetLoader;
import nanoverse.compiler.pipeline.instantiate.loader.processes.BaseProcessArgumentsLoader;
import nanoverse.compiler.pipeline.instantiate.loader.processes.ProcessInterpolator;
import nanoverse.compiler.pipeline.translate.nodes.MapObjectNode;
import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.control.arguments.DoubleArgument;
import nanoverse.runtime.geometry.set.CoordinateSet;
import nanoverse.runtime.layers.LayerManager;

import java.util.Random;

/**
 * Created by dbborens on 10/5/2015.
 */
public class DirichletBoundaryEnforcerInterpolator extends ProcessInterpolator {
    public DirichletBoundaryEnforcerInterpolator() {
        super();
    }

    public DirichletBoundaryEnforcerInterpolator(LoadHelper load, BaseProcessArgumentsLoader bpaLoader) {
        super(load, bpaLoader);
    }

    public CoordinateSet activeSites(MapObjectNode node, LayerManager lm, GeneralParameters p) {
        CoordinateSetLoader loader = (CoordinateSetLoader) load.getLoader(node, "activeSites", true);
        MapObjectNode childNode = (MapObjectNode) node.getMember("activeSites");
        return loader.instantiate(childNode, lm, p);
    }

    public String layer(MapObjectNode node) {
        return load.aString(node, "layer");
    }

    public DoubleArgument value(MapObjectNode node, Random random) {
        return load.aDoubleArgument(node, "value", random);
    }
}
