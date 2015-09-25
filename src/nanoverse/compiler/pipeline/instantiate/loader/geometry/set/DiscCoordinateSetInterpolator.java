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

package nanoverse.compiler.pipeline.instantiate.loader.geometry.set;

import nanoverse.compiler.pipeline.instantiate.helpers.LoadHelper;
import nanoverse.compiler.pipeline.instantiate.loader.control.identifiers.CoordinateLoader;
import nanoverse.compiler.pipeline.translate.nodes.MapObjectNode;
import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.control.arguments.IntegerArgument;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.geometry.Geometry;
import nanoverse.runtime.layers.LayerManager;

import java.util.Random;

/**
 * Created by dbborens on 9/19/2015.
 */
public class DiscCoordinateSetInterpolator {

    private final LoadHelper load;
    private final DiscCoordinateSetDefaults defaults;

    public DiscCoordinateSetInterpolator() {
        load = new LoadHelper();
        defaults = new DiscCoordinateSetDefaults();
    }

    public DiscCoordinateSetInterpolator(LoadHelper load,
                                         DiscCoordinateSetDefaults defaults) {
        this.load = load;
        this.defaults = defaults;
    }

    public Geometry geometry(LayerManager lm) {
        return lm.getCellLayer().getGeometry();
    }

    public Coordinate offset(MapObjectNode node, LayerManager lm, GeneralParameters p) {
        CoordinateLoader loader = (CoordinateLoader) load.getLoader(node, "offset", false);
        if (loader == null) {
            return defaults.offset(lm);
        }

        MapObjectNode cNode = (MapObjectNode) node.getMember("offset");
        return loader.instantiate(cNode, lm, p);
    }

    public IntegerArgument radiusArg(MapObjectNode node, Random random) {
        return load.anIntegerArgument(node, "radius", random, defaults::radius);
    }
}
