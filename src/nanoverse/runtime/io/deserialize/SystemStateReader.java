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

import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.control.identifiers.Extrema;
import nanoverse.runtime.geometry.Geometry;
import nanoverse.runtime.io.deserialize.agent.AgentNameIterator;
import nanoverse.runtime.io.deserialize.continuum.*;
import nanoverse.runtime.layers.LightweightSystemState;
import nanoverse.runtime.layers.SystemState;

import java.util.*;
import java.util.stream.Stream;

/**
 * Created by dbborens on 3/26/14.
 */
public class SystemStateReader implements Iterable<LightweightSystemState> {

    private final SystemStateIterator iterator;

    public SystemStateReader(int[] channelIds,
                               GeneralParameters p, Geometry geometry) {
        iterator = new SystemStateIterator(channelIds, p, geometry);
    }

    public SystemStateReader(SystemStateIterator iterator) {
        this.iterator = iterator;
    }

    public double[] getTimes() {
        return iterator.getTimes();
    }

    public int[] getFrames() {
        return iterator.getFrames();
    }

    @Override
    public Iterator<LightweightSystemState> iterator() {
        return iterator;
    }

}
