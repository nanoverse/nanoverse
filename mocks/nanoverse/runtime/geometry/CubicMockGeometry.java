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

package nanoverse.runtime.geometry;

import nanoverse.runtime.control.identifiers.*;

import java.util.ArrayList;

/**
 * Created by David B Borenstein on 12/24/13.
 */
public class CubicMockGeometry extends MockGeometry {
    // Used to emulate a boundary condition. Note that the mock BC only
    // is defined along one edge, so use with caution.
    private int width = -1;

    public CubicMockGeometry() {
        super();
        setConnectivity(3);
        setDimensionality(3);
    }

    @Override
    public Coordinate[] getNeighbors(Coordinate coord, int mode) {
        if (coord.hasFlag(Flags.PLANAR))
            throw new IllegalStateException(coord.toString());

        ArrayList<Coordinate> neighbors = new ArrayList<Coordinate>();

        int x = coord.x();
        int y = coord.y();
        int z = coord.z();

        consider(neighbors, x + 1, y, z);
        consider(neighbors, x - 1, y, z);
        consider(neighbors, x, y + 1, z);
        consider(neighbors, x, y - 1, z);
        consider(neighbors, x, y, z + 1);
        consider(neighbors, x, y, z - 1);

        return neighbors.toArray(new Coordinate[0]);
    }

    public Coordinate rel2abs(Coordinate coord, Coordinate displacement, int mode) {
        // Only works in x direction
        int x1 = coord.x() + displacement.x();

        if (width > 0 && x1 >= width) {
            return null;
        }
        int y1 = coord.y();
        int z1 = coord.z();

        int f1 = coord.flags();

        return new Coordinate3D(x1, y1, z1, f1);
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
