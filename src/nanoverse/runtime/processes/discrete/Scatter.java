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

package nanoverse.runtime.processes.discrete;

import nanoverse.runtime.agent.Agent;
import nanoverse.runtime.control.arguments.AgentDescriptor;
import nanoverse.runtime.control.halt.*;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.processes.*;
import nanoverse.runtime.processes.gillespie.GillespieState;
import nanoverse.runtime.structural.annotations.FactoryTarget;

import java.util.List;

public class Scatter extends AgentProcess {

    private final AgentDescriptor cellDescriptor;
    private final ScatterTargetManager targetManager;

    @FactoryTarget
    public Scatter(BaseProcessArguments arguments, AgentProcessArguments cpArguments, AgentDescriptor cellDescriptor) {
        super(arguments, cpArguments);
        this.cellDescriptor = cellDescriptor;
        this.targetManager = new ScatterTargetManager(getLayer(),
            () -> getActiveSites(),
            getGeneralParameters().getRandom());
    }

    public void target(GillespieState gs) throws HaltCondition {
        targetManager.target(gs);
    }

    public void fire(StepState state) throws HaltCondition {
        System.out.println("Executing Scatter.");

        int n = getMaxTargets().next();

        List<Coordinate> targets = targetManager.getTargets(n);

        if (targets.isEmpty()) {
            throw new LatticeFullEvent();
        }

        for (Coordinate target : targets) {
            Agent agent = cellDescriptor.next();
            getLayer().getUpdateManager().place(agent, target);
        }
    }

    @Override
    public void init() {
    }
}
