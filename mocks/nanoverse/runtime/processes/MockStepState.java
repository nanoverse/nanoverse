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

import java.util.*;
import java.util.stream.*;

/**
 * Created by David B Borenstein on 4/20/14.
 */
public class MockStepState extends StepState {

    boolean record = false;
    private HashMap<Integer, List<Coordinate>> highlightMap;

    public MockStepState() {
        this(0.0);
    }

    public MockStepState(double startTime) {
        this(startTime, 0);
    }

    public MockStepState(double startTime, int frame) {
        super(startTime, frame);
        highlightMap = new HashMap<>();
    }

    @Override
    public Stream<Coordinate> getHighlights(Integer channel) {
        return highlightMap.get(channel).stream();
    }

    public boolean isRecorded() {
        return record;
    }

    @Override
    public void record(LayerManager layerManager) {
        super.record(layerManager);
        record = true;
    }

    public void setHighlights(Integer channel, Stream<Coordinate> highlights) {
        highlightMap.put(channel, highlights.collect(Collectors.toList()));
    }

    public void setRecord(boolean record) {
        this.record = record;
    }
}
