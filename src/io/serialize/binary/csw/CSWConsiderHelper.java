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

package io.serialize.binary.csw;

import control.identifiers.*;

import java.util.HashMap;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Created by dbborens on 5/26/2015.
 */
public class CSWConsiderHelper {
    private final HashMap<String, Extrema> extremaMap;
    private final Function<Integer, Coordinate> deindexer;

    public CSWConsiderHelper(HashMap<String, Extrema> extremaMap, Function<Integer, Coordinate> deindexer) {
        this.extremaMap = extremaMap;
        this.deindexer = deindexer;
    }

    public void consider(String id, int frame, Stream<Double> values) {
        int index = 0;
        for(Double value : (Iterable<Double>) values) {
            Extrema extrema = extremaMap.get(id);
            Coordinate c = deindexer.apply(index);
            extrema.consider(value, c, frame);
            index++;
        }
    }
}
