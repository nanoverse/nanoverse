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

package io.visual.color;

import control.arguments.Argument;
import control.halt.HaltCondition;
import control.identifiers.*;
import io.visual.HSLColor;
import layers.SystemState;

import java.awt.*;

/**
 * Given some range of values, scales color intensity
 * according to some continuous field.
 *
 * Created by dbborens on 5/31/2015.
 */
public class NormalizedContinuumColorManager extends ColorManager {

    private final float minHue;
    private final float maxHue;
    private final float minLuminance;
    private final float maxLuminance;
    private final float minSaturation;
    private final float maxSaturation;
    private final ContinuumNormalizationHelper normalizer;

    public NormalizedContinuumColorManager(Argument<Double> minHueArg,
                                           Argument<Double> maxHueArg,
                                           Argument<Double> minSaturationArg,
                                           Argument<Double> maxSaturationArg,
                                           Argument<Double> minLuminanceArg,
                                           Argument<Double> maxLuminanceArg,
                                           String continuumId) {

        normalizer = new ContinuumNormalizationHelper(continuumId);

        try {
            this.minHue = minHueArg.next().floatValue();
            this.maxHue = maxHueArg.next().floatValue();
            this.minLuminance = minLuminanceArg.next().floatValue();
            this.maxLuminance = maxLuminanceArg.next().floatValue();
            this.minSaturation = minSaturationArg.next().floatValue();
            this.maxSaturation = maxSaturationArg.next().floatValue();
        } catch (HaltCondition haltCondition) {
            throw new IllegalStateException("Halt condition thrown after end of simulation", haltCondition);
        }
    }

    public NormalizedContinuumColorManager(Argument<Double> minHueArg,
                                           Argument<Double> maxHueArg,
                                           Argument<Double> minSaturationArg,
                                           Argument<Double> maxSaturationArg,
                                           Argument<Double> minLuminanceArg,
                                           Argument<Double> maxLuminanceArg,
                                           ContinuumNormalizationHelper normalizer) {

        this.normalizer = normalizer;

        try {
            this.minHue = minHueArg.next().floatValue();
            this.maxHue = maxHueArg.next().floatValue();
            this.minSaturation = minSaturationArg.next().floatValue();
            this.maxSaturation = maxSaturationArg.next().floatValue();
            this.minLuminance = minLuminanceArg.next().floatValue();
            this.maxLuminance = maxLuminanceArg.next().floatValue();
        } catch (HaltCondition haltCondition) {
            throw new IllegalStateException("Halt condition thrown after end of simulation", haltCondition);
        }
    }

    @Override
    public Color getColor(Coordinate c, SystemState systemState) {
        float normalized = (float) normalizer.normalize(c, systemState);

        // See HSLColor.java for explanation of scalings used below
        float hue = applyScale(normalized, minHue, maxHue) * 360F;
        float saturation = applyScale(normalized, minSaturation, maxSaturation) * 100F;
        float luminance = applyScale(normalized, minLuminance, maxLuminance) * 100F;

        Color adjusted = HSLColor.toRGB(hue, saturation, luminance);
        return adjusted;
    }

    private float applyScale(float percent, float min, float max) {
        float range = max - min;
        float value = (range * percent) + min;
        return value;
    }


    @Override
    public Color getBorderColor() {
        return Color.DARK_GRAY;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NormalizedContinuumColorManager that = (NormalizedContinuumColorManager) o;

        if (Float.compare(that.minHue, minHue) != 0) return false;
        if (Float.compare(that.maxHue, maxHue) != 0) return false;
        if (Float.compare(that.minLuminance, minLuminance) != 0) return false;
        if (Float.compare(that.maxLuminance, maxLuminance) != 0) return false;
        if (Float.compare(that.minSaturation, minSaturation) != 0) return false;
        if (Float.compare(that.maxSaturation, maxSaturation) != 0) return false;
        return normalizer.equals(that.normalizer);

    }

    @Override
    public int hashCode() {
        int result = (minHue != +0.0f ? Float.floatToIntBits(minHue) : 0);
        result = 31 * result + (maxHue != +0.0f ? Float.floatToIntBits(maxHue) : 0);
        result = 31 * result + (minLuminance != +0.0f ? Float.floatToIntBits(minLuminance) : 0);
        result = 31 * result + (maxLuminance != +0.0f ? Float.floatToIntBits(maxLuminance) : 0);
        result = 31 * result + (minSaturation != +0.0f ? Float.floatToIntBits(minSaturation) : 0);
        result = 31 * result + (maxSaturation != +0.0f ? Float.floatToIntBits(maxSaturation) : 0);
        result = 31 * result + normalizer.hashCode();
        return result;
    }
}
