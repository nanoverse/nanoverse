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
package nanoverse.compiler.pipeline.instantiate.factory.io.visual.color;

import nanoverse.runtime.io.visual.color.SurfaceGrowthColorManager;
import nanoverse.runtime.io.visual.color.ColorManager;
import nanoverse.runtime.control.arguments.DoubleArgument;
import nanoverse.compiler.pipeline.instantiate.factory.Factory;

public class SurfaceColorModelFactory implements Factory<SurfaceGrowthColorManager> {

    private final SurfaceColorModelFactoryHelper helper;

    private ColorManager base;
    private DoubleArgument luminanceScale;
    private DoubleArgument saturationScale;

    public SurfaceColorModelFactory() {
        helper = new SurfaceColorModelFactoryHelper();
    }

    public SurfaceColorModelFactory(SurfaceColorModelFactoryHelper helper) {
        this.helper = helper;
    }

    public void setBase(ColorManager base) {
        this.base = base;
    }

    public void setLuminanceScale(DoubleArgument luminanceScale) {
        this.luminanceScale = luminanceScale;
    }

    public void setSaturationScale(DoubleArgument saturationScale) {
        this.saturationScale = saturationScale;
    }

    @Override
    public SurfaceGrowthColorManager build() {
        return helper.build(base, luminanceScale, saturationScale);
    }
}