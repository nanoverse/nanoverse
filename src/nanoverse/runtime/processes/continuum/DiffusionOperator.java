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

package nanoverse.runtime.processes.continuum;

import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.geometry.Geometry;
import no.uib.cipr.matrix.sparse.CompDiagMatrix;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.IntStream;

/**
 * Created by dbborens on 1/24/15.
 */
public class DiffusionOperator extends CompDiagMatrix {

    public DiffusionOperator(DiffusionConstantHelper helper, Geometry geometry) {
        this(geometry.getCanonicalSites().length);
        load(helper, geometry);
    }

    private DiffusionOperator(int n) {
        super(n, n);

    }

    private void load(DiffusionConstantHelper helper, Geometry geometry) {
        Coordinate[] sites = geometry.getCanonicalSites();
        int n = sites.length;
        Function<Coordinate, Integer> indexer = geometry.getIndexer();
        IntStream.range(0, n)
            .boxed()
            .forEach(j -> {
                Coordinate coord = sites[j];

                // Set the diagonal value
                add(j, j, helper.getDiagonalValue());

                // Set each neighbor. For reflecting boundary conditions, one or
                // more neighbors may be the diagonal.
                Arrays.asList(geometry.getNeighbors(coord, Geometry.APPLY_BOUNDARIES))
                    .stream()
                    .map(indexer)
                    .forEach(i -> add(i, j, helper.getNeighborValue()));

            });
    }
}
