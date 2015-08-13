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
package compiler.pipeline.instantiate.factory.io.visual.color;

import control.arguments.Argument;
import io.visual.color.ColorManager;
import io.visual.color.NormalizedContinuumColorManager;
import control.arguments.DoubleArgument;
import compiler.pipeline.instantiate.factory.Factory;

public class ContinuumColorModelFactory implements Factory<NormalizedContinuumColorManager> {

    private final ContinuumColorModelFactoryHelper helper;

    private Argument<Double> minHueArg;
    private Argument<Double> maxHueArg;
    private Argument<Double> minSaturationArg;
    private Argument<Double> maxSaturationArg;
    private Argument<Double> minLuminanceArg;
    private Argument<Double> maxLuminanceArg;
    private String continuumId;
    private boolean averageLuminance;
    private ColorManager base;

    public ContinuumColorModelFactory() {
        helper = new ContinuumColorModelFactoryHelper();
    }

    public ContinuumColorModelFactory(ContinuumColorModelFactoryHelper helper) {
        this.helper = helper;
    }

    public void setMinHueArg(Argument<Double> minHueArg) {
        this.minHueArg = minHueArg;
    }

    public void setMaxHueArg(Argument<Double> maxHueArg) {
        this.maxHueArg = maxHueArg;
    }

    public void setMinSaturationArg(Argument<Double> minSaturationArg) {
        this.minSaturationArg = minSaturationArg;
    }

    public void setMaxSaturationArg(Argument<Double> maxSaturationArg) {
        this.maxSaturationArg = maxSaturationArg;
    }

    public void setMinLuminanceArg(Argument<Double> minLuminanceArg) {
        this.minLuminanceArg = minLuminanceArg;
    }

    public void setMaxLuminanceArg(Argument<Double> maxLuminanceArg) {
        this.maxLuminanceArg = maxLuminanceArg;
    }

    public void setContinuumId(String continuumId) {
        this.continuumId = continuumId;
    }

    public void setAverageLuminance(boolean averageLuminance) {
        this.averageLuminance = averageLuminance;
    }

    public void setBase(ColorManager base) {
        this.base = base;
    }

    @Override
    public NormalizedContinuumColorManager build() {
        return helper.build(minHueArg, maxHueArg, minSaturationArg, maxSaturationArg, minLuminanceArg, maxLuminanceArg, continuumId, averageLuminance, base);
    }
}