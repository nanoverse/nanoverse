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

package nanoverse.runtime.io.deserialize;

import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.layers.LightweightSystemState;
import nanoverse.runtime.structural.utilities.FileConventions;

import java.io.*;
import java.util.*;

/**
 * Loads highlights from all channels and makes them available
 * as sets.
 * <p>
 * Created by dbborens on 3/26/14.
 */
public class HighlightReader {

    private HashMap<Integer, DataInputStream> inputs;
    private CoordinateDeindexer deindexer;

    public HighlightReader(String root, int[] channels, CoordinateDeindexer deindexer) {
        this.deindexer = deindexer;
        inputs = new HashMap<>(channels.length);
        for (Integer channel : channels) {
            String filename = FileConventions.makeHighlightFilename(channel);
            String fullPath = root + filename;
            DataInputStream stream = FileConventions.makeDataInputStream(fullPath);
            inputs.put(channel, stream);
        }
    }

    /**
     * Load highlight data into a SystemState container.
     */
    public void populate(LightweightSystemState state) {
        Map<Integer, Set<Coordinate>> highlightsMap = next();

        for (Integer channel : highlightsMap.keySet()) {
            Set<Coordinate> highlights = highlightsMap.get(channel);
            state.setHighlights(channel, highlights);
        }
    }

    public Map<Integer, Set<Coordinate>> next() {
        Map<Integer, Set<Coordinate>> ret = new HashMap<>(inputs.size());
        for (Integer channel : inputs.keySet()) {
            DataInputStream channelInput = inputs.get(channel);
            Set<Coordinate> coordinates = loadNext(channelInput);
            ret.put(channel, coordinates);
        }

        return ret;
    }

    private Set<Coordinate> loadNext(DataInputStream channelInput) {
        try {
            return doLoadNext(channelInput);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private Set<Coordinate> doLoadNext(DataInputStream input) throws IOException {
        Coordinate[] coordinates = PrimitiveDeserializer.readCoordinateVector(input, deindexer);

        Set<Coordinate> ret = new HashSet<Coordinate>(coordinates.length);
        for (Coordinate c : coordinates) {
            ret.add(c);
        }

        return ret;
    }
}
