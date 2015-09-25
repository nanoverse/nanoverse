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

import org.dom4j.Element;

import java.awt.*;

/**
 * Created by dbborens on 4/4/14.
 */
public abstract class ColorFactory {

    public static Color instantiate(Element root, Color defaultColor) {
        if (root == null) {
            return defaultColor;
        }

        // For the moment, we only support hex color encoding.
        Element hexElement = root.element("hex");

        if (hexElement == null) {
            throw new IllegalArgumentException("You must specify the hex code for the color you want.");
        }

        String hex = "0x" + hexElement.getTextTrim();
        Color color = Color.decode(hex);
        return color;
    }
}
