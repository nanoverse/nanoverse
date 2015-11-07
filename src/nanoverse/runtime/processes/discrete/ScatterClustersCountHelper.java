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

package nanoverse.runtime.processes.discrete;

import nanoverse.runtime.control.arguments.IntegerArgument;
import nanoverse.runtime.control.halt.HaltCondition;

/**
 * Created by dbborens on 10/21/2015.
 */
public class ScatterClustersCountHelper {
    private final IntegerArgument maxTargets;
    private final IntegerArgument neighborCount;

    public ScatterClustersCountHelper(IntegerArgument maxTargets,
                                      IntegerArgument neighborCount) {
        this.maxTargets = maxTargets;
        this.neighborCount = neighborCount;
    }

    public int getCeiling() {
        try {
            int n = maxTargets.next();
            if (n < 0) {
                throw new IllegalArgumentException("Scatter cluster process requires >= 0 max targets.");
            }

            return n;
        } catch (HaltCondition ex) {
            throw new RuntimeException("Unexpected halt condition", ex);
        }
    }

    public int getNeighborCount() {
        int m;

        try {
            m = neighborCount.next();
        } catch (HaltCondition ex) {
            throw new RuntimeException("Unexpected halt condition", ex);
        }

        return m;
    }

}
