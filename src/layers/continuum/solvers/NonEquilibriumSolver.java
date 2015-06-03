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
import no.uib.cipr.matrix.*;
import structural.utilities.MatrixUtils;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by dbborens on 6/3/2015.
 */
public class NonEquilibriumSolver extends ContinuumSolver {


    public NonEquilibriumSolver(ContinuumLayerContent content, ScheduledOperations so) {
        super(content, so);
    }

    @Override
    protected Vector doSolve() {
        Vector source = so.getSource();
        Matrix operator = so.getOperator();

        // Non-equilibrium matrix operations are not yet implemented
        if (!MatrixUtils.isIdentity(operator)) {
            throw new NotImplementedException();
        }

        Vector current = content.getState().copy();
        Vector result = current.add(source);
        return result;
    }
}
