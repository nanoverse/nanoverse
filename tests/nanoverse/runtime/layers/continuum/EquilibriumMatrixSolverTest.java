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

package nanoverse.runtime.layers.continuum;

import nanoverse.runtime.layers.continuum.solvers.*;
import nanoverse.runtime.structural.utilities.MatrixUtils;
import no.uib.cipr.matrix.*;
import no.uib.cipr.matrix.Vector;
import no.uib.cipr.matrix.sparse.CompDiagMatrix;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import test.TestBase;

import java.util.*;

@RunWith(value = Parameterized.class)
public class EquilibriumMatrixSolverTest extends TestBase {

    private DenseVector initial;
    private EquilibriumMatrixSolver query;

    public EquilibriumMatrixSolverTest(EquilibriumMatrixSolver solver) {
        query = solver;
        initial = new DenseVector(3);
        initial.set(1, 1.0);
    }

    @Parameterized.Parameters
    public static Collection solvers() {
        // Array containing all the solvers the test will run with
        EquilibriumMatrixSolver[] solvers = new EquilibriumMatrixSolver[]{
            new EquilibriumKrylovSolver(true),
            new EquilibriumBandSolver(true),
            new EquilibriumPetscSolver(true)};
        return Arrays.asList(solvers);
    }

    /**
     * Identity matrix; no initial concentration or source.
     *
     * @throws Exception
     */
    @Test
    public void trivialCaseReturnsZero() throws Exception {
        CompDiagMatrix operator = MatrixUtils.compDiagIdentity(3);
        DenseVector source = new DenseVector(3);
        initial = new DenseVector(3);               // Zero out initial value
        doTest(source, operator, new DenseVector(3));
    }

    private void doTest(DenseVector source, CompDiagMatrix operator, DenseVector expected) {
        Vector actual = query.solve(source, operator, initial.copy());
        assertVectorsEqual(expected, actual, 1e-14);
    }

    /**
     * In the special case that there are no operations and no sources,
     * the system should return the original vector.
     *
     * @throws Exception
     */
    @Test
    public void identityOperatorReturnsInput() throws Exception {
        CompDiagMatrix operator = MatrixUtils.compDiagIdentity(3);
        DenseVector source = new DenseVector(3);
        doTest(source, operator, initial.copy());
    }

    /**
     * If there is zero initial value and no source, then the solution
     * remains identically zero.
     */
    @Test
    public void zeroValueReturnsZero() throws Exception {
        CompDiagMatrix operator = diffusion();
        DenseVector source = new DenseVector(3);
        initial = new DenseVector(3);               // Zero out initial value
        doTest(source, operator, new DenseVector(3));
    }

    private CompDiagMatrix diffusion() {

        CompDiagMatrix operator = new CompDiagMatrix(3, 3);

        /* Operator:
         *
         * 0.8 0.1 0.0
         * 0.1 0.8 0.1
         * 0.0 0.1 0.8
         */

        operator.set(0, 0, 0.8);
        operator.set(0, 1, 0.1);
        operator.set(0, 2, 0.0);

        operator.set(1, 0, 0.1);
        operator.set(1, 1, 0.8);
        operator.set(1, 2, 0.1);

        operator.set(2, 0, 0.0);
        operator.set(2, 1, 0.1);
        operator.set(2, 2, 0.8);

        return operator;
    }

    /**
     * If a coordinate with a non-zero initial value has a diagonal
     * operator value greater than 1.0, it will diverge--we expect an
     * error.
     */
    @Test(expected = IllegalStateException.class)
    public void divergentInputThrows() {
        CompDiagMatrix operator = new CompDiagMatrix(3, 3);
        operator.set(1, 1, 2.0);
        DenseVector source = new DenseVector(3);
        query.solve(source, operator, initial);

    }

    /**
     * If a non-decaying site also has a positive source term, it will diverge
     * and we expect an error.
     *
     * @throws Exception
     */
    @Test(expected = IllegalStateException.class)
    public void linearDivergenceThrows() throws Exception {
        CompDiagMatrix operator = MatrixUtils.compDiagIdentity(3);
        DenseVector source = new DenseVector(3);
        source.set(0, 1.0);
        query.solve(source, operator, initial);
    }

    /**
     * If the matrix is not the identity, but every row and column sums to one,
     * then there is no steady state solution -- we expect an error.
     */
    @Test(expected = IllegalStateException.class)
    public void noSteadyStateThrows() {
        CompDiagMatrix operator = advection();
        DenseVector source = new DenseVector(3);
        query.solve(source, operator, initial);
    }

    /**
     * Advection with periodic boundary conditions. No steady
     * state.
     *
     * @return
     */
    private CompDiagMatrix advection() {
        CompDiagMatrix operator = new CompDiagMatrix(3, 3);

        /* Operator:
         *
         * 0.9 0.1 0.0
         * 0.0 0.9 0.1
         * 0.1 0.0 0.9
         */

        operator.set(0, 0, 0.9);
        operator.set(0, 1, 0.1);
        operator.set(0, 2, 0.0);

        operator.set(1, 0, 0.0);
        operator.set(1, 1, 0.9);
        operator.set(1, 2, 0.1);

        operator.set(2, 0, 0.1);
        operator.set(2, 1, 0.0);
        operator.set(2, 2, 0.9);

        return operator;
    }

    /**
     * If there is no source and the matrix is non-identical and has a steady
     * state solution, that steady state solution must be zero.
     */
    @Test
    public void decayGoesToZero() {
        DenseVector source = new DenseVector(3);
        CompDiagMatrix operator = diffusion();
        DenseVector expected = new DenseVector(3);
        doTest(source, operator, expected);
    }

    /**
     * A source at the middle coordinate and diffusion with absorbing
     * boundaries; should be solved as normal and have a non-trivial
     * solution.
     *
     * @throws Exception
     */
    @Test
    public void generalCaseSolvesMatrix() throws Exception {
        DenseVector source = new DenseVector(3);
        source.set(1, 1);
        CompDiagMatrix operator = diffusion();
        DenseVector expected = new DenseVector(new double[]{5.0, 10.0, 5.0});
        doTest(source, operator, expected);
    }
}