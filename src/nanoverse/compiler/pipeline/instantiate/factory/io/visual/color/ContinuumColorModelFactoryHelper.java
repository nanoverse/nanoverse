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
package nanoverse.compiler.pipeline.instantiate.factory.io.visual.color;

import nanoverse.runtime.control.arguments.DoubleArgument;
import nanoverse.runtime.io.visual.color.ColorManager;
import nanoverse.runtime.io.visual.color.ContinuumColorModel;


public class ContinuumColorModelFactoryHelper {

    public ContinuumColorModel build(DoubleArgument minHueArg, DoubleArgument maxHueArg, DoubleArgument minSaturationArg, DoubleArgument maxSaturationArg, DoubleArgument minLuminanceArg, DoubleArgument maxLuminanceArg, String continuumId, boolean averageLuminance, ColorManager base) {
        return new ContinuumColorModel(minHueArg, maxHueArg, minSaturationArg, maxSaturationArg, minLuminanceArg, maxLuminanceArg, continuumId, averageLuminance, base);
    }
}