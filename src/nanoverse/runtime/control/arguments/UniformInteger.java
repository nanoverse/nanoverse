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

package nanoverse.runtime.control.arguments;

import java.util.Random;

/**
 * An argument for eSLIME objects that returns an integer with
 * uniform probability within a specified range.
 * <p>
 * Created by David B Borenstein on 4/7/14.
 */
public class UniformInteger implements IntegerArgument {
    private Random random;
    private int range;
    private int offset;

    public UniformInteger(int min, int max, Random random) {
        range = max - min;
        offset = min;
        this.random = random;
    }

    @Override
    public Integer next() {
        int o = random.nextInt(range);
        int result = o + offset;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof UniformInteger)) {
            return false;
        }

        UniformInteger other = (UniformInteger) obj;

        if (range != other.range) {
            return false;
        }

        if (offset != other.offset) {
            return false;
        }

        return true;
    }
}
