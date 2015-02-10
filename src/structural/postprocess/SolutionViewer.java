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

package structural.postprocess;

import control.identifiers.Coordinate;
import control.identifiers.Flags;
import geometry.Geometry;
import no.uib.cipr.matrix.DenseVector;

/**
 * Read-only wrapper for solutions to continuum processes.
 * If the requested coordinate is out of bounds, it returns 0D.
 * <p>
 * Created by dbborens on 12/16/13.
 */
public class SolutionViewer {
    private final DenseVector solution;
    private final Geometry geometry;

    public SolutionViewer(DenseVector solution, Geometry geometry) {
        this.solution = solution;
        this.geometry = geometry;
    }

    public double getRelative(Coordinate offset) {
        if (!offset.hasFlag(Flags.VECTOR)) {
            throw new IllegalStateException("SolutionViewer::getRelative expects an offset, not an absolute coordinate.");
        }

        Coordinate converted = applyOffset(offset);

        //System.out.println("Looking up index of " + converted);
        Integer index = geometry.getIndexer().apply(converted);
        if (index == null) {
            return 0D;
        } else {
            return solution.get(index);
        }
    }

    public double getAbsolute(Coordinate coordinate) {
        if (coordinate.hasFlag(Flags.VECTOR)) {
            throw new IllegalStateException("getAbsolute expects an absolute coordinate, not an offset.");
        }

        Integer index = geometry.getIndexer().apply(coordinate);
        return solution.get(index);
    }

    private Coordinate applyOffset(Coordinate offset) {
        Coordinate center = geometry.getCenter();

        Coordinate result;

        int x = center.x() + offset.x();
        int y = center.y() + offset.y();

        if (center.hasFlag(Flags.PLANAR)) {
            result = new Coordinate(x, y, 0);
        } else {
            int z = center.z() + offset.z();
            result = new Coordinate(x, y, z, 0);
        }

        return result;
    }

    public DenseVector getSolution() {
        return solution;
    }
}
