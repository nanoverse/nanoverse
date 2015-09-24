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

package layers.continuum;

import no.uib.cipr.matrix.DenseVector;
import no.uib.cipr.matrix.Matrix;
import no.uib.cipr.matrix.Vector;
import no.uib.cipr.matrix.sparse.CompDiagMatrix;
import org.junit.Before;
import org.junit.Test;
import structural.utilities.MatrixUtils;
import test.LinearMocks;

public class ScheduledOperationsTest extends LinearMocks {

    private ScheduledOperations query;

    @Before
    public void initQuery() {
        query = new ScheduledOperations(geom, true);
    }

    @Test
    public void injScalarIncrementsSourceVector() throws Exception {
        query.inject(a, 1.0);
        query.inject(a, 0.5);

        Vector expected = vector(1.5, 0, 0);
        Vector actual = query.getSource();

        assertVectorsEqual(expected, actual, epsilon);
    }

    @Test
    public void injScalarDoesNotAffectOperator() throws Exception {
        query.inject(a, 1.0);

        Matrix expected = MatrixUtils.compDiagIdentity(3);
        Matrix actual = query.getOperator();

        assertMatricesEqual(expected, actual, epsilon);
    }

    @Test
    public void setSourceOverwritesValue() throws Exception {
        query.setSource(a, 1.0);

       Vector expected = vector(1.0, 0.0, 0.0);
       Vector actual = query.getSource();

        assertVectorsEqual(expected, actual, epsilon);
    }

    @Test
    public void injVectorIsVectorAddition() throws Exception {
        DenseVector vector = vector(1.0, 2.0, 3.0);
        query.inject(vector);
        query.inject(vector);

        Vector expected = vector(2.0, 4.0, 6.0);
        Vector actual = query.getSource();

        assertVectorsEqual(expected, actual, epsilon);
    }

    @Test
    public void injVectorDoesNotAffectOperator() throws Exception {
        DenseVector vector = vector(1.0, 2.0, 3.0);
        query.inject(vector);

        Matrix expected = MatrixUtils.compDiagIdentity(3);
        Matrix actual = query.getOperator();

        assertMatricesEqual(expected, actual, epsilon);
    }

    @Test
    public void expAddsToIdentityMatrix() throws Exception {
        query.exp(a, 3.0);
        query.exp(a, 0.5);

        Matrix expected = matrix(4.5, 1, 1);
        Matrix actual = query.getOperator();

        assertMatricesEqual(expected, actual, epsilon);
    }

    @Test
    public void expDoesNotAffectSource() throws Exception {
        query.exp(a, 3.0);

        Vector expected = vector(0, 0, 0);
        Vector actual = query.getSource();

        assertVectorsEqual(expected, actual, epsilon);
    }

    @Test
    public void zeroOperatorRowZerosRow() throws Exception {
        query.zeroOperatorRow(a);

        Matrix expected = matrix(0, 1, 1);
        Matrix actual = query.getOperator();

        assertMatricesEqual(expected, actual, epsilon);
    }

    @Test
    public void resetSetsSourceToZero() throws Exception {
        query.inject(a, 1.0);
        query.reset();

        Vector expected = vector(0, 0, 0);
        Vector actual = query.getSource();

        assertVectorsEqual(expected, actual, epsilon);
    }

    @Test
    public void resetSetsOperatorToIdentity() throws Exception {
        CompDiagMatrix toApply = matrix(1, 2, 3);
        query.apply(toApply);
        query.reset();

        Matrix expected = MatrixUtils.compDiagIdentity(3);
        Matrix actual = query.getOperator();

        assertMatricesEqual(expected, actual, epsilon);
    }

    @Test
    public void applyIsMatrixAddition() throws Exception {
        CompDiagMatrix toApply = matrix(1.0, 2.0, 3.0);
        query.apply(toApply);

        Matrix expected = matrix(2.0, 3.0, 4.0);
        Matrix actual = query.getOperator();

        assertMatricesEqual(expected, actual, epsilon);
    }

    @Test
    public void applyDoesNotAffectSource() throws Exception {
        CompDiagMatrix toApply = matrix(1.0, 2.0, 3.0);
        query.apply(toApply);

        Vector expected = vector(0, 0, 0);
        Vector actual = query.getSource();

        assertVectorsEqual(expected, actual, epsilon);
    }
}