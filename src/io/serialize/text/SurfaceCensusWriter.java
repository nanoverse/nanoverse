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

package io.serialize.text;

import control.GeneralParameters;
import control.halt.HaltCondition;
import control.identifiers.Coordinate;
import io.serialize.Serializer;
import layers.LayerManager;
import layers.cell.CellLayer;
import processes.StepState;
import structural.annotations.FactoryTarget;

import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeSet;

/**
 * Writes out the number of each "state" as a function of time.
 *
 * @author dbborens
 */
public class SurfaceCensusWriter extends Serializer {

    private static final String FILENAME = "surface_census.txt";

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
    public SurfaceCensusWriter(GeneralParameters p, LayerManager lm) {
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

    private void increment(HashMap<Integer, Integer> observations, int state) {
        if (!observations.containsKey(state)) {
            observations.put(state, 0);
        }

        int value = observations.get(state);
        observations.put(state, value + 1);
        observedStates.add(state);
    }

    private boolean isAtFront(Coordinate c, CellLayer layer) {
        int[] neighborStates = layer.getLookupManager().getNeighborStates(c, false);

        // If any neighbor is 0 (vacant), the point is at the front
        for (int neighborState : neighborStates) {
            if (neighborState == 0) {
                return true;
            }
        }
        // If none of the neighbors are vacant, the point is interior
        return false;
    }

    @Override
    public void flush(StepState stepState) {
        CellLayer layer = stepState.getRecordedCellLayer();
        frames.add(stepState.getFrame());

        // Create a bucket for this frame.
        HashMap<Integer, Integer> observations = new HashMap<>();
        histo.put(stepState.getFrame(), observations);

        // Iterate over all occupied sites.
        for (Coordinate c : layer.getViewer().getOccupiedSites()) {
            // Is it at the front? If so, count it.
            if (isAtFront(c, layer)) {
                int state = layer.getViewer().getState(c);
                increment(observations, state);
            }
        }
    }

    public void dispatchHalt(HaltCondition ex) {
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
}
