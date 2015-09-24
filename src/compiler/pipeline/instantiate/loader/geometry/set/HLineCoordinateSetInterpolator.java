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

package compiler.pipeline.instantiate.loader.geometry.set;

import compiler.pipeline.instantiate.helpers.LoadHelper;
import compiler.pipeline.instantiate.loader.control.identifiers.CoordinateLoader;
import compiler.pipeline.translate.nodes.MapObjectNode;
import control.GeneralParameters;
import control.identifiers.Coordinate;
import geometry.Geometry;
import layers.LayerManager;

import java.util.Random;

/**
 * Created by dbborens on 9/19/2015.
 */
public class HLineCoordinateSetInterpolator {
    private final LoadHelper load;
    private final HLineCoordinateSetDefaults defaults;

    public HLineCoordinateSetInterpolator() {
        load = new LoadHelper();
        defaults = new HLineCoordinateSetDefaults();
    }

    public HLineCoordinateSetInterpolator(LoadHelper load,
                                          HLineCoordinateSetDefaults defaults) {

        this.load = load;
        this.defaults = defaults;
    }

    public int length(MapObjectNode node, Random random) {
        return load.anInteger(node, "length", random, defaults::length);
    }

    public Geometry geometry(LayerManager lm) {
        return lm.getCellLayer().getGeometry();
    }

    public Coordinate start(MapObjectNode node, LayerManager lm, GeneralParameters p) {
        CoordinateLoader loader = (CoordinateLoader) load.getLoader(node, "start", false);
        if (loader == null) {
            return defaults.start(lm);
        }

        MapObjectNode cNode = (MapObjectNode) node.getMember("start");
        return loader.instantiate(cNode, lm, p);
    }
}
