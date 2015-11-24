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

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by dbborens on 11/23/2015.
 */
public abstract class SequentialPalette<T> extends Palette<T> {

    private final List<Color> elements;
    private final HashMap<T, Color> colorsByName;
    private int index = 0;

    public SequentialPalette(Color nullValueColor, Color borderColor) {
        super(nullValueColor, borderColor);
        elements = resolveElements();
        colorsByName = new HashMap<>();
    }

    protected abstract List<Color> resolveElements();

    @Override
    public Color apply(T value) {
        if (value == null) {
            return nullValueColor;
        }
        createColorIfNew(value);
        return colorsByName.get(value);
    }

    private void createColorIfNew(T value) {
        if (!colorsByName.containsKey(value)) {
            Color color = getNextColor();
            colorsByName.put(value, color);
        }
    }

    private Color getNextColor() {
        Color ret = elements.get(index);
        index = (index + 1) % elements.size();
        return ret;
    }
}
