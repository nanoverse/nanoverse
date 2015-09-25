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

package nanoverse.runtime.factory.io.visual.color;

import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.control.arguments.DoubleArgument;
import nanoverse.runtime.factory.control.arguments.DoubleArgumentFactory;
import nanoverse.runtime.io.visual.color.*;
import org.dom4j.Element;


/**
 * Created by dbborens on 7/25/14.
 */
public abstract class SurfaceGrowthColorManagerFactory {

    public static double DEFAULT_SATURATION_SCALING = 0.5;
    public static double DEFAULT_LUMINANCE_SCALING = 1.0;

    public static SurfaceGrowthColorManager instantiate(Element e, GeneralParameters p) {
        ColorManager base = instantiateBase(e, p);
        DoubleArgument luminanceScale = DoubleArgumentFactory.instantiate(e, "luminance-scale", DEFAULT_LUMINANCE_SCALING, p.getRandom());
        DoubleArgument saturationScale = DoubleArgumentFactory.instantiate(e, "saturation-scale", DEFAULT_SATURATION_SCALING, p.getRandom());

        SurfaceGrowthColorManager ret = new SurfaceGrowthColorManager(base, luminanceScale, saturationScale);

        return ret;
    }

    private static ColorManager instantiateBase(Element e, GeneralParameters p) {
        Element baseElement = e.element("base");
        if (baseElement == null) {
            return new DefaultColorManager();
        }

        ColorManager base = ColorManagerFactory.instantiate(baseElement, p);
        return base;
    }
}
