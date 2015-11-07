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

package nanoverse.runtime.processes.gillespie;

import nanoverse.runtime.structural.RangeMap;

/**
 * @author dbborens
 * @tested GillespieTest.java
 */
public class GillespieChooser {

    private RangeMap<Integer> chooser;

    public GillespieChooser(GillespieState state) {
        chooser = new RangeMap<>();

        for (Integer processId : state.getKeys()) {
            double weight = state.getWeight(processId);
            chooser.add(processId, weight);
        }
    }

    /**
     * Returns a particular process based on an input between 0
     * and the total weight, with nanoverse.runtime.processes sorted numerically
     * by ID number.
     * <p>
     * So if w_i is the weight of process i, then you'll get back
     * <p>
     * 0 <= x < w_1         --> return process 1's id
     * w_1 <= x < w_1 + w_2 --> return process 2's id
     * ...
     * w_n-1 <= x < sum(w)  --> return process n's id
     *
     * @return
     */
    public Integer selectTarget(double x) {
        return chooser.selectTarget(x);
    }
}
