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

package nanoverse.runtime.io.visual.color.palettes;

import nanoverse.runtime.structural.annotations.FactoryTarget;

import java.awt.*;
import java.util.Map;

/**
 * Created by dbborens on 11/25/2015.
 */
public class CustomPalette<T> extends Palette<T> {

    private final Map<T, Color> mappings;
    private final Color defaultColor;

    @FactoryTarget
    public CustomPalette(Color nullValueColor, Color borderColor, Color defaultColor, Map<T, Color> mappings) {
        super(nullValueColor, borderColor);
        this.mappings = mappings;
        this.defaultColor = defaultColor;
    }

    @Override
    public Color apply(T t) {
        if (t == null) {
            return nullValueColor;
        } else if (!mappings.containsKey(t)) {
            return defaultColor;
        }

        return mappings.get(t);
    }
}
