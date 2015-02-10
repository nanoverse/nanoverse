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

package control.arguments;

import structural.utilities.EpsilonUtil;

import java.util.Random;

/**
 * An argument for eSLIME objects that returns a double-precision
 * floating point within a certain range between minimum (inclusive)
 * and maximum (exclusive), with uniform PDF.
 * <p>
 * Created by David B Borenstein on 4/7/14.
 */
public class UniformDouble extends Argument<Double> {

    private Random random;
    private double range;
    private double offset;

    public UniformDouble(double min, double max, Random random) {
        range = max - min;
        offset = min;
        this.random = random;
    }

    @Override
    public Double next() {
        double o = random.nextDouble();
        return (o * range) + offset;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof UniformDouble)) {
            return false;
        }

        UniformDouble other = (UniformDouble) obj;

        if (!EpsilonUtil.epsilonEquals(range, other.range)) {
            return false;
        }

        if (!EpsilonUtil.epsilonEquals(offset, other.offset)) {
            return false;
        }

        return true;
    }
}
