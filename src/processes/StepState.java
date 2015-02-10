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

package processes;

import control.identifiers.Coordinate;
import layers.cell.CellLayer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * State object for a cycle in a simulation. Each
 * process can modify this state object as necessary.
 * <p>
 * When the processes are done, information from this
 * state object is passed to appropriate downstream
 * elements.
 *
 * @author dbborens
 */
public class StepState {

    private CellLayer recordedCellLayer;
    private boolean recorded;

    private HashMap<Integer, Set<Coordinate>> highlights;
    private double dt;
    private double startTime;
    private int frame;

    public StepState(double startTime, int frame) {
        highlights = new HashMap<>();
        dt = 0;
        this.startTime = startTime;
        this.frame = frame;
        this.recorded = false;
    }

    public void highlight(Coordinate c, Integer channel) {
        if (!highlights.containsKey(channel)) {
            highlights.put(channel, new HashSet<Coordinate>());
        }
        Set<Coordinate> set = highlights.get(channel);
        set.add(c);
    }

    public void advanceClock(double time) {
        dt += time;
    }

    public double getDt() {
        return dt;
    }

    public Coordinate[] getHighlights(Integer channel) {
        if (!highlights.containsKey(channel)) {
            return new Coordinate[0];
        }

        Set<Coordinate> set = highlights.get(channel);

        return set.toArray(new Coordinate[set.size()]);
    }

    public boolean isRecorded() {
        return recorded;
    }

    public void record(CellLayer cellLayer) {
        recordedCellLayer = cellLayer.clone();
        recorded = true;

    }

    public double getTime() {
        return startTime + dt;
    }

    public int getFrame() {
        return frame;
    }

    public CellLayer getRecordedCellLayer() {
        return recordedCellLayer;
    }
}
