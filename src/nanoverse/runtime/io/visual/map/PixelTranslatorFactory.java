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

package nanoverse.runtime.io.visual.map;

import nanoverse.runtime.geometry.Geometry;

/**
 * Created by dbborens on 4/3/14.
 */
public class PixelTranslatorFactory {
    public static PixelTranslator instantiate(Geometry geometry) {
        int d = geometry.getDimensionality();
        int c = geometry.getConnectivity();

        // Rectangular lattice
        if (d == 2 && c == 2) {
            return new RectPixelTranslator();

            // Triangular lattice
        } else if (d == 2 && c == 3) {
            return new TriPixelTranslator();

            // Cubic lattide
        } else if (d == 3 && c == 3) {
            return new CubePixelTranslator();

        } else if (d == 1 && c == 1) {
            return new RectPixelTranslator();

        } else {
            throw new IllegalArgumentException("Unrecognized nanoverse.runtime.geometry " +
                "(dimensionality = " + d + "; connectivity = " + c + ")");
        }
    }
}
