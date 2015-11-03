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

package nanoverse.runtime.structural.utilities;

import no.uib.cipr.matrix.Vector;

@Deprecated
public class VectorViewer {

    protected Vector v;

    private double max;

    private double min;

    private double range;

    public VectorViewer(Vector v, double min, double max) {
        this.v = v;
        this.min = min;
        this.max = max;
        range = max - min;
    }

    public double max() {
        return max;
    }

    public double min() {
        return min;
    }

    public double get(int i) {
        return v.get(i);
    }

    public int size() {
        return v.size();
    }

    /**
     * Returns a value scaled from 0 to 1, with 0 corresponding
     * to the minimum value of the vector and 1 corresponding to the
     * maximum.
     *
     * @param i Index into the vector.
     */
    public double getScaled(int i) {
        if (range == 0)
            return 0;

        // If we are using non-zero extrema, return zeros as zeros; otherwise, normalize
        if (min != 0 && v.get(i) == 0d)
            return 0d;

        if (v.get(i) < min)
            throw new RuntimeException(v.get(i) + " < " + min + " @ " + i);

        double x = (v.get(i) - min) / range;
        return x;
    }

    public double[] getData() {
        double[] data = new double[v.size()];
        for (int i = 0; i < v.size(); i++) {
            data[i] = v.get(i);
        }

        return data;
    }
}

