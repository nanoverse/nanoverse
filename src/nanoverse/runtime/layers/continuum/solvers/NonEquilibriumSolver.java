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
import nanoverse.runtime.structural.NotYetImplementedException;
import nanoverse.runtime.structural.annotations.FactoryTarget;
import no.uib.cipr.matrix.Vector;

/**
 * Created by dbborens on 6/3/2015.
 */
public class NonEquilibriumSolver extends ContinuumSolver {


    @FactoryTarget
    public NonEquilibriumSolver(ContinuumLayerContent content, ScheduledOperations so) {
        super(content, so);
        if (so.isOperators()) {
            throw new NotYetImplementedException();
        }
    }

    @Override
    protected Vector doSolve() {
        Vector source = so.getSource();

        Vector current = content.getState().copy();
        Vector result = current.add(source);
        return result;
    }
}
