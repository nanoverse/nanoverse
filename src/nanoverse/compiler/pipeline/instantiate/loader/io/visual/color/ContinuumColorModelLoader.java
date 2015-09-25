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

package nanoverse.compiler.pipeline.instantiate.loader.io.visual.color;

import nanoverse.compiler.pipeline.instantiate.factory.io.visual.color.ContinuumColorModelFactory;
import nanoverse.compiler.pipeline.translate.nodes.MapObjectNode;
import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.control.arguments.DoubleArgument;
import nanoverse.runtime.io.visual.color.*;

/**
 * Created by dbborens on 8/10/2015.
 */
public class ContinuumColorModelLoader extends ColorModelLoader<NormalizedContinuumColorManager> {
    private final ContinuumColorModelFactory factory;
    private final ContinuumColorModelInterpolator interpolator;

    public ContinuumColorModelLoader() {
        factory = new ContinuumColorModelFactory();
        interpolator = new ContinuumColorModelInterpolator();
    }

    public ContinuumColorModelLoader(ContinuumColorModelFactory factory,
                                     ContinuumColorModelInterpolator interpolator) {
        this.factory = factory;
        this.interpolator = interpolator;
    }

    @Override
    public ColorManager instantiate(MapObjectNode node, GeneralParameters p) {
        boolean averageLuminance = interpolator.averageLuminance(node, p.getRandom());
        factory.setAverageLuminance(averageLuminance);

        ColorManager base = interpolator.base(node, p);
        factory.setBase(base);

        String continuumId = interpolator.id(node);
        factory.setContinuumId(continuumId);

        DoubleArgument maxHueArg = interpolator.maxHue(node, p.getRandom());
        factory.setMaxHueArg(maxHueArg);

        DoubleArgument minHueArg = interpolator.minHue(node, p.getRandom());
        factory.setMinHueArg(minHueArg);

        DoubleArgument maxSaturationArg = interpolator.maxSat(node, p.getRandom());
        factory.setMaxSaturationArg(maxSaturationArg);

        DoubleArgument minSaturationArg = interpolator.minSat(node, p.getRandom());
        factory.setMinSaturationArg(minSaturationArg);

        DoubleArgument maxLuminanceArg = interpolator.maxLum(node, p.getRandom());
        factory.setMaxLuminanceArg(maxLuminanceArg);

        DoubleArgument minLuminanceArg = interpolator.minLum(node, p.getRandom());
        factory.setMinLuminanceArg(minLuminanceArg);

        return factory.build();
    }
}
