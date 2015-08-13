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

import control.arguments.*;
import control.halt.HaltCondition;
import control.identifiers.*;
import io.visual.HSLColor;
import layers.SystemState;
import structural.annotations.FactoryTarget;

import java.awt.*;
import java.awt.image.ColorModel;
import java.util.HashSet;

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
    private final ColorManager base;
    private final boolean averageLuminance;

    @FactoryTarget(displayName = "ContinuumColorModel")
    public NormalizedContinuumColorManager(Argument<Double> minHueArg,
                                           Argument<Double> maxHueArg,
                                           Argument<Double> minSaturationArg,
                                           Argument<Double> maxSaturationArg,
                                           Argument<Double> minLuminanceArg,
                                           Argument<Double> maxLuminanceArg,
                                           String continuumId,
                                           boolean averageLuminance,
                                           ColorManager base) {

        this.base = base;
        this.averageLuminance = averageLuminance;
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
                                           ContinuumNormalizationHelper normalizer,
                                           boolean averageLuminance,
                                           ColorManager base) {

        this.base = base;
        this.normalizer = normalizer;
        this.averageLuminance = averageLuminance;

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
        Color baseColor = base.getColor(c, systemState);
        Color overlayColor = getOverlayColor(c, systemState);

        return blend(baseColor, overlayColor);
    }

    private Color blend(Color baseColor, Color overlayColor) {
        float hue = blendHue(baseColor, overlayColor);
        float saturation = blendSaturation(baseColor, overlayColor);
        float luminance = blendLuminance(baseColor, overlayColor);
        return HSLColor.toRGB(hue, saturation, luminance);
    }

    // TODO This might have funny behavior when colors are near each other on annulus. Try it out.
    private float blendHue(Color baseColor, Color overlayColor) {
        // TODO Need to implement a color blender helper
        float baseHue = HSLColor.fromRGB(baseColor)[0] / 360.0F;
//        float overHue = HSLColor.fromRGB(overlayColor)[0] / 360.0F;
//        float hue = (baseHue + overHue) / 2.0F * 360F;
//        return hue;
        return baseHue * 360F;
    }

    private float blendSaturation(Color baseColor, Color overlayColor) {
        float baseSat = HSLColor.fromRGB(baseColor)[1] / 100.0F;
        float overSat = HSLColor.fromRGB(overlayColor)[1] / 100.0F;
        return (baseSat + overSat) / 2.0F * 100.0F;
    }

    /**
     * Blend luminance, either by averaging or compositing.
     * @param baseColor
     * @param overlayColor
     * @return
     */
    private float blendLuminance(Color baseColor, Color overlayColor) {
        float baseLuminance = HSLColor.fromRGB(baseColor)[2] / 100.0F;
        float overlayLuminance = HSLColor.fromRGB(overlayColor)[2] / 100.0F;

        // See HSLColor.java for explanation of scalings below
        if (averageLuminance) {
            return (baseLuminance + overlayLuminance) / 2.0F * 100.0F;
        } else {
            return baseLuminance * overlayLuminance * 100.0F;
        }
    }

    private Color getOverlayColor(Coordinate c, SystemState systemState) {
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
