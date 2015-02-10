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
import io.visual.color.ColorManager;
import io.visual.color.DefaultColorManager;
import org.dom4j.Element;

/**
 * Created by dbborens on 4/1/14.
 */
public abstract class ColorManagerFactory {

    public static ColorManager instantiate(Element element, GeneralParameters p) {
        // If no color manager is specified, use the default color manager.

        if (element == null) {
            return new DefaultColorManager();
        }

        String className = getClassName(element);
        if (className.equalsIgnoreCase("default")) {
            return new DefaultColorManager();
        } else if (className.equalsIgnoreCase("surface-growth")) {
            return SurfaceGrowthColorManagerFactory.instantiate(element, p);
        } else {
            throw new IllegalArgumentException("Unrecognized color manager class '" + className + "'");
        }
    }

    private static String getClassName(Element element) {
        Element classNameElem = element.element("class");
        if (classNameElem == null) {
            throw new IllegalArgumentException("Missing required argument <class> in item <color>");
        }
        return classNameElem.getTextTrim();
    }
}
