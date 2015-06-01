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
import no.uib.cipr.matrix.sparse.*;
import structural.utilities.EpsilonUtil;
import structural.utilities.MatrixUtils;


/**
 * Created by dbborens on 12/26/14.
 */
public class EquilibriumMatrixSolver {
    /**
     * Solve the difference relation
     * <p>
     * c_{n+1} = Q c_n + g
     * <p>
     * for steady state, where g is a source vector and Q is an operator matrix.
     * <p>
     * This is accomplished by solving the equation
     * <p>
     * [I - Q] c = g
     * <p>
     * for c. There are several edge cases, however, that can conceivably produce
     * solutions by amplifying error. The solver therefore preempts these by
     * providing precomputed answers in each case.
     *
     * @param source
     * @param operator
     * @param initial
     * @return
     */
    public Vector solve(Vector source, Matrix operator, Vector initial) {
        // If the operator is the identity matrix and the source is zero,
        // return the initial value.SteadyStateHelper
        if (isZeroVector(source) && isIdentity(operator)) {
            return initial.copy();
        }

        // If there are no sources and no initial value, return zero.
        if (isZeroVector(initial) && isZeroVector(source)) {
            return zeroVector(initial);
        }

        // If any site starts with a non-zero concentration and is set to
        // inflate, it is going to diverge; throw an exception.
        if (isExponentialDivergence(initial, operator)) {
            throw new IllegalStateException("Continuum state divergence: non-zero initial value set to " +
                    "exponentiate forever.");
        }

        if (isLinearDivergence(source, operator)) {
            throw new IllegalStateException("Continuum state divergence: a non-decaying site has a positive source " +
                    "term.");
        }

        // If the matrix is not the identity, but every row and column sums to
        // one, then there is no steady state solution.
        if (isDoublyStochastic(operator) && isZeroVector(source)) {
            throw new IllegalStateException("No steady state exists for continuum.");
        }

        // If there is a non-trivial operation with no sources and no
        // divergences, then the only possible solution is c=0.
        if (isZeroVector(source) && !isIdentity(operator)) {
            return zeroVector(initial);
        }

        // In all other cases, just solve the matrix.
        return ssSolve(source, operator, initial);
    }

    private boolean isLinearDivergence(Vector source, Matrix operator) {
        for (int i = 0; i < operator.numRows(); i++) {
            double exp = operator.get(i, i);
            double inj = source.get(i);
            if (exp >= 1.0 && inj > 0) {
                return true;
            }
        }
        return false;
    }

    private boolean isDoublyStochastic(Matrix operator) {
        for (int i = 0; i < operator.numRows(); i++) {

            double rowSum = 0.0;
            double colSum = 0.0;

            for (int j = 0; j < operator.numRows(); j++) {
                rowSum += operator.get(i, j);
                colSum += operator.get(j, i);
            }

            boolean rowEqualsOne = EpsilonUtil.epsilonEquals(1.0, rowSum);
            boolean colEqualsOne = EpsilonUtil.epsilonEquals(1.0, colSum);

            if (!(rowEqualsOne && colEqualsOne)) {
                return false;
            }
        }

        return true;
    }

    private boolean isExponentialDivergence(Vector initial, Matrix operator) {
        for (int i = 0; i < operator.numRows(); i++) {
            double value = initial.get(i);
            double scale = operator.get(i, i);

            if (value > 0.0 && scale > 1.0) {
                return true;
            }
        }

        return false;
    }

    private boolean isIdentity(Matrix matrix) {
        for (int i = 0; i < matrix.numRows(); i++) {
            for (int j = 0; j < matrix.numColumns(); j++) {

                // All diagonals should be 1
                if (i == j && !EpsilonUtil.epsilonEquals(1.0, matrix.get(i, j))) {
                    return false;
                }

                // All others should be zero
                else if (i != j && !EpsilonUtil.epsilonEquals(0.0, matrix.get(i, j))) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean isZeroVector(Vector vector) {
        // Are any entries non-zero?
        for (int i = 0; i < vector.size(); i++) {
            double value = vector.get(i);

            if (!EpsilonUtil.epsilonEquals(0.0, value)) {
                return false;
            }
        }

        return true;
    }

    private DenseVector zeroVector(Vector initial) {
        return new DenseVector(initial.size());
    }

    /**
     * Solve difference equation for steady state by inverting the steady
     * state equation. Assumes no edge cases apply.
     *
     * @param source
     * @param operator
     * @param initial
     * @return
     */
    private DenseVector ssSolve(Vector source, Matrix operator, Vector initial) {
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
        operator.add(MatrixUtils.I(operator.numRows()));
    }
}
