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

package nanoverse.runtime.agent.action.displacement;

import nanoverse.runtime.control.identifiers.Coordinate;

/**
 * Created by dbborens on 10/21/2015.
 */
public class DisplacementOption {
    // This is the site that would start out occupied and end up vacant
    // (unless occupied == vacant).
    private final Coordinate occupied;

    // This is the site that would start out vacant and end up occupied
    // (unless occupied == vacant).
    private final Coordinate vacant;

    // L1 distance between origin and vacancy.
    private final int distance;

    public DisplacementOption(Coordinate occupied, Coordinate vacant, int distance) {
        this.occupied = occupied;
        this.vacant = vacant;
        this.distance = distance;
    }

    public Coordinate getOccupied() {
        return occupied;
    }

    public Coordinate getVacant() {
        return vacant;
    }

    public int getDistance() {
        return distance;
    }
}
