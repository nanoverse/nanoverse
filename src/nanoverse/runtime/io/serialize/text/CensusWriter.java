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

package nanoverse.runtime.io.serialize.text;

import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.control.halt.HaltCondition;
import nanoverse.runtime.io.serialize.Serializer;
import nanoverse.runtime.layers.LayerManager;
import nanoverse.runtime.layers.cell.*;
import nanoverse.runtime.processes.StepState;
import nanoverse.runtime.structural.annotations.FactoryTarget;

import java.io.BufferedWriter;
import java.util.*;

/**
 * Writes out the number of each "state" as a function of time.
 *
 * @author dbborens
 */
public class CensusWriter extends Serializer {

    private static final String FILENAME = "census.txt";

    // It is necessary to flush all data at the end of each iteration, rather
    // than after each flush event, because a state may appear for the first
    // time in the middle of the simulation, and we want an accurate column
    // for every observed state in the census table.

//    ArrayList<Integer> frames = new ArrayList<>();

    ArrayList<Integer> frames;
    // The keys to this map are FRAMES. The values are a mapping from STATE
    // number to count. If a state number does not appear, that means the
    // count was zero at that time.
    HashMap<Integer, HashMap<Integer, Integer>> histo;
    //    HashSet<Integer> observedStates = new HashSet<>();
    HashSet<Integer> observedStates;

    private BufferedWriter bw;

    @FactoryTarget
    public CensusWriter(GeneralParameters p, LayerManager lm) {
        super(p, lm);
    }

    @Override
    public void init() {
        super.init();
        histo = new HashMap<>();
        frames = new ArrayList<>();
        observedStates = new HashSet<>();

        String filename = p.getInstancePath() + '/' + FILENAME;
        mkDir(p.getInstancePath(), true);
        bw = makeBufferedWriter(filename);
    }

    public void dispatchHalt(HaltCondition ex) {
        int t = (int) Math.round(ex.getGillespie());
        doFlush(lm.getAgentLayer(), t);
        conclude();
        closed = true;
    }

    private void conclude() {
        // Sort the states numerically
        TreeSet<Integer> sortedStates = new TreeSet<>(observedStates);

        // Write out the header
        StringBuilder line = new StringBuilder();
        line.append("frame");

        for (Integer state : sortedStates) {
            line.append("\t");
            line.append(state);
        }

        line.append("\n");

        hAppend(bw, line);

        TreeSet<Integer> sortedFrames = new TreeSet<>(histo.keySet());
        for (Integer frame : sortedFrames) {
            HashMap<Integer, Integer> observations = histo.get(frame);

            line = new StringBuilder();
            line.append(frame);

            for (Integer state : sortedStates) {
                line.append("\t");

                if (observations.containsKey(state)) {
                    line.append(observations.get(state));
                } else {
                    line.append("0");
                }
            }

            line.append("\n");
            hAppend(bw, line);

        }
        hClose(bw);

    }

    public void close() {
        // Doesn't do anything.
    }

    @Override
    public void flush(StepState stepState) {
        AgentLayer layer = stepState.getRecordedAgentLayer();
        doFlush(layer, stepState.getFrame());
    }

    private void doFlush(AgentLayer layer, int t) {
        frames.add(t);

        // Create a bucket for this frame.
        HashMap<Integer, Integer> observations = new HashMap<>();
        histo.put(t, observations);

        // Iterate over all observed states for this frame.
        StateMapViewer smv = layer.getViewer().getStateMapViewer();
        for (Integer state : smv.getStates()) {
            Integer count = smv.getCount(state);
            observations.put(state, count);
            observedStates.add(state);
        }

    }
}
