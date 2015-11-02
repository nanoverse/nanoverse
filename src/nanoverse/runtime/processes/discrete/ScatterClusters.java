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

import nanoverse.runtime.control.arguments.*;
import nanoverse.runtime.control.halt.HaltCondition;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.processes.*;
import nanoverse.runtime.processes.discrete.cluster.SeparationStrategyManager;
import nanoverse.runtime.processes.discrete.filter.*;
import nanoverse.runtime.processes.gillespie.GillespieState;
import nanoverse.runtime.structural.annotations.FactoryTarget;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class ScatterClusters extends AgentProcess {

    private final Filter targetFilter;

    private final Consumer<List<Coordinate>> shuffler;
    private final ScatterClustersCountHelper ceilingHelper;
    private final ScatterClustersPlacementHelper placementHelper;
    private List<Coordinate> candidates;

    @FactoryTarget
    public ScatterClusters(BaseProcessArguments arguments,
                           AgentProcessArguments cpArguments,
                           IntegerArgument neighborCount,
                           AgentDescriptor cellDescriptor,
                           SeparationStrategyManager strategy) {

        super(arguments, cpArguments);
        this.ceilingHelper =
            new ScatterClustersCountHelper(getMaxTargets(), neighborCount);
        this.placementHelper = new ScatterClustersPlacementHelper(strategy, cellDescriptor);
        Filter vacancy = new VacancyFilter(getLayer());
        targetFilter = new NotFilter(vacancy);
        shuffler = list -> Collections.shuffle(list, arguments.getGeneralParameters().getRandom());
    }

    public ScatterClusters(BaseProcessArguments arguments, AgentProcessArguments cpArguments, Filter targetFilter, ScatterClustersCountHelper ceilingHelper, ScatterClustersPlacementHelper placementHelper, Consumer<List<Coordinate>> shuffler) {
        super(arguments, cpArguments);
        this.targetFilter = targetFilter;
        this.ceilingHelper = ceilingHelper;
        this.placementHelper = placementHelper;
        this.shuffler = shuffler;
    }

    public void target(GillespieState gs) throws HaltCondition {
        // Construct initial set of candidates

        List<Coordinate> preCandidates = getActiveSites().stream()
            .collect(Collectors.toList());

        candidates = targetFilter.apply(preCandidates);
    }

    public void fire(StepState state) throws HaltCondition {
        shuffler.accept(candidates);
        int n = ceilingHelper.getCeiling();
        int m = ceilingHelper.getNeighborCount();
        Iterator<Coordinate> cIter = candidates.iterator();
        placementHelper.doPlacement(n, m, cIter);
    }


    @Override
    public void init() {
        candidates = null;
    }
}
