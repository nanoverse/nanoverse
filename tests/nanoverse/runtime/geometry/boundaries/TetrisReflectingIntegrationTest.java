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

package nanoverse.runtime.geometry.boundaries;

import nanoverse.runtime.geometry.Geometry;
import nanoverse.runtime.geometry.lattice.*;
import nanoverse.runtime.geometry.shape.*;
import nanoverse.runtime.processes.continuum.*;
import no.uib.cipr.matrix.DenseMatrix;
import org.junit.Test;
import test.TestBase;

/**
 * Integration test for TetrisReflectingBoundary. Checks that the operator
 * matrix produced is correct.
 *
 * @author Daniel Greenidge
 */
public class TetrisReflectingIntegrationTest extends TestBase {
    Lattice lattice = new RectangularLattice();
    Shape shape = new Rectangle(lattice, 3, 3);
    Boundary boundary = new TetrisReflectingBoundary(shape, lattice);
    Geometry geometry = new Geometry(lattice, shape, boundary);

    @Test
    public void itMakesTheCorrectMatrix() throws Exception {
        DiffusionConstantHelper helper = new DiffusionConstantHelper(0.1,
            2, 2);
        DiffusionOperator operator = new DiffusionOperator(helper, geometry);

        double[][] expectedValues =
            {{-0.3, 0.1, 0.1, 0.1, 0, 0, 0, 0, 0},
                {0.1, -0.3, 0.1, 0, 0.1, 0, 0, 0, 0},
                {0.1, 0.1, -0.3, 0, 0, 0.1, 0, 0, 0},
                {0.1, 0, 0, -0.4, 0.1, 0.1, 0.1, 0, 0},
                {0, 0.1, 0, 0.1, -0.4, 0.1, 0, 0.1, 0},
                {0, 0, 0.1, 0.1, 0.1, -0.4, 0, 0, 0.1},
                {0, 0, 0, 0.1, 0, 0, -0.4, 0.1, 0.1},
                {0, 0, 0, 0, 0.1, 0, 0.1, -0.4, 0.1},
                {0, 0, 0, 0, 0, 0.1, 0.1, 0.1, -0.4}};
        DenseMatrix expected = new DenseMatrix(expectedValues);

        assertMatricesEqual(expected, operator, 1e-3);
    }
}
