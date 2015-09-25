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

import no.uib.cipr.matrix.DenseVector;
import no.uib.cipr.matrix.sparse.CompDiagMatrix;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.*;

/**
 * Created by dbborens on 12/31/14.
 */
public class ReactionLinker {

    private final Consumer<DenseVector> injector;
    private final Consumer<CompDiagMatrix> exponentiator;
    private final AgentToOperatorHelper atoHelper;
    private final boolean operators;

    public ReactionLinker(ScheduledOperations so, AgentToOperatorHelper atoHelper) {
        injector = vector -> so.inject(vector);
        operators = so.isOperators();

        if (operators) {
            exponentiator = matrix -> so.apply(matrix);
        } else {
            exponentiator = matrix -> {
                throw new IllegalStateException("Attempted to schedule matrix operator, but operators are explicitly disabled");
            };
        }

        this.atoHelper = atoHelper;

    }

    public ReactionLinker(Consumer<DenseVector> injector, Consumer<CompDiagMatrix> exponentiator,
                          AgentToOperatorHelper atoHelper, boolean operators) {
        this.injector = injector;
        this.exponentiator = exponentiator;
        this.atoHelper = atoHelper;
        this.operators = operators;
    }

    public void apply(Stream<RelationshipTuple> relationships) {
        List<RelationshipTuple> list = relationships.collect(Collectors.toList());
        inject(list);

        if (operators) {
            exponentiate(list);
        }
    }

    private void inject(List<RelationshipTuple> relationships) {
        DenseVector delta = atoHelper.getSource(relationships);
        injector.accept(delta);
    }

    private void exponentiate(List<RelationshipTuple> relationshipTuples) {
        CompDiagMatrix exponents = atoHelper.getOperator(relationshipTuples);
        exponentiator.accept(exponents);
    }
}
