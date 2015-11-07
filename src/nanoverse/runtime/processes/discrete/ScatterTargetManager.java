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

import nanoverse.runtime.control.halt.HaltCondition;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.geometry.set.CoordinateSet;
import nanoverse.runtime.layers.cell.AgentLayer;
import nanoverse.runtime.processes.discrete.filter.*;
import nanoverse.runtime.processes.gillespie.GillespieState;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;

/**
 * Created by dbborens on 10/21/2015.
 */
public class ScatterTargetManager {

    private final Filter siteCleaner;
    private final Supplier<CoordinateSet> activeSites;
    private final Consumer<List<Coordinate>> shuffler;

    private List<Coordinate> candidates;

    public ScatterTargetManager(AgentLayer layer,
                                Supplier<CoordinateSet> activeSites,
                                Random random) {

        this.activeSites = activeSites;

        // Requires that sites be OCCUPIED
        Filter vacancyFilter = new VacancyFilter(layer);

        // Requires that sites be VACANT
        siteCleaner = new NotFilter(vacancyFilter);

        shuffler = list -> Collections.shuffle(list, random);
    }

    public ScatterTargetManager(Filter siteCleaner,
                                Supplier<CoordinateSet> activeSites,
                                Consumer<List<Coordinate>> shuffler) {
        this.siteCleaner = siteCleaner;
        this.activeSites = activeSites;
        this.shuffler = shuffler;
    }

    public void target(GillespieState gs) throws HaltCondition {
        // Construct initial set of candidates
        List<Coordinate> initial = activeSites
            .get().stream().collect(Collectors.toList());

        candidates = siteCleaner
            .apply(initial);


        // Legacy code
        if (gs != null) {
            gs.add(0, candidates.size(), candidates.size() * 1.0D);
        }
    }

    public List<Coordinate> getTargets(int maxTargets) {
        if (maxTargets < 0 || candidates.size() <= maxTargets) {
            return candidates;
        }

        shuffler.accept(candidates);
        return candidates.subList(0, maxTargets);
    }
}
