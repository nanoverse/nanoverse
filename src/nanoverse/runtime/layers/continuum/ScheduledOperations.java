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

import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.geometry.Geometry;
import nanoverse.runtime.structural.annotations.FactoryTarget;
import nanoverse.runtime.structural.utilities.MatrixUtils;
import no.uib.cipr.matrix.*;
import no.uib.cipr.matrix.sparse.CompDiagMatrix;

import java.util.function.Function;

/**
 * Helper class for continuum nanoverse.runtime.layers. Knows what transformations
 * have been scheduled since the last update.
 * Created by dbborens on 12/11/14.
 */
public class ScheduledOperations {

    private final CompDiagMatrix identity;
    private final DenseVector zeroVector;
    private final boolean operators;
    private CompDiagMatrix operator;
    private Vector source;
    private Function<Coordinate, Integer> indexer;

    @FactoryTarget
    public ScheduledOperations(Geometry geom, boolean operators) {
        int n = geom.getCanonicalSites().length;
        indexer = geom.getIndexer();
        this.operators = operators;

        if (operators) {
            identity = MatrixUtils.compDiagIdentity(n);
        } else {
            identity = null;
        }

        zeroVector = new DenseVector(n);

        reset();
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
     * Sets the source value for a particular coordinate (i.e. overwrites the
     * coordinate's location in the source vector with b)
     *
     * @param coordinate the coordinate of the location to set
     * @param b          the value to set
     */
    public void setSource(Coordinate coordinate, double b) {
        int index = indexer.apply(coordinate);
        source.set(index, b);
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
     * Zeros the row in the operator matrix associated with coordinate
     *
     * @param coordinate the coordinate whose row we want to zero out
     */
    public void zeroOperatorRow(Coordinate coordinate) {
        if (!operators) {
            throw new IllegalStateException("Operators are disabled but an " +
                "Dirichlet boundary condition is being enforced");
        }

        int index = indexer.apply(coordinate);
        MatrixUtils.zeroRow(operator, index);
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

    public boolean isOperators() {
        return operators;
    }
}
