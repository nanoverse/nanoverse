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

package nanoverse.runtime.structural.utilities;

import no.uib.cipr.matrix.*;
import no.uib.cipr.matrix.sparse.CompDiagMatrix;
import org.junit.Test;
import test.TestBase;

import static org.junit.Assert.*;
/**
 * Created on 2016-06-15.
 *
 * @author Daniel Greenidge
 */
public class MatrixUtilsTest extends TestBase {

    @Test
    public void testMatrixForm() throws Exception {
        Matrix m = new DenseMatrix(2, 2);
        m.set(0, 0, 1);
        m.set(0, 1, 2);
        m.set(1, 0, 3);
        m.set(1, 1, 4);

        String expected = "1.000\t2.000\t\n3.000\t4.000\t\n";

        assertEquals(expected, MatrixUtils.matrixForm(m));
    }

    @Test
    public void testAsMatrix() throws Exception {
        Vector v = new DenseVector(4);
        v.add(0, 0);
        v.add(1, 1);
        v.add(2, 2);
        v.add(3, 3);

        String expected = "0.000\t1.000\t\n2.000\t3.000\t\n";

        assertEquals(expected, MatrixUtils.asMatrix(v, 2));
    }

    @Test
    public void testCompDiagIdentity() throws Exception {
        int size = 3;
        CompDiagMatrix expected = new CompDiagMatrix(Matrices.identity(size));
        CompDiagMatrix actual = MatrixUtils.compDiagIdentity(size);

        // We have to do this because CompDiagMatrix doesn't override
        // Object.equals()
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                assertTrue(EpsilonUtil.epsilonEquals(expected.get(i, j),
                        actual.get(i, j)));
            }
        }
    }

    @Test
    public void testIsIdentity() throws Exception {
        Matrix identity1 = MatrixUtils.compDiagIdentity(3);
        Matrix identity2 = Matrices.identity(3);

        assertTrue(MatrixUtils.isIdentity(identity1));
        assertTrue(MatrixUtils.isIdentity(identity2));

        identity1.set(0, 0, 2);
        identity2.set(1, 0, 1);

        assertFalse(MatrixUtils.isIdentity(identity1));
        assertFalse(MatrixUtils.isIdentity(identity2));
    }

    @Test
    public void testIsZeroVector() throws Exception {
        Vector v1 = new DenseVector(2);
        v1.set(0, 0);
        v1.set(1, 0);
        assertTrue(MatrixUtils.isZeroVector(v1));

        Vector v2 = new DenseVector(2);
        v2.set(0, 0);
        v2.set(0, 1);
        assertFalse(MatrixUtils.isZeroVector(v2));
    }

    @Test
    public void testZeroVector() throws Exception {
        Vector v = new DenseVector(2);
        v.set(0, 1);
        v.set(1, 1);
        Vector zero = MatrixUtils.zeroVector(v);

        assertTrue(MatrixUtils.isZeroVector(zero));
    }

    @Test
    public void testIsColSumOne() throws Exception {
        /* Matrix to test:
            0.9   0.8     0.7
            0.1   0.2     0.3
            0     0       0     */

        CompDiagMatrix operator = new CompDiagMatrix(3, 3);
        operator.set(0, 0, 0.9);
        operator.set(0, 1, 0.8);
        operator.set(0, 2, 0.7);
        operator.set(1, 0, 0.1);
        operator.set(1, 1, 0.2);
        operator.set(1, 2, 0.3);
        operator.set(2, 0, 0);
        operator.set(2, 1, 0);
        operator.set(2, 2, 0);

        assertTrue(MatrixUtils.isColSumOne(operator));
        operator.set(0, 0, 1);
        assertFalse(MatrixUtils.isColSumOne(operator));
    }

    @Test
    public void testIsRowSumOne() throws Exception {
        /* Matrix to test:
             0.9    0.1     0
             0.9    0.1     0
             0.9    0.1     0   */

        Matrix operator = new DenseMatrix(3, 3);
        operator.set(0, 0, 0.9);
        operator.set(1, 0, 0.9);
        operator.set(2, 0, 0.9);
        operator.set(0, 1, 0.1);
        operator.set(1, 1, 0.1);
        operator.set(2, 1, 0.1);
        operator.set(0, 2, 0);
        operator.set(1, 2, 0);
        operator.set(2, 2, 0);

        assertTrue(MatrixUtils.isRowSumOne(operator));
        operator.set(2, 2, 1);
        assertFalse(MatrixUtils.isRowSumOne(operator));
    }

    @Test
    public void testZeroRow() throws Exception {
        CompDiagMatrix operator = MatrixUtils.compDiagIdentity(3);
        CompDiagMatrix expected = new CompDiagMatrix(3, 3);
        expected.set(0, 0, 1);
        expected.set(1, 1, 0);
        expected.set(2, 2, 1);

        MatrixUtils.zeroRow(operator, 1);

        for (int i = 0; i < operator.numRows(); i++) {
            for (int j = 0; j < operator.numColumns(); j++) {
                assertEquals(expected.get(i, j), operator.get(i, j), epsilon);
            }
        }
    }
}