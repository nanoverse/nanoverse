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

package compiler.pipeline.instantiate.loader.processes.continuum;

import compiler.pipeline.instantiate.helpers.LoadHelper;
import compiler.pipeline.instantiate.loader.geometry.set.CoordinateSetLoader;
import compiler.pipeline.instantiate.loader.processes.*;
import compiler.pipeline.translate.nodes.MapObjectNode;
import control.GeneralParameters;
import control.arguments.DoubleArgument;
import geometry.set.CoordinateSet;
import layers.LayerManager;

import java.util.Random;

/**
 * Created by dbborens on 8/26/2015.
 */
public class InjectionProcessInterpolator extends ProcessInterpolator {

    private final InjectionProcessDefaults defaults;

    public InjectionProcessInterpolator() {
        super();
        defaults = new InjectionProcessDefaults();
    }

    public InjectionProcessInterpolator(LoadHelper load,
                                        BaseProcessArgumentsLoader bpaLoader,
                                        InjectionProcessDefaults defaults) {
        super(load, bpaLoader);
        this.defaults = defaults;
    }

    public CoordinateSet activeSites(MapObjectNode node, LayerManager lm, GeneralParameters p) {
        CoordinateSetLoader loader = (CoordinateSetLoader) load.getLoader(node, "activeSites", false);
        if (loader == null) {
            return defaults.activeSites(lm, p);
        }

        MapObjectNode cNode = (MapObjectNode) node.getMember("activeSites");

        return loader.instantiate(cNode, lm, p);
    }

    public String layer(MapObjectNode node) {
        return load.aString(node, "layer");
    }

    public DoubleArgument value(MapObjectNode node, Random random) {
        return load.aDoubleArgument(node, "value", random);
    }
}
