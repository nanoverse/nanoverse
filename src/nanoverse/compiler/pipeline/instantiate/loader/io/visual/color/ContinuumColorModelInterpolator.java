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

package nanoverse.compiler.pipeline.instantiate.loader.io.visual.color;

import nanoverse.compiler.pipeline.instantiate.helpers.LoadHelper;
import nanoverse.compiler.pipeline.translate.nodes.MapObjectNode;
import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.control.arguments.DoubleArgument;
import nanoverse.runtime.io.visual.color.ColorManager;

import java.util.Random;

/**
 * Created by dbborens on 8/23/2015.
 */
public class ContinuumColorModelInterpolator {

    private final LoadHelper load;
    private final ContinuumColorModelDefaults defaults;

    public ContinuumColorModelInterpolator() {
        load = new LoadHelper();
        defaults = new ContinuumColorModelDefaults();
    }

    public ContinuumColorModelInterpolator(LoadHelper load, ContinuumColorModelDefaults defaults) {
        this.load = load;
        this.defaults = defaults;
    }

    public boolean averageLuminance(MapObjectNode node, Random random) {
        return load.aBoolean(node, "useLuminanceAverage", random,
            defaults::averageLuminance);
    }

    public ColorManager base(MapObjectNode node, GeneralParameters p) {
        ColorModelLoader loader = (ColorModelLoader) load.getLoader(node, "base", false);

        if (loader == null) {
            return defaults.base(p);
        }

        MapObjectNode cNode = (MapObjectNode) node.getMember("base");

        return loader.instantiate(cNode, p);
    }

    public String id(MapObjectNode node) {
        return load.aString(node, "id");
    }

    public DoubleArgument maxHue(MapObjectNode node, Random random) {
        return load.aDoubleArgument(node, "maxHue", random, defaults::maxHue);
    }

    public DoubleArgument minHue(MapObjectNode node, Random random) {
        return load.aDoubleArgument(node, "minHue", random, defaults::minHue);
    }

    public DoubleArgument minSat(MapObjectNode node, Random random) {
        return load.aDoubleArgument(node, "minSat", random, defaults::minSat);
    }

    public DoubleArgument maxSat(MapObjectNode node, Random random) {
        return load.aDoubleArgument(node, "maxSat", random, defaults::maxSat);
    }

    public DoubleArgument maxLum(MapObjectNode node, Random random) {
        return load.aDoubleArgument(node, "maxLum", random, defaults::maxLum);
    }

    public DoubleArgument minLum(MapObjectNode node, Random random) {
        return load.aDoubleArgument(node, "minLum", random, defaults::minLum);
    }
}
