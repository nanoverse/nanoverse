package nanoverse.runtime.processes.discrete.filter;

import nanoverse.runtime.control.identifiers.Coordinate;

import java.util.List;
import java.util.stream.*;

/**
 * Inverts the result of another filter.
 * <p>
 * Created by dbborens on 10/21/2015.
 */
public class NotFilter extends Filter {

    private final Filter toInvert;

    public NotFilter(Filter toInvert) {
        this.toInvert = toInvert;
    }

    @Override
    public List<Coordinate> apply(List<Coordinate> toFilter) {
        return toFilter.stream()
            .filter(this::isFiltered)
            .collect(Collectors.toList());
    }

    private boolean isFiltered(Coordinate c) {
        List<Coordinate> query = Stream.of(c).collect(Collectors.toList());
        int count = toInvert.apply(query).size();
        return (count == 0);
    }
}
