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
import no.uib.cipr.matrix.DenseVector;
import no.uib.cipr.matrix.Vector;
import no.uib.cipr.matrix.sparse.CompDiagMatrix;
import structural.utilities.MatrixUtils;

import java.util.function.Function;

/**
 * Helper class for continuum layers. Knows what transformations
 * have been scheduled since the last update.
 * Created by dbborens on 12/11/14.
 */
public class ScheduledOperations {

    private final CompDiagMatrix identity;
    private final DenseVector zeroVector;
    private CompDiagMatrix operator;
    private Vector source;
    private Function<Coordinate, Integer> indexer;
    private final boolean operators;

    public ScheduledOperations(Function<Coordinate, Integer> indexer, int n, boolean operators) {
        this.indexer = indexer;
        this.operators = operators;

        if (operators) {
            identity = MatrixUtils.CompDiagIdentity(n);
        } else {
            identity = null;
        }

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
        if (!operators) {
            throw new IllegalStateException("Operators are disabled but an exponentiation event is being scheduled");
        }

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
        if (operators) {
            operator = new CompDiagMatrix(identity);
        }

        // Replace source vector with zero vector
        source = zeroVector.copy();
    }

    /**
     * Add this matrix to the current matrix. Note that this
     * means successive scalings will be additive in magnitude,
     * not multiplicative.
     *
     * @param toApply The matrix to be applied
     */
    public void apply(CompDiagMatrix toApply) {
        if (!operators) {
            throw new IllegalStateException("Operators are disabled but an operator matrix is being scheduled");
        }
        operator.add(toApply);
    }


    public Vector getSource() {
        return source;
    }

    public CompDiagMatrix getOperator() {
        return operator;
    }

}
