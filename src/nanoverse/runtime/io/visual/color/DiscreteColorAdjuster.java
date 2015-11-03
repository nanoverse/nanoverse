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

import nanoverse.runtime.control.halt.HaltCondition;
import nanoverse.runtime.io.visual.HSLColor;

import java.awt.*;

/**
 * Created by dbborens on 10/27/2015.
 */
public class DiscreteColorAdjuster {
    private final float sScale, lScale;

    public DiscreteColorAdjuster(float sScale, float lScale) {
        this.sScale = sScale;
        this.lScale = lScale;
    }

    public Color adjustColor(Color baseColor) {
        HSLColor baseHSL = new HSLColor(baseColor);

        float saturation = adjust(baseHSL.getSaturation(), sScale);
        float luminance = adjust(baseHSL.getLuminance(), lScale);
        float hue = baseHSL.getHue();
        Color adjusted = HSLColor.toRGB(hue, saturation, luminance);

        return adjusted;
    }

    private float adjust(float original, float scale) {
        float uncapped = original * scale;
        return cap(uncapped);
    }

    private float cap(float value) {
        return value > 1.0F ? 1.0F : value;
    }
}
