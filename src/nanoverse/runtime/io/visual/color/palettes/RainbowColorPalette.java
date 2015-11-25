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
import java.util.HashMap;
import java.util.List;
import java.util.stream.*;

/**
 * Created by dbborens on 10/27/2015.
 */
public class RainbowColorPalette<T> extends SequentialPalette<T> {

    public static final List<Color> ELEMENTS = Stream.of(
            Color.RED,
            Color.PINK,
            Color.ORANGE,
            Color.YELLOW,
            Color.GREEN,
            Color.BLUE,
            Color.MAGENTA
    ).collect(Collectors.toList());

    @FactoryTarget
    public RainbowColorPalette(Color nullValueColor,
                               Color borderColor) {
        super(nullValueColor, borderColor);
    }

    @Override
    protected List<Color> resolveElements() {
        return ELEMENTS;
    }
}
