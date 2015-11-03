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

package nanoverse.runtime.layers.continuum.solvers;

import nanoverse.runtime.structural.utilities.MatrixUtils;
import no.uib.cipr.matrix.*;
import no.uib.cipr.matrix.sparse.CompDiagMatrix;

/**
 * Solves the difference relation for steady state using sparse LU decomposition
 * The operator matrix is copied to a band matrix, so this is only suitable for
 * boundary conditions that are NOT periodic in the Y-direction
 *
 * @author Daniel Greenidge
 */
public class EquilibriumBandSolver extends EquilibriumMatrixSolver {

    /**
     * {@inheritDoc}
     */
    public EquilibriumBandSolver(boolean operators) {
        super(operators);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DenseVector ssSolve(Vector source, CompDiagMatrix operator,
                               Vector initial) {
        steadyState(operator);

        int n = operator.numRows();

        // Copy operator to band matrix
        int[] indices = operator.getIndex();
        int superdiagonals = indices[indices.length - 1];
        int subdiagonals = Math.abs(indices[0]);
        BandMatrix a = new BandMatrix(operator, subdiagonals, superdiagonals
            + subdiagonals);

        // Solve with sparse LU decomposition
        DenseMatrix b = new DenseMatrix(source);
        BandLU solver = new BandLU(n, subdiagonals, superdiagonals);
        solver.factor(a);

        try {
            solver.solve(b);
        } catch (MatrixSingularException ex) {
            ex.printStackTrace();
        }

        return new DenseVector(b.getData());
    }


    /**
     * Modifies an operator Q in place to I - Q.
     *
     * @param operator
     * @return
     */
    private void steadyState(Matrix operator) {
        operator.scale(-1.0);
        operator.add(MatrixUtils.compDiagIdentity(operator.numRows()));
    }
}
