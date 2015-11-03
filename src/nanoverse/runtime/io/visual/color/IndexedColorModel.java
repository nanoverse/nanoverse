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

package nanoverse.runtime.io.visual.color;

import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.io.visual.color.palettes.RainbowColorPalette;
import nanoverse.runtime.layers.SystemState;
import nanoverse.runtime.structural.annotations.FactoryTarget;

import java.awt.*;
import java.util.HashMap;
import java.util.function.Supplier;

/**
 * Created by dbborens on 4/1/14.
 */
public class IndexedColorModel extends ColorManager {
    public static final Color BORDER_COLOR = Color.DARK_GRAY;

    private final HashMap<String, Color> colorsByName;
    private final Supplier<Color> palette;

    public IndexedColorModel() {
        colorsByName = new HashMap<>();
        palette = new RainbowColorPalette();
    }

    public IndexedColorModel(HashMap<String, Color> colorsByName,
                             Supplier<Color> palette) {

        this.colorsByName = colorsByName;
        this.palette = palette;
    }

    @Override
    public Color getColor(Coordinate c, SystemState systemState) {
        String name = systemState.getLayerManager().getAgentLayer().getViewer().getName(c);
        createColorIfNew(name);
        return colorsByName.get(name);
    }

    private void createColorIfNew(String name) {
        if (!colorsByName.containsKey(name)) {
            Color value = palette.get();
            colorsByName.put(name, value);
        }
    }

    @Override
    public Color getBorderColor() {
        return BORDER_COLOR;
    }
}
