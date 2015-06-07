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

import control.identifiers.Coordinate;
import no.uib.cipr.matrix.DenseMatrix;
import no.uib.cipr.matrix.DenseVector;
import no.uib.cipr.matrix.Matrix;
import no.uib.cipr.matrix.Vector;
import structural.utilities.MatrixUtils;

import java.util.function.Function;

/**
 * Helper class for continuum layers. Knows what transformations
 * have been scheduled since the last update.
 * Created by dbborens on 12/11/14.
 */
public class ScheduledOperations {

    private final Matrix identity;
    private final DenseVector zeroVector;
    private Matrix operator;
    private Vector source;
    private Function<Coordinate, Integer> indexer;

    public ScheduledOperations(Function<Coordinate, Integer> indexer, int n) {
        this.indexer = indexer;

        identity = MatrixUtils.I(n);

//        identity = new DenseMatrix(bandIdentity);
        zeroVector = new DenseVector(n);

        reset();
    }

    /**
     * Add specified amount to a particular coordinate.
     *
     * @param coordinate
     * @param delta
     */
    public void inject(Coordinate coordinate, double delta) {
        int index = indexer.apply(coordinate);
        double current = source.get(index);
        double next = current + delta;

        source.set(index, next);
    }

    public void inject(DenseVector delta) {
        source.add(delta);
    }

    /**
     * Exponentiate a particular location (i.e., add b to the diagonal.)
     *
     * @param coordinate
     * @param b
     */
    public void exp(Coordinate coordinate, double b) {
        int index = indexer.apply(coordinate);
        double current = operator.get(index, index);
        double next = current + b;
        operator.set(index, index, next);
    }

    /**
     * Initialize source and operator vectors. The default values would
     * leave the current state of the field unaltered if applied.
     */
    public void reset() {
        // Reset operator to identity
        operator = identity.copy();

        // Replace source vector with zero vector
        source = zeroVector.copy();
    }

    /**
     * If this is the first matrix operation being scheduled, REPLACE the
     * default (identity) matrix with this matrix. Otherwise, ADD this matrix
     * to the current matrix. Note that this means successive scalings will
     * be additive in magnitude, not multiplicative.
     *
     * @param toApply The matrix to be applied
     */
    public void apply(Matrix toApply) {
        operator.add(toApply);
    }


    public Vector getSource() {
        return source;
    }

    public Matrix getOperator() {
        return operator;
    }

}
