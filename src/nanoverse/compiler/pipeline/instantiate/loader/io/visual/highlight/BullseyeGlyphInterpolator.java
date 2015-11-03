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

package nanoverse.compiler.pipeline.instantiate.loader.io.visual.highlight;

import nanoverse.compiler.pipeline.instantiate.helpers.LoadHelper;
import nanoverse.compiler.pipeline.instantiate.loader.io.visual.color.ColorLoader;
import nanoverse.compiler.pipeline.translate.nodes.MapObjectNode;
import nanoverse.runtime.control.GeneralParameters;

import java.awt.*;
import java.util.Random;

/**
 * Created by dbborens on 8/23/2015.
 */
public class BullseyeGlyphInterpolator {

    private final LoadHelper load;
    private final BullseyeGlyphDefaults defaults;
    private final ColorLoader colorLoader;

    public BullseyeGlyphInterpolator() {
        load = new LoadHelper();
        defaults = new BullseyeGlyphDefaults();
        colorLoader = new ColorLoader();
    }

    public BullseyeGlyphInterpolator(LoadHelper load,
                                     BullseyeGlyphDefaults defaults,
                                     ColorLoader colorLoader) {
        this.load = load;
        this.defaults = defaults;
        this.colorLoader = colorLoader;
    }

    public Color primary(MapObjectNode node, GeneralParameters p) {
        String colorStr = load.aString(node, "primary", defaults::primary);
        return colorLoader.instantiate(colorStr, p);
    }

    public Color secondary(MapObjectNode node, GeneralParameters p) {
        String colorStr = load.aString(node, "secondary", defaults::secondary);
        return colorLoader.instantiate(colorStr, p);
    }

    public Double size(MapObjectNode node, Random random) {
        return load.aDouble(node, "size", random, defaults::size);
    }
}
