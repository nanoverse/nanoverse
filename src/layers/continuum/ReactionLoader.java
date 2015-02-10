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

import no.uib.cipr.matrix.DenseMatrix;
import no.uib.cipr.matrix.DenseVector;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by dbborens on 12/31/14.
 */
public class ReactionLoader {

    private Consumer<DenseVector> injector;
    private Consumer<DenseMatrix> exponentiator;
    private AgentToOperatorHelper helper;

    public ReactionLoader(Consumer<DenseVector> injector, Consumer<DenseMatrix> exponentiator, AgentToOperatorHelper helper) {
        this.injector = injector;
        this.exponentiator = exponentiator;
        this.helper = helper;
    }

    private void inject(List<RelationshipTuple> relationships) {
        DenseVector delta = helper.getSource(relationships);
        injector.accept(delta);
    }

    private void exponentiate(List<RelationshipTuple> relationshipTuples) {
        DenseMatrix exponents = helper.getOperator(relationshipTuples);
        exponentiator.accept(exponents);
    }

    public void apply(Stream<RelationshipTuple> relationships) {
        // TODO Refactor this hierarchy to pass through stream only once
        List<RelationshipTuple> list = relationships.collect(Collectors.toList());
        inject(list);
        exponentiate(list);
    }
}
