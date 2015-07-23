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

package processes.discrete.check;

import control.arguments.*;
import control.halt.DominationEvent;
import control.halt.HaltCondition;
import processes.BaseProcessArguments;
import processes.StepState;
import processes.discrete.CellProcess;
import processes.discrete.CellProcessArguments;
import processes.gillespie.GillespieState;

/**
 * Halt the simulation when the target cell type has the specified fraction
 * of the overall live cell population.
 * <p>
 * Checks for extinction or fixation events.
 * <p>
 * Created by dbborens on 1/13/14.
 */
public class CheckForDomination extends CellProcess {
    private DoubleArgument targetFractionArg;
    private IntegerArgument targetStateArg;
    private double targetFraction;
    private int targetState;

    public CheckForDomination(BaseProcessArguments arguments, CellProcessArguments cpArguments, IntegerArgument targetStateArg, DoubleArgument targetFractionArg) {
        super(arguments, cpArguments);

        this.targetFractionArg = targetFractionArg;
        this.targetStateArg = targetStateArg;
    }

    @Override
    public void init() {
        try {
            targetFraction = targetFractionArg.next();
            targetState = targetStateArg.next();
            if (targetState == 0) {
                throw new IllegalArgumentException("Dead state (0) set as domination target. Use CheckForExtinction instead.");
            }
        } catch (HaltCondition ex) {
            throw new IllegalStateException(ex);
        }
    }

    @Override
    public void target(GillespieState gs) throws HaltCondition {
        // There's only one event that can happen in this process.
        if (gs != null) {
            gs.add(this.getID(), 1, 0.0D);
        }
    }

    @Override
    public void fire(StepState stepState) throws HaltCondition {
        if (targetState == -1) {
            checkAllStates(stepState);
        } else {
            doCheck(targetState, stepState);
        }
    }

    private void checkAllStates(StepState stepState) throws HaltCondition {
        Integer[] states = getLayer().getViewer().getStateMapViewer().getStates();

        for (Integer targetState : states) {
            // The dead state cannot "dominate" the system (that's extinction)
            if (targetState == 0) {
                continue;
            }
            doCheck(targetState, stepState);
        }

    }

    private void doCheck(int target, StepState stepState) throws HaltCondition {
        double numTargetCells = getLayer().getViewer().getStateMapViewer().getCount(target);
        double numCells = getLayer().getViewer().getOccupiedSites().size();

        double fraction = numTargetCells / numCells;

        if (fraction >= targetFraction) {
            throw new DominationEvent(target);
        }

    }
}
