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

package nanoverse.runtime.structural.utilities;

import no.uib.cipr.matrix.*;
import no.uib.cipr.matrix.sparse.CompDiagMatrix;

public abstract class MatrixUtils {
    /**
     * Returns a matrix in a tab-separated matrix form.
     *
     * @return
     */
    public static String matrixForm(Matrix m) {
        StringBuilder sb = new StringBuilder();

        int r = m.numRows();
        int c = m.numColumns();
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                sb.append(String.format("%.3f", m.get(i, j)));
                sb.append('\t');
            }
            sb.append('\n');
        }

        return sb.toString();
    }

    /**
     * Returns a vector in a tab-separated matrix form.
     *
     * @return
     */
    public static String asMatrix(Vector v, int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= v.size(); i++) {
            sb.append(String.format("%.3f", v.get(i - 1)));
            sb.append('\t');

            if (i % n == 0) {
                sb.append("\n");
            }
        }

        return sb.toString();
    }

    /**
     * Returns an compressed diagonal identity matrix of size n.
     *
     * @param n the size of the identity matrix
     * @return m  the compressed diagonal identity matrix
     */
    public static CompDiagMatrix compDiagIdentity(int n) {
        int[] diagonals = {0};  // Indices of diagonals to preallocate
        CompDiagMatrix m = new CompDiagMatrix(n, n, diagonals);
        for (int i = 0; i < n; i++) {
            m.set(i, i, 1d);
        }

        return m;
    }

    public static boolean isIdentity(Matrix matrix) {
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

    public static boolean isZeroVector(Vector vector) {
        // Are any entries non-zero?
        for (int i = 0; i < vector.size(); i++) {
            double value = vector.get(i);

            if (!EpsilonUtil.epsilonEquals(0.0, value)) {
                return false;
            }
        }

        return true;
    }

    public static DenseVector zeroVector(Vector initial) {
        return new DenseVector(initial.size());
    }

    /**
     * Checks if a matrice's columns all sum to one.
     * <p>
     * This is a faster algorithm that exploits matrix sparsity.
     *
     * @param matrix the matrix to check
     * @return true if the columns sum to one, false otherwise
     */
    public static boolean isColSumOne(CompDiagMatrix matrix) {
        PaddedDiagonalStorage storage = new PaddedDiagonalStorage(matrix);

        for (int i = 0; i < storage.getNumColumns(); i++) {
            double colSum = 0.0;

            for (int j = 0; j < storage.getNumRows(); j++) {
                colSum += storage.get(j, i);
            }

            boolean colEqualsOne = EpsilonUtil.epsilonEquals(1.0, colSum);

            if (!colEqualsOne) {
                return false;
            }
        }

        return true;
    }

    /**
     * Checks if a matrice's rows all sum to one.
     *
     * @param matrix the matrix to check
     * @return true if the rows sum to one, false otherwise
     */
    public static boolean isRowSumOne(Matrix matrix) {
        for (int i = 0; i < matrix.numRows(); i++) {
            double rowSum = 0.0;

            for (int j = 0; j < matrix.numColumns(); j++) {
                rowSum += matrix.get(i, j);
            }

            boolean rowEqualsOne = EpsilonUtil.epsilonEquals(1.0, rowSum);

            if (!rowEqualsOne) {
                return false;
            }
        }

        return true;
    }

    /**
     * Zeros out a row of a compressed diagonal storage matrix in place.
     *
     * @param matrix   the matrix to operate on
     * @param rowIndex the index of the row to zero out
     */
    public static void zeroRow(CompDiagMatrix matrix, int rowIndex) {
        int[] indices = matrix.getIndex();

        for (int i = 0; i < indices.length; i++) {
            int columnIndex = indices[i] + rowIndex;

            if (columnIndex >= 0 && columnIndex < matrix.numColumns()) {
                matrix.set(rowIndex, columnIndex, 0.0);
            }
        }
    }
}
