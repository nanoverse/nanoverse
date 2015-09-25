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

import no.uib.cipr.matrix.sparse.CompDiagMatrix;

/**
 * A wrapper for the internal storage of
 * no.uib.cipr.matrix.sparse.CompDiagMatrix that simulates a padded storage
 * array.
 * <p>
 * CompDiagMatrix's internal storage uses a jagged array, which makes summing
 * the columns quickly in MatrixUtils.isColSumOne() difficult. This class makes
 * the internal storage behave like a typical, padded diagonal matrix format.
 * See http://web.eecs.utk.edu/~dongarra/etemplates/node376.html for a
 * description of the format we are mimicking.
 * <p>
 * Created on 2015-06-17.
 *
 * @author Daniel Greenidge
 */
public class PaddedDiagonalStorage {
    private int numColumns;
    private int numRows;
    // These are the original, non-padded matrix storage
    private double[][] diagonals;
    private int[] indices;

    /**
     * Constructor.
     *
     * @param matrix the matrix whose internal storage we want to grab
     */
    public PaddedDiagonalStorage(CompDiagMatrix matrix) {
        this.diagonals = matrix.getDiagonals();
        this.indices = matrix.getIndex();

        int maxDiagonalLength = 0;
        for (double[] i : diagonals) {
            if (i.length > maxDiagonalLength) {
                maxDiagonalLength = i.length;
            }
        }
        this.numColumns = maxDiagonalLength;

        this.numRows = diagonals.length;
    }

    /**
     * Gets an element in the padded internal storage array.
     *
     * @param row    the 0-based row index
     * @param column the 0-based column index
     * @return the element indexed by (row, column)
     */
    public double get(int row, int column) {
        Index external = new Index(row, column);
        Index internal = mapIndex(external);

        if (internal.row == -1 || internal.column == -1) {  // Out of bounds
            return 0;
        } else {
            return diagonals[internal.row][internal.column];
        }
    }

    /**
     * Maps an index for the padded storage to an index for the jagged storage.
     *
     * @param external the index for the padded storage.
     * @return internal the index for the jagged storage. If internal.row and
     * internal.column are set to -1, the element does not exist in the
     * jagged storage (so should be padded, i.e. set to 0).
     */
    private Index mapIndex(Index external) {
        // Our row index should be the same internally and externally. But we
        // need to check the column, so we initially assume that it is out of
        // bounds.
        Index internal = new Index(external.row, -1);
        int diagonalLength = diagonals[external.row].length;

        if (indices[external.row] < 0) {  // We need to 'pad' the end of the row
            if (diagonalLength - external.column > 0) {
                internal.column = external.column;
            }
        } else {  // We need to 'pad' the beginning of the row
            int padLength = indices[external.row];
            int shiftedIndex = external.column - padLength;
            if (shiftedIndex >= 0) {
                internal.column = shiftedIndex;
            }
        }

        return internal;
    }

    /**
     * Gets the number of columns in the padded internal storage array. Note
     * this is NOT the number of columns in the original matrix.
     *
     * @return the number of columns in the padded internal storage array
     */
    public int getNumColumns() {
        return numColumns;
    }

    /**
     * Gets the number of rows in the padded internal storage array. Note this
     * is NOT the number of rows in the original matrix.
     *
     * @return the number of rows in the padded internal storage array
     */
    public int getNumRows() {
        return numRows;
    }

    // There are no setter methods because changing this instance won't change
    // the original matrix, and we don't want out-of-sync copies

    /**
     * A container for a (row, column) pair so we can pass them around at the
     * same time.
     */
    private class Index {
        int row;
        int column;

        public Index(int row, int column) {
            this.row = row;
            this.column = column;
        }
    }
}
