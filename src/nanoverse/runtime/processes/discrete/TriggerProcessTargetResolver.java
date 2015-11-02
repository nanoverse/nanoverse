package nanoverse.runtime.processes.discrete;

import nanoverse.runtime.agent.Agent;
import nanoverse.runtime.control.halt.HaltCondition;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.geometry.set.CoordinateSet;
import nanoverse.runtime.layers.cell.AgentLayer;
import nanoverse.runtime.processes.discrete.filter.Filter;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.*;

/**
 * Created by dbborens on 10/21/2015.
 */
public class TriggerProcessTargetResolver {

    private final Supplier<CoordinateSet> activeSites;
    private final AgentLayer layer;

    // User-specified filter
    private final Filter userFilter;

    // Filters for legacy options
    private final Filter skipVacancyFilter;
    private final Filter hasNeighborsFilter;
    private final Filter maxTargetsFilter;

    public TriggerProcessTargetResolver(Supplier<CoordinateSet> activeSites,
                                        AgentLayer layer,
                                        Filter userFilter,
                                        Filter skipVacancyFilter,
                                        Filter hasNeighborsFilter,
                                        Filter maxTargetsFilter) {

        this.activeSites = activeSites;
        this.layer = layer;
        this.userFilter = userFilter;
        this.skipVacancyFilter = skipVacancyFilter;
        this.hasNeighborsFilter = hasNeighborsFilter;
        this.maxTargetsFilter = maxTargetsFilter;
    }

    public Stream<Agent> resolveTargets() throws HaltCondition {
        List<Coordinate> initial = activeSites.get()
            .stream()
            .collect(Collectors.toList());

        List<Coordinate> vacancyFiltered = skipVacancyFilter.apply(initial);
        List<Coordinate> neighborhoodFiltered = hasNeighborsFilter.apply(vacancyFiltered);
        List<Coordinate> userFiltered = userFilter.apply(neighborhoodFiltered);
        List<Coordinate> maxTargetsFiltered = maxTargetsFilter.apply(userFiltered);

        return maxTargetsFiltered
            .stream()
            .map(c -> layer.getViewer().getAgent(c));
    }
}
