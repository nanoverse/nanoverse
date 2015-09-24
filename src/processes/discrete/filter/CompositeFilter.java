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

package processes.discrete.filter;

import com.google.common.collect.Lists;
import control.identifiers.Coordinate;
import structural.annotations.FactoryTarget;

import java.util.Arrays;
import java.util.List;
import java.util.stream.*;

/**
 * Created by dbborens on 5/5/14.
 */
public class CompositeFilter extends Filter {
    private List<Filter> childList;

    @FactoryTarget
    public CompositeFilter(Stream<Filter> children) {
        childList = children.collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CompositeFilter that = (CompositeFilter) o;

        if (!childList.equals(that.childList)) return false;

        return true;
    }

    @Override
    public List<Coordinate> apply(List<Coordinate> toFilter) {
        for (Filter child : childList) {
            toFilter = child.apply(toFilter);
        }

        return toFilter;
    }
}
