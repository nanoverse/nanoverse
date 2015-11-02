package nanoverse.runtime.processes.discrete.filter;

import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.layers.cell.*;
import nanoverse.runtime.structural.annotations.FactoryTarget;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by dbborens on 10/21/2015.
 */
public class VacancyFilter extends Filter {
    private final AgentLayer layer;

    @FactoryTarget
    public VacancyFilter(AgentLayer layer) {
        this.layer = layer;
    }

    @Override
    public List<Coordinate> apply(List<Coordinate> toFilter) {

        return toFilter.stream()
            .filter(this::isOccupied)
            .collect(Collectors.toList());
    }

    private boolean isOccupied(Coordinate coordinate) {
        AgentLayerViewer viewer = layer.getViewer();
        return viewer.isOccupied(coordinate);
    }
}
