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

package layers.continuum.solvers;

import layers.continuum.*;
import layers.continuum.solvers.EquilibriumMatrixSolver;
import no.uib.cipr.matrix.Matrix;
import no.uib.cipr.matrix.Vector;
import structural.utilities.MatrixUtils;

/**
 * Created by dbborens on 12/12/14.
 */
public class EquilibriumSolver {

    int count = 0;

    private ContinuumLayerContent content;
    private ScheduledOperations so;
    private EquilibriumMatrixSolver steadyState;

    public EquilibriumSolver(ContinuumLayerContent content, ScheduledOperations so, EquilibriumMatrixSolver steadyState) {
        this.content = content;
        this.so = so;
        this.steadyState = steadyState;
    }

    /**
     * Apply all scheduled operations, then reset the schedule.
     */
    public void solve() {
        Vector source = so.getSource();
        Matrix operator = so.getOperator();

        Vector template = content.getState().copy();
        Vector solution = steadyState.solve(source, operator, template);

        content.setState(solution);
//        System.out.println(MatrixUtils.asMatrix(solution, 32));
        so.reset();
    }

}
