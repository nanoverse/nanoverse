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
import control.identifiers.Coordinate;
import io.visual.HSLColor;
import layers.SystemState;
import structural.annotations.FactoryTarget;

import java.awt.*;

/**
 * Color manager that shows surface cells in a vibrant color, and interior
 * cells in a dull color. This operates as an overlay on an existing color
 * scheme.
 * <p>
 * Created by dbborens on 7/23/14.
 */
public class SurfaceGrowthColorManager extends ColorManager {

    private ColorManager base;
    private Argument<Double> luminanceScale;
    private Argument<Double> saturationScale;

    @FactoryTarget(displayName = "SurfaceColorModel")
    public SurfaceGrowthColorManager(ColorManager base,
                                     Argument<Double> luminanceScale,
                                     Argument<Double> saturationScale) {
        this.base = base;
        this.saturationScale = saturationScale;
        this.luminanceScale = luminanceScale;
    }

    @Override
    public Color getColor(Coordinate c, SystemState systemState) {
        Color baseColor = base.getColor(c, systemState);

        // Check whether point is interior or exterior
        boolean isInterior = checkIsInterior(c, systemState);

        if (isVacant(systemState, c)) {
            return Color.BLACK;
        }
        // Scale lightness appropriately
        if (isInterior) {
            return adjustColor(baseColor);
        } else {
            return baseColor;
        }
    }

    private boolean isVacant(SystemState systemState, Coordinate c) {
        if (systemState.getLayerManager().getCellLayer().getViewer().isOccupied(c)) {
            return false;
        }

        return true;
    }

    private Color adjustColor(Color baseColor) {
        HSLColor baseHSL = new HSLColor(baseColor);
        float originalSaturation = baseHSL.getSaturation();
        float originalLuminance = baseHSL.getLuminance();
        float hue = baseHSL.getHue();

        float sScale, lScale;
        try {
            sScale = saturationScale.next().floatValue();
        } catch (HaltCondition ex) {
            throw new IllegalStateException(ex);
        }

        float saturation = originalSaturation * sScale;
        if (saturation > 100.0) {
            saturation = 100.0F;
        }

        try {
            lScale = luminanceScale.next().floatValue();
        } catch (HaltCondition ex) {
            throw new IllegalStateException(ex);
        }

        float luminance = originalLuminance * lScale;
        if (luminance > 100.0) {
            luminance = 100.0F;
        }

        Color adjusted = HSLColor.toRGB(hue, saturation, luminance);
        return adjusted;
    }

    private boolean checkIsInterior(Coordinate c, SystemState systemState) {
        int[] neighborStates = systemState.getLayerManager().getCellLayer().getLookupManager().getNeighborStates(c, false);

        // If any neighbor is 0 (vacant), the point is not interior
        for (int neighborState : neighborStates) {
            if (neighborState == 0) {
                return false;
            }
        }
        // If none of the neighbors are vacant, the point is interior
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SurfaceGrowthColorManager)) return false;

        SurfaceGrowthColorManager that = (SurfaceGrowthColorManager) o;

        if (!base.equals(that.base)) return false;
        if (!luminanceScale.equals(that.luminanceScale)) return false;
        if (!saturationScale.equals(that.saturationScale)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = base.hashCode();
        result = 31 * result + luminanceScale.hashCode();
        result = 31 * result + saturationScale.hashCode();
        return result;
    }

    @Override
    public Color getBorderColor() {
        return Color.DARK_GRAY;
    }
}
