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

package nanoverse.runtime.io.serialize.text;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by dbborens on 10/23/2015.
 */
public class CensusLineManager {

    private final HashMap<Integer, HashMap<String, Integer>> histo;
    private final CensusFrameLineBuilder builder;

    public CensusLineManager(HashMap<Integer, HashMap<String, Integer>> histo) {
        this.histo = histo;
        builder = new CensusFrameLineBuilder(histo);
    }

    public CensusLineManager(HashMap<Integer, HashMap<String, Integer>> histo,
                             CensusFrameLineBuilder builder) {
        this.histo = histo;
        this.builder = builder;
    }

    public void writeFrames(TextOutputHandle handle, List<String> names) {
        TreeSet<Integer> sortedFrames = new TreeSet<>(histo.keySet());
        sortedFrames.stream()
            .map(frame -> builder.frameLine(frame, names))
            .forEach(handle::write);
    }


    public void writeHeader(TextOutputHandle handle, List<String> sortedNames) {
        String joined = sortedNames
            .stream()
            .collect(Collectors.joining("\t"));

        String line = buildHeaderLine(joined);
        handle.write(line);
    }

    private String buildHeaderLine(String joined) {
        StringBuilder sb = new StringBuilder();
        sb.append("frame");
        sb.append("\t");
        sb.append(joined);
        sb.append("\n");
        return sb.toString();
    }
}
