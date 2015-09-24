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

package compiler.pipeline.instantiate.loader.io.visual.highlight;

import compiler.pipeline.instantiate.helpers.LoadHelper;
import compiler.pipeline.instantiate.loader.io.visual.color.ColorLoader;
import compiler.pipeline.translate.nodes.MapObjectNode;
import control.GeneralParameters;

import java.awt.*;
import java.util.Random;

/**
 * Created by dbborens on 8/23/2015.
 */
public class DotGlyphInterpolator {
    private final LoadHelper load;
    private final DotGlyphDefaults defaults;
    private final ColorLoader colorLoader;

    public DotGlyphInterpolator() {
        load = new LoadHelper();
        defaults = new DotGlyphDefaults();
        colorLoader = new ColorLoader();
    }

    public DotGlyphInterpolator(LoadHelper load,
                                DotGlyphDefaults defaults,
                                ColorLoader colorLoader) {
        this.load = load;
        this.defaults = defaults;
        this.colorLoader = colorLoader;
    }

    public Color color(MapObjectNode node, GeneralParameters p) {
        String colorStr = load.aString(node, "color", defaults::color);
        return colorLoader.instantiate(colorStr, p);
    }

    public double size(MapObjectNode node, Random random) {
        return load.aDouble(node, "size", random, defaults::size);
    }
}
