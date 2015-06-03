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

package factory.layers.continuum;

import layers.continuum.*;
import layers.continuum.solvers.*;
import org.dom4j.Element;

/**
 * Created by dbborens on 6/3/2015.
 */
public class ContinuumSolverFactory {

    public static Solver instantiate(Element e, ContinuumLayerContent content, ScheduledOperations so) {
        if (e == null) {
            return makeEquilibriumSolver(content, so);
        }
        
        String solverType = e.getTextTrim();
        if (solverType.equalsIgnoreCase("equilibrium")) {
            return makeEquilibriumSolver(content, so);
        } else if (solverType.equalsIgnoreCase("non-equilibrium")) {
            return makeNonEquilibriumSolver(content, so);
        }
        throw new IllegalArgumentException("Unrecognized continuum solver class '" + solverType + "'");
    }

    private static Solver makeNonEquilibriumSolver(ContinuumLayerContent content, ScheduledOperations so) {
        return new NonEquilibriumSolver(content, so);
    }

    private static Solver makeEquilibriumSolver(ContinuumLayerContent content, ScheduledOperations so) {
        EquilibriumMatrixSolver steadyState = new EquilibriumMatrixSolver();
        return new EquilibriumSolver(content, so, steadyState);
    }
}
