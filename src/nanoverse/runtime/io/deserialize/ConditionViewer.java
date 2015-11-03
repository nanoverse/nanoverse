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

package nanoverse.runtime.io.deserialize;

import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.structural.NotYetImplementedException;

import java.util.HashSet;

@Deprecated
public class ConditionViewer {

    //    private VectorViewer f;
    private String[] names;
    private double gCurrent;
    private int frame;
    private HashSet<Coordinate> highlights;
    private CoordinateDeindexer deindexer;

    //    public ConditionViewer(VectorViewer f, int[] names, HashSet<Coordinate> highlights,
//                           int frame, double gCurrent, CoordinateDeindexer deindexer) {
    public ConditionViewer(String[] names, HashSet<Coordinate> highlights,
                           int frame, double gCurrent, CoordinateDeindexer deindexer) {

//        this.f = f;
        this.names = names;
        this.gCurrent = gCurrent;
        this.deindexer = deindexer;
        this.frame = frame;
        this.highlights = highlights;
    }

    public String getState(Coordinate c) {
        int i = deindexer.getIndex(c);

        return names[i];
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

        return (names[i] == null);
    }

    public double getGillespie() {
        return gCurrent;
    }

    public int getFrame() {
        return frame;
    }

    public String[] getStateVector() {
        return names;
    }
}
