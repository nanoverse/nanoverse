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
