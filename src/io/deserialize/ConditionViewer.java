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

import control.identifiers.Coordinate;
import structural.NotYetImplementedException;

import java.util.HashSet;

@Deprecated
public class ConditionViewer {

//    private VectorViewer f;
    private int[] states;
    private double gCurrent;
    private int frame;
    private HashSet<Coordinate> highlights;
    private CoordinateDeindexer deindexer;

//    public ConditionViewer(VectorViewer f, int[] states, HashSet<Coordinate> highlights,
//                           int frame, double gCurrent, CoordinateDeindexer deindexer) {
    public ConditionViewer(int[] states, HashSet<Coordinate> highlights,
        int frame, double gCurrent, CoordinateDeindexer deindexer) {

//        this.f = f;
        this.states = states;
        this.gCurrent = gCurrent;
        this.deindexer = deindexer;
        this.frame = frame;
        this.highlights = highlights;
    }

    public int getState(Coordinate c) {
        int i = deindexer.getIndex(c);

        return states[i];
    }

    public double getHealth(Coordinate c) {
        throw new NotYetImplementedException();
//        int i = deindexer.getIndex(c);
//        return f.get(i);
    }

    public HashSet<Coordinate> getHighlights() {
        return highlights;
    }

    public boolean isVacant(Coordinate c) {
        int i = deindexer.getIndex(c);

        return (states[i] == 0);
    }

    public double getGillespie() {
        return gCurrent;
    }

    public int getFrame() {
        return frame;
    }

    public int[] getStateVector() {
        return states;
    }

//    public double[] getHealthVector() {
//        return f.getData();
//    }
}
