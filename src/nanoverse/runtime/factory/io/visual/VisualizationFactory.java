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

package nanoverse.runtime.factory.io.visual;

import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.factory.io.visual.kymograph.KymographFactory;
import nanoverse.runtime.factory.io.visual.map.MapVisualizationFactory;
import nanoverse.runtime.io.visual.MockVisualization;
import nanoverse.runtime.io.visual.Visualization;
import org.dom4j.Element;

/**
 * Created by dbborens on 4/3/14.
 */
public abstract class VisualizationFactory {

    public static Visualization instantiate(Element root, GeneralParameters p) {
        String className = getClassName(root);

        if (className.equalsIgnoreCase("map")) {
            return MapVisualizationFactory.instantiate(root, p);
        } else if (className.equalsIgnoreCase("kymograph")) {
            return KymographFactory.instantiate(root, p);
        } else if (className.equalsIgnoreCase("mock")) {
            return new MockVisualization();
        } else {
            throw new IllegalArgumentException("Unrecognized visualization " +
                    "class '" + className + "'");
        }
    }

    public static String getClassName(Element root) {
        Element cnNode = root.element("class");
        if (cnNode == null) {
            throw new IllegalArgumentException("Missing required argument 'visualization' in 'visualization-serializer' tag");
        }
        String className = cnNode.getTextTrim();
        return className;
    }
}
