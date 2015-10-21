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
        if (maxTargets < 0) {
            return candidates;
        }

        shuffler.accept(candidates);
        return candidates.subList(0, maxTargets);
    }
}
