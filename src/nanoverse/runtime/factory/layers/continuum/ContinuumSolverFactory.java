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

package nanoverse.runtime.factory.layers.continuum;

import nanoverse.runtime.layers.continuum.*;
import nanoverse.runtime.layers.continuum.solvers.*;
import org.dom4j.Element;

/**
 * Created by dbborens on 6/3/2015.
 */
public class ContinuumSolverFactory {

    public static ContinuumSolver instantiate(Element e, ContinuumLayerContent content, ScheduledOperations so, boolean operators) {
        if (e == null) {
            return makeEquilibriumSolver(content, so, operators);
        }

        String solverType = e.getTextTrim();
        if (solverType.equalsIgnoreCase("equilibrium")) {
            return makeEquilibriumSolver(content, so, operators);
        } else if (solverType.equalsIgnoreCase("non-equilibrium")) {
            return makeNonEquilibriumSolver(content, so, operators);
        }
        throw new IllegalArgumentException("Unrecognized continuum solver class '" + solverType + "'");
    }

    private static ContinuumSolver makeNonEquilibriumSolver(ContinuumLayerContent content, ScheduledOperations so, boolean operators) {
        return new NonEquilibriumSolver(content, so);
    }

    private static ContinuumSolver makeEquilibriumSolver(ContinuumLayerContent content, ScheduledOperations so, boolean operators) {
        // TODO: Set solver type using config file instead of hard-coding it
        EquilibriumMatrixSolver steadyState = new EquilibriumBandSolver(operators);
        return new EquilibriumSolver(content, so, steadyState);
    }
}
