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

package nanoverse.runtime.layers.continuum.solvers;

import no.uib.cipr.matrix.DenseVector;
import no.uib.cipr.matrix.Matrix;
import no.uib.cipr.matrix.Vector;
import no.uib.cipr.matrix.sparse.*;
import nanoverse.runtime.structural.utilities.MatrixUtils;

/**
 * Solves the difference relation for steady state using a conjugate gradient (Krylov) solver)
 *
 * @author Daniel Greenidge
 */
public class EquilibriumKrylovSolver extends EquilibriumMatrixSolver {

    /**
     * {@inheritDoc}
     */
    public EquilibriumKrylovSolver(boolean operators) {
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

        IterativeSolver solver = new CGS(initial);
        Preconditioner preconditioner = new DiagonalPreconditioner(n);
        preconditioner.setMatrix(operator);

        DenseVector sol = new DenseVector(n);

        try {
            solver.solve(operator, source, sol);
        } catch (IterativeSolverNotConvergedException ex) {
            ex.printStackTrace();
        }

        return sol;
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
