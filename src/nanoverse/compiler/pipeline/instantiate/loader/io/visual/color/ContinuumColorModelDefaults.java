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

package nanoverse.compiler.pipeline.instantiate.loader.io.visual.color;

import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.control.arguments.*;
import nanoverse.runtime.io.visual.color.ColorManager;

/**
 * Created by dbborens on 8/23/2015.
 */
public class ContinuumColorModelDefaults {
    public static final double DEFAULT_MINIMUM_HUE = 0.0;
    public static final double DEFAULT_MAXIMUM_HUE = 0.0;
    public static final double DEFAULT_MINIMUM_SATURATION = 0.0;
    public static final double DEFAULT_MAXIMUM_SATURATION = 0.0;
    public static final double DEFAULT_MINIMUM_LUMINANCE = 0.0;
    public static final double DEFAULT_MAXIMUM_LUMINANCE = 1.0;
    public static final String DEFAULT_BASE_COLOR = "white";

    public Boolean averageLuminance() {
        return false;
    }

    public ColorManager base(GeneralParameters p) {
        UniformColorModelLoader loader = new UniformColorModelLoader();
        return loader.instantiate(DEFAULT_BASE_COLOR, p);
    }

    public DoubleArgument maxHue() {
        return new ConstantDouble(DEFAULT_MAXIMUM_HUE);
    }

    public DoubleArgument minHue() {
        return new ConstantDouble(DEFAULT_MINIMUM_HUE);
    }

    public DoubleArgument minSat() {
        return new ConstantDouble(DEFAULT_MINIMUM_SATURATION);
    }

    public DoubleArgument maxSat() {
        return new ConstantDouble(DEFAULT_MAXIMUM_SATURATION);
    }

    public DoubleArgument minLum() {
        return new ConstantDouble(DEFAULT_MINIMUM_LUMINANCE);
    }

    public DoubleArgument maxLum() {
        return new ConstantDouble(DEFAULT_MAXIMUM_LUMINANCE);
    }
}
