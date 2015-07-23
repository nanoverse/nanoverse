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

package processes.discrete.filter;

import control.arguments.*;
import control.halt.HaltCondition;
import control.identifiers.Coordinate;
import layers.cell.CellLayer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Removes all individuals at greater than a certain depth from the surface
 * of the population (i.e., the nearest vacant site).
 * <p>
 * Created by dbborens on 8/1/14.
 */
public class DepthFilter extends Filter {

    private IntegerArgument maxDepth;
    private CellLayer layer;

    public DepthFilter(CellLayer layer, IntegerArgument maxDepth) {
        this.maxDepth = maxDepth;
        this.layer = layer;
    }

    @Override
    public List<Coordinate> apply(List<Coordinate> toFilter) {
        // Get current depth value.

        int depth;
        try {
            depth = maxDepth.next();
        } catch (HaltCondition ex) {
            throw new IllegalStateException(ex);
        }
        // We have to use a set because, with some boundary conditions, we could
        // see the same site twice.
        HashSet<Coordinate> set = new HashSet<>(toFilter.size());

        // Consider each set in the unfiltered pool.
        for (Coordinate c : toFilter) {

            // Find the nearest vacancies within a radius up to and including
            // the maximum permissible. If a cell has vacant neighbors, it is at
            // the surface (depth = 0), and if it is next to cells with vacant
            // neigbhors, it is at depth 1. But the argument for getNearestVacancies
            // is an exclusive boundary, so add 1 to the argument.
            Coordinate[] vacancies = layer.getLookupManager().getNearestVacancies(c, depth + 1);

            // If any vacancies were found within the permissible depth, then
            // the coordinate meets requirements.
            if (vacancies.length > 0) {
                set.add(c);
            }
        }

        List<Coordinate> ret = new ArrayList<>(set);
        return ret;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DepthFilter)) return false;

        DepthFilter that = (DepthFilter) o;

        if (!maxDepth.equals(that.maxDepth)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return maxDepth.hashCode();
    }
}
