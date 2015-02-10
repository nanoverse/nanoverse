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

/**
 * This class is exactly equivalent to a Double object, except it extends
 * Argument, which is the base class for eSLIME factory arguments.
 * <p>
 * Created by David B Borenstein on 4/7/14.
 */
public class ConstantDouble extends Argument<Double> {

    private Double value;

    public ConstantDouble(Double value) {
        this.value = value;
    }

    @Override
    public Double next() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ConstantDouble)) {
            return false;
        }

        ConstantDouble other = (ConstantDouble) obj;

        if (!EpsilonUtil.epsilonEquals(value, other.value)) {
            return false;
        }

        return true;
    }
}
