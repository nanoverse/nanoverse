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
public class CensusFrameLineBuilder {
    private final HashMap<Integer, HashMap<String, Integer>> histo;

    public CensusFrameLineBuilder(HashMap<Integer, HashMap<String, Integer>> histo) {
        this.histo = histo;
    }

    public String frameLine(Integer frame, List<String> names) {

        String trimmed = names.stream()
            .map(name -> countInstancesInFrame(name, frame))
            .map(count -> count.toString())
            .collect(Collectors.joining("\t"));

        String line = frame + "\t" + trimmed + "\n";
        return line;
    }

    private Integer countInstancesInFrame(String name, Integer frame) {
        HashMap<String, Integer> observations = histo.get(frame);

        if (observations.containsKey(name)) {
            return observations.get(name);
        }

        return 0;
    }
}
