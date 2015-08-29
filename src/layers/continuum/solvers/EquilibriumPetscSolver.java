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

package layers.continuum.solvers;

import no.uib.cipr.matrix.DenseVector;
import no.uib.cipr.matrix.Matrix;
import no.uib.cipr.matrix.Vector;
import no.uib.cipr.matrix.sparse.CompDiagMatrix;
import structural.utilities.MatrixUtils;

/**
 * Solves the difference relation for steady state using PETSc
 *
 * @author Daniel Greenidge
 */
public class EquilibriumPetscSolver extends EquilibriumMatrixSolver {

    /**
     * {@inheritDoc}
     */
    public EquilibriumPetscSolver(boolean operators) {
        super(operators);
    }

    static {
        System.out.println(System.getProperty("java.library.path"));
        System.loadLibrary("solver");
    }

    @Override
    DenseVector ssSolve(Vector source, CompDiagMatrix operator, Vector initial) {
        steadyState(operator);
        double[] solution = new double[source.size()];
        solve(operator.numRows(), operator.getIndex(),
                operator.getDiagonals(), solution,
                ((DenseVector) source).getData());
        DenseVector sol = new DenseVector(solution);
        return sol;
    }

    /**
     * Solves the system Ax=b using PETSc
     *
     * @param n the dimension of the system
     * @param indices the indices of A from CompDiagMatrix.getIndex()
     * @param diagonals the diagonals of A from CompDiagMatrix.getDiagonals()
     * @param rhs the right-hand side of Ax=b
     */
    private native int solve(int n, int[] indices, double[][] diagonals,
                              double[] solution, double[] rhs);

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
