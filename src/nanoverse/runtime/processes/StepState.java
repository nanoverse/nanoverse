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

package nanoverse.runtime.processes;

import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.layers.LayerManager;
import nanoverse.runtime.layers.cell.CellLayer;

import java.util.*;
import java.util.stream.*;

/**
 * State object for a cycle in a simulation. Each
 * process can modify this state object as necessary.
 * <p>
 * When the nanoverse.runtime.processes are done, information from this
 * state object is passed to appropriate downstream
 * elements.
 *
 * @author dbborens
 */
public class StepState {

    private final HashMap<Integer, Set<Coordinate>> highlights;
    private final double startTime;
    private final int frame;
    private final HashMap<String, List<Double>> continuumValues;
    private CellLayer recordedCellLayer;
    private boolean recorded;
    private double dt;

    public StepState(double startTime, int frame, HashMap<Integer, Set<Coordinate>> highlights, HashMap<String, List<Double>> continuumValues) {
        this.highlights = highlights;
        this.continuumValues = continuumValues;
        this.startTime = startTime;
        this.frame = frame;
        dt = 0;
        this.recorded = false;
    }

    public StepState(double startTime, int frame) {
        highlights = new HashMap<>();
        continuumValues = new HashMap<>();
        dt = 0;
        this.startTime = startTime;
        this.frame = frame;
        this.recorded = false;
    }

    public void highlight(Coordinate c, Integer channel) {
        if (!highlights.containsKey(channel)) {
            highlights.put(channel, new HashSet<>());
        }
        Set<Coordinate> set = highlights.get(channel);
        set.add(c);
    }

    public void advanceClock(double time) {
        dt += time;
        System.out.println(frame + "\t" + startTime + "\t" + dt + "\t" + getTime());
    }

    public double getTime() {
        return startTime + dt;
    }

    public double getDt() {
        return dt;
    }

    public Stream<Coordinate> getHighlights(Integer channel) {
        if (!highlights.containsKey(channel)) {
            return Stream.empty();
        }

        return highlights.get(channel).stream();
    }

    public boolean isRecorded() {
        return recorded;
    }

    public void record(LayerManager layerManager) {
        recordedCellLayer = layerManager.getCellLayer().clone();
        layerManager.getContinuumLayerIds().forEach(id -> {
            List<Double> values = layerManager
                .getContinuumLayer(id)
                .getStateStream()
                .collect(Collectors.toList());

            continuumValues.put(id, values);
        });
        recorded = true;
    }

    public int getFrame() {
        return frame;
    }

    public CellLayer getRecordedCellLayer() {
        return recordedCellLayer;
    }

    public Stream<Double> getRecordedContinuumValues(String id) {
        return continuumValues.get(id).stream();
    }
}
