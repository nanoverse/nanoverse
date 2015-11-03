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
import nanoverse.runtime.control.halt.HaltCondition;
import nanoverse.runtime.processes.*;
import nanoverse.runtime.processes.discrete.filter.*;
import nanoverse.runtime.processes.gillespie.GillespieState;
import nanoverse.runtime.structural.annotations.FactoryTarget;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Causes agents within the active area to perform the specified behavior.
 * Created by David B Borenstein on 2/15/14.
 */
public class TriggerProcess extends AgentProcess {
    private final String behaviorName;

    private final TriggerProcessTargetResolver targetResolver;

    // We use a cell array because triggering may move agents
    private List<Agent> targets;

    @FactoryTarget
    public TriggerProcess(BaseProcessArguments arguments, AgentProcessArguments cpArguments,
                          String behaviorName,
                          Filter filter,
                          boolean skipVacant,
                          boolean requireNeighbors) {
        super(arguments, cpArguments);
        this.behaviorName = behaviorName;
        Filter userFilter = filter;

        // Legacy logic -- delete when time
        Filter skipVacancyFilter = skipVacant ? new VacancyFilter(getLayer()) : new NullFilter();
        Filter hasNeighborsFilter = requireNeighbors ? new HasNeighborsFilter(getLayer()) : new NullFilter();

        int maxTargets;
        try {
            maxTargets = getMaxTargets().next();
        } catch (HaltCondition ex) {
            throw new RuntimeException(ex);
        }

        Filter maxTargetsFilter = new SampleFilter(maxTargets, arguments.getGeneralParameters().getRandom());
        targetResolver = new TriggerProcessTargetResolver(() -> getActiveSites(),
            getLayer(),
            userFilter,
            skipVacancyFilter,
            hasNeighborsFilter,
            maxTargetsFilter);
    }

    public TriggerProcess(BaseProcessArguments arguments, AgentProcessArguments cpArguments, String behaviorName, TriggerProcessTargetResolver targetResolver) {
        super(arguments, cpArguments);
        this.behaviorName = behaviorName;
        this.targetResolver = targetResolver;
    }

    @Override
    public void target(GillespieState gs) throws HaltCondition {
        targets = targetResolver
            .resolveTargets()
            .collect(Collectors.toList());

        if (gs != null) {
            gs.add(getID(), 1, 1D);
        }

    }

    @Override
    public void fire(StepState state) throws HaltCondition {
        for (Agent target : targets) {

            // If the cell has been removed as a result of firing the trigger
            // process in a previous target, skip it.
            if (!getLayer().getViewer().exists(target)) {
                continue;
            }

            // A null caller on the trigger method means that the caller is
            // a process rather than a cell.
            target.trigger(behaviorName, null);
        }

    }

    @Override
    public void init() {
        targets = null;
    }
}
