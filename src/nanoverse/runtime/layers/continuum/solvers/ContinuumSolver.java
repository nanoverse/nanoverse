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

package nanoverse.runtime.layers.continuum.solvers;

import nanoverse.runtime.control.identifiers.*;
import nanoverse.runtime.layers.continuum.*;
import nanoverse.runtime.structural.annotations.FactoryTarget;
import no.uib.cipr.matrix.Vector;


/**
 * Created by dbborens on 5/31/2015.
 */
public abstract class ContinuumSolver {
    protected final ContinuumLayerContent content;
    protected final ScheduledOperations so;

    @FactoryTarget
    public ContinuumSolver(ContinuumLayerContent content, ScheduledOperations so) {
        this.content = content;
        this.so = so;
    }

    public void solve() {
        Vector solution = doSolve();
        content.setState(solution);
        so.reset();
    }

    protected abstract Vector doSolve();
}
