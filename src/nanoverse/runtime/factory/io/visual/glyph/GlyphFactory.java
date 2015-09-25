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

package nanoverse.runtime.factory.io.visual.glyph;

import nanoverse.runtime.factory.io.visual.color.ColorFactory;
import nanoverse.runtime.io.visual.glyph.MockGlyph;
import nanoverse.runtime.io.visual.highlight.*;
import nanoverse.runtime.structural.utilities.XmlUtil;
import org.dom4j.Element;

import java.awt.*;

/**
 * Created by dbborens on 4/3/14.
 */
public abstract class GlyphFactory {

    public static Glyph instantiate(Element glyphRoot) {
        String className = getClassName(glyphRoot);
        if (className.equalsIgnoreCase("mock")) {
            return new MockGlyph();
        } else if (className.equalsIgnoreCase("dot")) {
            return dotGlyph(glyphRoot);
        } else if (className.equalsIgnoreCase("bullseye")) {
            return bullseyeGlyph(glyphRoot);
        } else if (className.equalsIgnoreCase("crosshairs")) {
            return crosshairsGlyph(glyphRoot);
        } else {
            throw new IllegalArgumentException("Unrecognized glyph class '" +
                className + "'");
        }
    }

    private static Glyph crosshairsGlyph(Element glyphRoot) {
        Color color = getColor(glyphRoot, "color", Color.WHITE);

        double circle = XmlUtil.getDouble(glyphRoot, "circle", 0.15);

        // Remember: cross is specified in terms of the size of the circle
        double cross = XmlUtil.getDouble(glyphRoot, "cross", 1.5);

        return new CrosshairsGlyph(color, circle, cross);

    }

    private static Glyph bullseyeGlyph(Element glyphRoot) {
        Color primary = getColor(glyphRoot, "primary-color", Color.RED);
        Color secondary = getColor(glyphRoot, "secondary-color", Color.WHITE);
        double size = XmlUtil.getDouble(glyphRoot, "size", 0.3);

        return new BullseyeGlyph(primary, secondary, size);
    }

    private static Glyph dotGlyph(Element glyphRoot) {
        Color color = getColor(glyphRoot, "color", Color.WHITE);
        double size = XmlUtil.getDouble(glyphRoot, "size", 0.1);
        return new DotGlyph(color, size);
    }

    private static Color getColor(Element glyphRoot, String colorRoot,
                                  Color defaultColor) {
        Element colorElement = glyphRoot.element(colorRoot);
        return ColorFactory.instantiate(colorElement, defaultColor);

    }

    private static String getClassName(Element glyphRoot) {
        Element cnElem = glyphRoot.element("class");
        String className = cnElem.getTextTrim();
        return className;
    }
}
