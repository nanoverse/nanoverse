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

package io.deserialize;

import geometry.Geometry;
import layers.LightweightSystemState;

import java.util.Iterator;

/**
 * Created by dbborens on 3/26/14.
 */
public class SystemStateReader implements Iterable<LightweightSystemState> {

    // A list of all time points measured in the data files, as measured
    // in system time.
    private double[] times;

    // The time points in times correspond to some number of cycles through
    // the simulation (frames).
    private int[] frames;
    // Coordinate deindexers convert from a numeric index to a coordinate.
    private CoordinateDeindexer deindexer;

    // Where are we in the time series being loaded?
    private int cursor;

    /* Data handles */
    private HighlightReader highlightReader;
    private LegacyCellStateReader cellStateReader;
//    private ContinuumStateReaderManager continuumStateReaderManager;

    private Geometry geometry;

    public SystemStateReader(String[] soluteIds, int[] channelIds,
                             String fileRoot, Geometry geometry) {
        cursor = 0;

        // Load coordinate de-indexer.
        deindexer = new CoordinateDeindexer(fileRoot);

        // Load frame-time linkage file.
        loadTime(fileRoot);

        // Open ContinuumStateReader object for each continuum field.
//        continuumStateReaderManager = new ContinuumStateReaderManager(fileRoot, soluteIds);

        // Open handle to data file for each highlght channel.
        highlightReader = new HighlightReader(fileRoot, channelIds, deindexer);

        // Open handle to data file for cell state vector.
        cellStateReader = new LegacyCellStateReader(fileRoot, deindexer);

        this.geometry = geometry;
    }


    private void loadTime(String fileRoot) {
        TimeReader timeReader = new TimeReader(fileRoot);
        times = timeReader.getTimes();
        frames = timeReader.getFrames();
    }

    public double[] getTimes() {
        return times;
    }

    public int[] getFrames() {
        return frames;
    }

    private void setTimeAndFrame(LightweightSystemState state) {
        double time = times[cursor];
        int frame = frames[cursor];
        state.setTime(time);
        state.setFrame(frame);
    }

    @Override
    public Iterator<LightweightSystemState> iterator() {
        return new SystemStateIterator();
    }

    private class SystemStateIterator implements Iterator<LightweightSystemState> {

        @Override
        public LightweightSystemState next() {

            // Construct display object
            LightweightSystemState state = new LightweightSystemState(geometry);

            // Populate time and frame
            setTimeAndFrame(state);

            // Populate cell states
            cellStateReader.populate(state);

            // Populate state of continuum fields
//            continuumStateReaderManager.populate(state);

            // Populate cell change highlights
            highlightReader.populate(state);

            // Advance the cursor
            cursor++;

            // Return display object
            return state;
        }

        public boolean hasNext() {
            return (cursor < frames.length);
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

}
