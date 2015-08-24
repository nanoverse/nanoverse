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

package compiler.pipeline.instantiate.loader.io.visual.color;

import compiler.error.UnrecognizedIdentifierError;
import compiler.pipeline.instantiate.loader.Loader;
import compiler.pipeline.translate.nodes.MapObjectNode;
import control.GeneralParameters;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.awt.*;

/**
 * Created by dbborens on 8/23/2015.
 */
public class ColorLoader extends Loader<Color> {

    public Color instantiate(String colorStr, GeneralParameters p) {
        if (colorStr.startsWith("#")) {
            return hexInstantiate(colorStr);
        }

        switch(colorStr.trim().toLowerCase()) {
            case "white":
                return Color.WHITE;
            case "light_gray":
                return Color.LIGHT_GRAY;
            case "light_grey":
                return Color.LIGHT_GRAY;
            case "gray":
                return Color.GRAY;
            case "grey":
                return Color.GRAY;
            case "dark_gray":
                return Color.DARK_GRAY;
            case "dark_grey":
                return Color.DARK_GRAY;
            case "black":
                return Color.BLACK;
            case "red":
                return Color.RED;
            case "pink":
                return Color.PINK;
            case "orange":
                return Color.ORANGE;
            case "yellow":
                return Color.YELLOW;
            case "green":
                return Color.GREEN;
            case "magenta":
                return Color.MAGENTA;
            case "cyan":
                return Color.CYAN;
            case "blue":
                return Color.BLUE;
            default:
                throw new UnrecognizedIdentifierError("Unknown color \"" + colorStr + "\"");
        }
    }

    private Color hexInstantiate(String colorStr) {
        String str = "0x" + colorStr.substring(1).trim();
        return Color.decode(str);
    }
}
