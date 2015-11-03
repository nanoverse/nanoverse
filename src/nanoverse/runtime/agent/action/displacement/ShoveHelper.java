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

import nanoverse.runtime.control.halt.HaltCondition;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.layers.cell.AgentLayer;

import java.util.Random;

/**
 * Created by dbborens on 10/20/2015.
 */
public class ShoveHelper {

    private final AgentLayer layer;
    private final TrajectoryChooser chooser;

    public ShoveHelper(AgentLayer layer, Random random) {
        this.layer = layer;
        chooser = new TrajectoryChooser(random);
    }

    public ShoveHelper(AgentLayer layer, TrajectoryChooser chooser) {
        this.layer = layer;
        this.chooser = chooser;
    }

    public boolean isOccupied(Coordinate c) {
        return layer.getViewer().isOccupied(c);
    }

    public void swap(Coordinate p, Coordinate q) throws HaltCondition {
        layer.getUpdateManager().swap(p, q);
    }

    public CoordinateTuple getNextTuple(Coordinate currentLocation, Coordinate d) {
        return chooser.getNextTuple(currentLocation, d);
    }

}
