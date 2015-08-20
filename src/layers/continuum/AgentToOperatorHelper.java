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
import geometry.Geometry;
import no.uib.cipr.matrix.DenseVector;
import no.uib.cipr.matrix.sparse.CompDiagMatrix;
import structural.annotations.FactoryTarget;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * Converts injections and exponentiations scheduled
 * by agents to vectors and matrices that can be applied
 * to the schedule.
 * <p>
 * Created by dbborens on 12/31/14.
 */
public class AgentToOperatorHelper {

    // Number of canonical sites
    private final int n;

    // Converts coordinates to vector/matrix indices
    private final Function<Coordinate, Integer> indexer;

    // Unless set to true, throws exceptions if operators are scheduled or retrieved
    private final boolean operators;

    @FactoryTarget
    public AgentToOperatorHelper(Geometry geom, boolean operators) {
        this.n = geom.getCanonicalSites().length;
        this.indexer = geom.getIndexer();
        this.operators = operators;
    }

    public CompDiagMatrix getOperator(List<RelationshipTuple> relationships) {
        if (!operators) {
            throw new IllegalStateException("Attempting to access operators while operators are explicitly disabled");
        }
        CompDiagMatrix matrix = new CompDiagMatrix(n, n);
        BiConsumer<Integer, Double> consumer = (i, v) -> matrix.add(i, i, v);
        Function<RelationshipTuple, Double> expLookup = RelationshipTuple::getExp;
        apply(relationships, expLookup, consumer);
        return matrix;
    }

    public DenseVector getSource(List<RelationshipTuple> relationships) {
        DenseVector vector = new DenseVector(n);
        BiConsumer<Integer, Double> consumer = (i, v) -> vector.add(i, v);
        Function<RelationshipTuple, Double> injLookup = RelationshipTuple::getInj;
        apply(relationships, injLookup, consumer);
        return vector;
    }

    private void apply(List<RelationshipTuple> relationships, Function<RelationshipTuple, Double> lookup, BiConsumer<Integer, Double> consumer) {
        relationships.forEach(relationship -> {
            Coordinate c = relationship.getCoordinate();
            Double v = lookup.apply(relationship);
            Integer i = indexer.apply(c);
            consumer.accept(i, v);
        });
    }
}
