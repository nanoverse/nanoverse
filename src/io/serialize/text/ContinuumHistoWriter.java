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

import com.sun.org.apache.xpath.internal.operations.Bool;
import control.GeneralParameters;
import control.halt.HaltCondition;
import control.identifiers.Coordinate;
import io.serialize.Serializer;
import layers.LayerManager;
import processes.StepState;

import java.io.BufferedWriter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Writes out the number of each "state" as a function of time.
 *
 * @author dbborens
 */
public class ContinuumHistoWriter extends Serializer {

    private final String FILENAME;
    private final String layerId;
    private final boolean occupiedOnly;

    ArrayList<Integer> frames;

    // The keys to this map are FRAMES. The values are a mapping from VALUE
    // to count. If a state number does not appear, that means the
    // count was zero at that time.
    HashMap<Integer, HashMap<Long, Integer>> histo;

    HashSet<Long> observedValues;

    private BufferedWriter bw;

    public ContinuumHistoWriter(GeneralParameters p, LayerManager lm, String layerId, boolean occupiedOnly) {
        super(p, lm);
        this.layerId = layerId;
        this.occupiedOnly = occupiedOnly;

        FILENAME = layerId + ".histogram.txt";
    }

    @Override
    public void init() {
        super.init();
        histo = new HashMap<>();
        frames = new ArrayList<>();
        observedValues = new HashSet<>();

        String filename = p.getInstancePath() + '/' + FILENAME;
        mkDir(p.getInstancePath(), true);
        bw = makeBufferedWriter(filename);
    }

    @Override
    public void flush(StepState stepState) {
        Stream<Double> stateStream = stepState.getRecordedContinuumValues(layerId);

        Function<Integer, Boolean> isOccupied = i -> {
            Coordinate c = lm.getDeindexer().apply(i);
            return stepState
                    .getRecordedCellLayer()
                    .getViewer()
                    .isOccupied(c);
        };

        doFlush(stateStream, stepState.getFrame(), isOccupied);
    }

    private void doFlush(Stream<Double> valueStream, int t, Function<Integer, Boolean> isOccupied) {
        frames.add(t);

        // Create a bucket for this frame.
        HashMap<Long, Integer> observations = new HashMap<>();
        histo.put(t, observations);

        // TODO This is crap; I did it because I needed to identify coordinates
        List<Double> valueList = valueStream.collect(Collectors.toList());
        int i = 0;
        for (double value : valueList) {
            if (!occupiedOnly || isOccupied.apply(i)) {
                record(value, observations);
            }
            i++;
        }

    }


    private void record(Double value, HashMap<Long, Integer> observations) {
        long bin = Math.round(value);
        if (!observations.containsKey(bin)) {
            observations.put(bin, 0);
        }
        int count = observations.get(bin);
        observations.put(bin, count + 1);
        observedValues.add(bin);
    }

    public void dispatchHalt(HaltCondition ex) {
        int t = (int) Math.round(ex.getGillespie());
        Function<Integer, Boolean> isOccupied = i -> {
            Coordinate c = lm.getDeindexer().apply(i);
            return lm.getCellLayer().getViewer().isOccupied(c);
        };
        doFlush(lm.getContinuumLayer(layerId).getStateStream(), t, isOccupied);
        conclude();
        closed = true;
    }

    private void conclude() {
        // Sort the states numerically
        TreeSet<Long> sortedValues = new TreeSet<>(observedValues);

        // Write out the header
        StringBuilder line = new StringBuilder();
        line.append("frame");

        for (Long values : sortedValues) {
            line.append("\t");
            line.append(values);
        }

        line.append("\n");

        hAppend(bw, line);

        TreeSet<Integer> sortedFrames = new TreeSet<>(histo.keySet());
        for (Integer frame : sortedFrames) {
            HashMap<Long, Integer> observations = histo.get(frame);

            line = new StringBuilder();
            line.append(frame);

            for (Long value : sortedValues) {
                line.append("\t");

                if (observations.containsKey(value)) {
                    line.append(observations.get(value));
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
