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

package nanoverse.runtime.geometry.set;

import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.structural.annotations.FactoryTarget;

import java.util.stream.*;

/**
 * Created by dbborens on 7/28/14.
 */
public class CustomSet extends CoordinateSet {

    @FactoryTarget(displayName = "CustomCoordinateSet")
    public CustomSet(Stream<Coordinate> coordinates) {
        this();
        addAll(coordinates.collect(Collectors.toSet()));
    }

    public CustomSet() {
        super();
    }

    public static CustomSet of(Coordinate... coordinates) {
        CustomSet ret = new CustomSet();
        for (Coordinate c : coordinates) {
            ret.add(c);
        }
        return ret;
    }
}
