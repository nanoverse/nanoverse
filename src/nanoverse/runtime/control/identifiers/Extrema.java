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

package nanoverse.runtime.control.identifiers;


import nanoverse.runtime.structural.utilities.EpsilonUtil;

public class Extrema {

    protected double min, max;
    protected TemporalCoordinate argMin, argMax;

    public Extrema(double min, TemporalCoordinate argMin, double max, TemporalCoordinate argMax) {
        this.min = min;
        this.argMin = argMin;
        this.max = max;
        this.argMax = argMax;
    }

    public Extrema() {
        min = Double.POSITIVE_INFINITY;
        argMin = null;

        // Maximum
        max = Double.NEGATIVE_INFINITY;
        argMax = null;
    }

    /**
     * Initialize all fields to a set of loaded values. Used when importing
     * metadata from a previous run.
     */
    public void load(double min, TemporalCoordinate argMin, double max, TemporalCoordinate argMax) {
        this.min = min;
        this.max = max;
        this.argMin = argMin;
        this.argMax = argMax;
    }

    /**
     * Compares a value to the minimum and maximum. If it goes beyond an
     * existing extremum, it gets assigned. Returns true if an assignment
     * was made. If it is the first value checked, by definition
     * it will be both the minimum and maximum.
     */
    public boolean consider(double u, Coordinate c, double t) {
        boolean assigned = false;
        if (u > max) {
            TemporalCoordinate tc = new TemporalCoordinate(c, t);
            max = u;
            argMax = tc;
            assigned = true;
        }

        if (u < min) {
            TemporalCoordinate tc = new TemporalCoordinate(c, t);
            min = u;
            argMin = tc;
            assigned = true;
        }

        return assigned;
    }

    public double min() {
        return min;
    }

    public double max() {
        return max;
    }

    public TemporalCoordinate argMin() {
        return argMin;
    }

    public TemporalCoordinate argMax() {
        return argMax;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Extrema)) {
            return false;
        }

        Extrema other = (Extrema) obj;

        if (!EpsilonUtil.epsilonEquals(other.min, this.min)) {
            return false;
        }

        if (!EpsilonUtil.epsilonEquals(other.max, this.max)) {
            return false;
        }

        if (!(other.argMax.equals(this.argMax))) {
            return false;
        }

        if (!(other.argMin.equals(this.argMin))) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(min);
        sb.append('@');
        appendArgMin(sb);
        sb.append(':');
        sb.append(max);
        sb.append('@');
        appendArgMax(sb);
        return sb.toString();
    }

    private void appendArgMax(StringBuilder sb) {
        if (argMax == null) {
            sb.append("NaN,NaN,NaN");
            return;
        }

        sb.append(argMax.toString());
    }

    private void appendArgMin(StringBuilder sb) {
        if (argMin == null) {
            sb.append("NaN,NaN,NaN");
            return;
        }

        sb.append(argMin.toString());
    }
}
