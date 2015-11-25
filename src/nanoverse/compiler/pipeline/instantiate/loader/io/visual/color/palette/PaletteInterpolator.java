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

package nanoverse.compiler.pipeline.instantiate.loader.io.visual.color.palette;

import nanoverse.compiler.pipeline.instantiate.helpers.LoadHelper;
import nanoverse.compiler.pipeline.instantiate.loader.Loader;
import nanoverse.compiler.pipeline.instantiate.loader.io.visual.color.ColorLoader;
import nanoverse.compiler.pipeline.instantiate.loader.io.visual.color.ColorModelLoader;
import nanoverse.compiler.pipeline.translate.nodes.MapObjectNode;
import nanoverse.runtime.control.GeneralParameters;

import java.awt.*;
import java.util.Random;

/**
 * Created by dbborens on 11/24/2015.
 */
public class PaletteInterpolator{

    private final LoadHelper load;
    private final ColorLoader colorLoader;
    private final PaletteDefaults defaults;

    public PaletteInterpolator() {
        load = new LoadHelper();
        colorLoader = new ColorLoader();
        defaults = new PaletteDefaults();
    }

    public PaletteInterpolator(LoadHelper load, ColorLoader colorLoader, PaletteDefaults defaults) {
        this.load = load;
        this.defaults = defaults;
        this.colorLoader = colorLoader;
    }

    public Color borderColor(MapObjectNode node, GeneralParameters p) {
        String colorName = load.aString(node, "border", defaults::borderColor);
        return colorLoader.instantiate(colorName, p);
    }

    public Color nullValueColor(MapObjectNode node, GeneralParameters p) {
        String colorName = load.aString(node, "nullValue", defaults::nullValueColor);
        return colorLoader.instantiate(colorName, p);
    }
}
