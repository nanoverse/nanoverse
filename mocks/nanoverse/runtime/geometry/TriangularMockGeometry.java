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

package nanoverse.runtime.geometry;

import nanoverse.runtime.control.identifiers.*;

import java.util.ArrayList;

public class TriangularMockGeometry extends MockGeometry {
    // Used to emulate a boundary condition. Note that the mock BC only
    // is defined along one edge, so use with caution.
    private int radius;

    public TriangularMockGeometry() {
        super();
        setConnectivity(3);
        setDimensionality(2);
    }

    @Override
    public Coordinate[] getNeighbors(Coordinate coord, int mode) {
        if (!coord.hasFlag(Flags.PLANAR))
            throw new IllegalStateException();

        ArrayList<Coordinate> neighbors = new ArrayList<Coordinate>();

        int x = coord.x();
        int y = coord.y();

        consider(neighbors, x, y + 1);
        consider(neighbors, x + 1, y + 1);
        consider(neighbors, x + 1, y);
        consider(neighbors, x, y - 1);
        consider(neighbors, x - 1, y - 1);
        consider(neighbors, x - 1, y);

        return neighbors.toArray(new Coordinate[0]);
    }

    public Coordinate rel2abs(Coordinate coord, Coordinate displacement, int mode) {
        // Only works in x direction
        int x1 = coord.x() + displacement.x();
        if (radius > 0 && x1 >= radius) {
            return null;
        }
        int y1 = coord.y();
        int f1 = coord.flags();

        return new Coordinate2D(x1, y1, f1);
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }
}
