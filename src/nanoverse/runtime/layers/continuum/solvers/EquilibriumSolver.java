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

package nanoverse.runtime.layers.continuum.solvers;

import nanoverse.runtime.layers.continuum.*;
import nanoverse.runtime.structural.annotations.FactoryTarget;
import no.uib.cipr.matrix.Vector;
import no.uib.cipr.matrix.sparse.CompDiagMatrix;

/**
 * Created by dbborens on 12/12/14.
 */
public class EquilibriumSolver extends ContinuumSolver {

    private final EquilibriumMatrixSolver steadyState;

    @FactoryTarget
    public EquilibriumSolver(ContinuumLayerContent content, ScheduledOperations so) {
        super(content, so);
        steadyState = new EquilibriumBandSolver(so.isOperators());
    }

    public EquilibriumSolver(ContinuumLayerContent content, ScheduledOperations so, EquilibriumMatrixSolver steadyState) {
        super(content, so);
        this.steadyState = steadyState;
    }

    /**
     * Apply all scheduled operations, then reset the schedule.
     */
    protected Vector doSolve() {
        Vector source = so.getSource();
        CompDiagMatrix operator = so.getOperator();

        Vector template = content.getState().copy();
        Vector solution = steadyState.solve(source, operator, template);
        return solution;
    }

}
