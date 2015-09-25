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

package nanoverse.compiler.pipeline.instantiate.loader.control.identifiers;

import nanoverse.runtime.geometry.Geometry;

/**
 * Created by dbborens on 8/12/15.
 */
public class CoordinateAdapter {

    public CoordinateSubclassLoader getLoader(Geometry geom) {
        if (geom.getDimensionality() == 1) {
            return new Coordinate1DLoader();
        } else if (geom.getDimensionality() == 2) {
            return new Coordinate2DLoader();
        } else if (geom.getDimensionality() == 3) {
            return new Coordinate3DLoader();
        }

        throw new UnsupportedOperationException("This version of Nanoverse " +
            "supports only 1D, 2D, and 3D geometries.");
    }
}
