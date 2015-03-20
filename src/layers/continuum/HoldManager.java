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

import java.util.function.Function;

/**
 * Created by dbborens on 1/9/15.
 */
public class HoldManager {
    private ContinuumAgentManager manager;
    private ContinuumSolver solver;
    private boolean held;

    public HoldManager(ContinuumAgentManager manager, ContinuumSolver solver) {
        this.manager = manager;
        this.solver = solver;
        this.held = false;
    }

    public void hold() {
        if (held) {
            throw new IllegalStateException("Attempted to initiate hold for already held continuum layer.");
        }

        held = true;
    }

    public void release() {
        if (!held) {
            throw new IllegalStateException("Attempted to release hold on a continuum layer that was not already held.");
        }

        held = false;

        solve();
    }

    private void solveIfNotHeld() {
        if (held)
            return;

        solve();
    }

    public void resolve(Runnable runnable) {
        runnable.run();
        solveIfNotHeld();
    }

    public void solve() {
        if (held) {
            throw new IllegalStateException("Attempting to solve while hold is in place.");
        }

        manager.apply();
        solver.solve();
    }

    public void reset() {
        manager.reset();
        held = false;
    }

    public ContinuumAgentLinker getLinker(Function<Coordinate, Double> stateLookup) {
        return manager.getLinker(stateLookup);
    }

    public String getId() {
        return manager.getId();
    }

    public boolean isHeld() {
        return held;
    }
}
