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

import nanoverse.compiler.pipeline.instantiate.factory.io.visual.color.palettes.CustomPaletteFactory;
import nanoverse.compiler.pipeline.translate.nodes.MapObjectNode;
import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.io.visual.color.palettes.CustomPalette;
import nanoverse.runtime.layers.LayerManager;

import java.awt.*;
import java.util.Map;

/**
 * Created by dbborens on 11/25/2015.
 */
public class CustomPaletteLoader<T> extends PaletteLoader<CustomPalette<T>> {
    private final CustomPaletteFactory factory;
    private final CustomPaletteInterpolator interpolator;

    public CustomPaletteLoader() {
        factory = new CustomPaletteFactory();
        interpolator = new CustomPaletteInterpolator();
    }

    @Override
    public CustomPalette<T> instantiate(MapObjectNode node, LayerManager lm, GeneralParameters p) {
        Color borderColor = interpolator.borderColor(node, p);
        factory.setBorderColor(borderColor);

        Color nullValueColor = interpolator.nullValueColor(node, p);
        factory.setNullValueColor(nullValueColor);

        Map<String, Color> mappings = interpolator.mappings(node, p);
        factory.setMappings(mappings);
        return factory.build();
    }
}
