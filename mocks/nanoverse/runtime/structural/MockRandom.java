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

package nanoverse.runtime.structural;

import java.util.Random;

/**
 * Created by dbborens on 5/15/14.
 */
public class MockRandom extends Random {
    private boolean booleanValue;

    private int nextIntValue = -1;

    @Override
    /**
     * Returns either the highest possible value, or,
     * if overriden, the specified value.
     */
    public int nextInt(int n) {
        if (nextIntValue == -1) {
            return n - 1;
        } else {
            return nextIntValue;
        }
    }

    @Override
    public boolean nextBoolean() {
        return booleanValue;
    }

    public void setNextIntValue(int nextIntValue) {
        this.nextIntValue = nextIntValue;
    }

    public void setBooleanValue(boolean booleanValue) {
        this.booleanValue = booleanValue;
    }
}
