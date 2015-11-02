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

package nanoverse.runtime.processes.discrete.check;

import nanoverse.runtime.control.arguments.Argument;
import nanoverse.runtime.control.halt.*;
import nanoverse.runtime.processes.*;
import nanoverse.runtime.processes.discrete.*;
import nanoverse.runtime.processes.gillespie.GillespieState;
import nanoverse.runtime.structural.annotations.FactoryTarget;

/**
 * Halt the simulation when the target cell type has the specified fraction
 * of the overall live cell population.
 * <p>
 * Checks for extinction or fixation events.
 * <p>
 * Created by dbborens on 1/13/14.
 */
public class CheckForDomination extends AgentProcess {
    private final double targetFraction;
    private final String targetName;

    @FactoryTarget
    public CheckForDomination(BaseProcessArguments arguments, AgentProcessArguments cpArguments, String targetName, Argument<Double> targetFractionArg) {
        super(arguments, cpArguments);
        this.targetName = targetName;
        try {
            targetFraction = targetFractionArg.next();
        } catch (HaltCondition haltCondition) {
            throw new RuntimeException();
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
        if (targetName.equals("")) {
            checkAllStates();
        } else {
            doCheck(targetName);
        }
    }

    @Override
    public void init() {
    }

    private void checkAllStates() throws HaltCondition {
        String[] names = getLayer().getViewer().getNameMapViewer().getNames();

        for (String name : names) {
            // The dead state cannot "dominate" the system (that's extinction)
            if (name == null) {
                continue;
            }
            doCheck(name);
        }

    }

    private void doCheck(String target) throws HaltCondition {
        double numTargetAgents = getLayer().getViewer().getNameMapViewer().getCount(target);
        double numAgents = getLayer().getViewer().getOccupiedSites().count();

        double fraction = numTargetAgents / numAgents;

        if (fraction >= targetFraction) {
            throw new DominationEvent(target);
        }

    }
}
