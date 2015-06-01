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

package factory.io.visual.color;

import control.GeneralParameters;
import control.arguments.Argument;
import factory.control.arguments.DoubleArgumentFactory;
import io.visual.color.NormalizedContinuumColorManager;
import org.dom4j.Element;

/**
 * Created by dbborens on 6/1/2015.
 */
public abstract class NormalizedContinuumColorManagerFactory {

    public static final double DEFAULT_MINIMUM_HUE = 0.0;
    public static final double DEFAULT_MAXIMUM_HUE = 0.0;
    public static final double DEFAULT_MINIMUM_SATURATION = 0.0;
    public static final double DEFAULT_MAXIMUM_SATURATION = 0.0;
    public static final double DEFAULT_MINIMUM_LUMINANCE = 0.0;
    public static final double DEFAULT_MAXIMUM_LUMINANCE = 1.0;

    public static NormalizedContinuumColorManager instantiate(Element e, GeneralParameters p) {
        Argument<Double> minHueArg = DoubleArgumentFactory.instantiate(e, "min-hue", DEFAULT_MINIMUM_HUE, p.getRandom());
        Argument<Double> maxHueArg = DoubleArgumentFactory.instantiate(e, "max-hue", DEFAULT_MAXIMUM_HUE, p.getRandom());
        Argument<Double> minSatArg = DoubleArgumentFactory.instantiate(e, "min-saturation", DEFAULT_MINIMUM_SATURATION, p.getRandom());
        Argument<Double> maxSatArg = DoubleArgumentFactory.instantiate(e, "max-saturation", DEFAULT_MAXIMUM_SATURATION, p.getRandom());
        Argument<Double> minLumArg = DoubleArgumentFactory.instantiate(e, "min-luminance", DEFAULT_MINIMUM_LUMINANCE, p.getRandom());
        Argument<Double> maxLumArg = DoubleArgumentFactory.instantiate(e, "max-luminance", DEFAULT_MAXIMUM_LUMINANCE, p.getRandom());
        String continuumId = e.element("continuum").getTextTrim();
        return new NormalizedContinuumColorManager(minHueArg, maxHueArg, minSatArg, maxSatArg, minLumArg, maxLumArg, continuumId);
    }
}
