package nanoverse.runtime.processes.discrete.filter;

import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.layers.cell.AgentLayer;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by dbborens on 10/21/2015.
 */
public class HasNeighborsFilter extends Filter {

    private final AgentLayer layer;

    public HasNeighborsFilter(AgentLayer layer) {
        this.layer = layer;
    }

    @Override
    public List<Coordinate> apply(List<Coordinate> toFilter) {
        return toFilter.stream()
            .filter(coord -> layer
                .getLookupManager()
                .getNeighborNames(coord, true)
                .count() > 0)
            .collect(Collectors.toList());
    }
}
