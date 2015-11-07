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

import nanoverse.runtime.layers.cell.AgentLayer;

import java.util.*;

/**
 * Created by dbborens on 10/23/2015.
 */
public class CensusFlushHelper {
    private final HashSet<String> observedNames;
    private final ArrayList<Integer> frames;
    private final HashMap<Integer, HashMap<String, Integer>> histo;
    private final CensusObservationRecorder recorder;

    public CensusFlushHelper(HashSet<String> observedNames, ArrayList<Integer> frames, HashMap<Integer, HashMap<String, Integer>> histo) {
        this.observedNames = observedNames;
        this.frames = frames;
        this.histo = histo;
        recorder = new CensusObservationRecorder(observedNames);
    }

    public CensusFlushHelper(HashSet<String> observedNames,
                             ArrayList<Integer> frames,
                             HashMap<Integer, HashMap<String, Integer>> histo,
                             CensusObservationRecorder recorder) {

        this.observedNames = observedNames;
        this.frames = frames;
        this.histo = histo;
        this.recorder = recorder;
    }

    public void doFlush(AgentLayer layer, int t) {
        frames.add(t);

        // Create a bucket for this frame.
        HashMap<String, Integer> observations = new HashMap<>();
        histo.put(t, observations);

        // Iterate over all observed states for this frame.
        recorder.recordObservations(layer, observations);
    }

    public void init() {
        histo.clear();
        frames.clear();
        observedNames.clear();
    }

}
