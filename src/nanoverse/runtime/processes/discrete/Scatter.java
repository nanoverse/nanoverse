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

    public Scatter(BaseProcessArguments arguments, AgentProcessArguments cpArguments, AgentDescriptor cellDescriptor, ScatterTargetManager targetManager) {
        super(arguments, cpArguments);
        this.cellDescriptor = cellDescriptor;
        this.targetManager = targetManager;
    }

    public void target(GillespieState gs) throws HaltCondition {
        targetManager.target(gs);
    }

    public void fire(StepState state) throws HaltCondition {
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
