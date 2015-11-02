package nanoverse.runtime.processes.discrete.filter;

import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.structural.annotations.FactoryTarget;

import java.util.*;
import java.util.function.Consumer;

/**
 * Created by dbborens on 10/19/2015.
 */
public class SampleFilter extends Filter {

    private final int maximum;
    private final Consumer<List<Coordinate>> shuffler;

    /**
     * Main constructor
     */
    @FactoryTarget
    public SampleFilter(int maximum, Random random) {
        this.maximum = maximum;
        shuffler = list -> Collections.shuffle(list, random);
    }

    /**
     * Testing constructor
     */
    public SampleFilter(int maximum, Consumer<List<Coordinate>> shuffler) {
        this.maximum = maximum;
        this.shuffler = shuffler;
    }

    @Override
    public List<Coordinate> apply(List<Coordinate> toFilter) {
        // If maximum is < 0, it means that there is no maximum; return all.
        if (maximum < 0) {
            return toFilter;
        }
        // If there the number of toFilter does not exceed the max, return.
        if (toFilter.size() <= maximum) {
            return toFilter;
        }

        // Otherwise, permute and choose the first n, where n = maximum.
        shuffler.accept(toFilter);

        List<Coordinate> reduced = toFilter.subList(0, maximum);

        return reduced;
    }
}
