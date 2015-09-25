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

package nanoverse.runtime.io.deserialize;

import nanoverse.runtime.structural.utilities.FileConventions;

import java.io.*;
import java.util.ArrayList;

/**
 * Reads out frames and the associated system time from
 * a time data file.
 * <p>
 * Created by dbborens on 3/26/14.
 */
public class TimeReader {

    private double[] times;
    private int[] frames;

    public TimeReader(String root) {
        String filename = FileConventions.TIME_FILENAME;
        String absolute = root + filename;
        DataInputStream stream = FileConventions.makeDataInputStream(absolute);
        loadData(stream);
    }

    private void loadData(DataInputStream stream) {
        boolean eof = false;
        ArrayList<Double> timesList = new ArrayList<>();
        ArrayList<Integer> framesList = new ArrayList<>();

        do {
            try {
                int frame = stream.readInt();
                double time = stream.readDouble();
                timesList.add(time);
                framesList.add(frame);
            } catch (EOFException ex) {
                eof = true;
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        } while (!eof);

        // I think Apache Commons has something for this, but I don't feel
        // like loading an entire dependency just for that
        times = toDoubleArray(timesList);
        frames = toIntegerArray(framesList);
    }

    // Apache commons can do this automagically
    private double[] toDoubleArray(ArrayList<Double> toConvert) {
        int n = toConvert.size();
        double[] ret = new double[n];
        for (int i = 0; i < n; i++) {
            ret[i] = toConvert.get(i);
        }
        return ret;
    }

    // Apache commons can do this automagically
    private int[] toIntegerArray(ArrayList<Integer> toConvert) {
        int n = toConvert.size();
        int[] ret = new int[n];
        for (int i = 0; i < n; i++) {
            ret[i] = toConvert.get(i);
        }
        return ret;
    }


    public double[] getTimes() {
        return times;
    }

    public int[] getFrames() {
        return frames;
    }
}
