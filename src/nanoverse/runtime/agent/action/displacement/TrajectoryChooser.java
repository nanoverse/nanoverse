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

import nanoverse.runtime.control.identifiers.*;
import nanoverse.runtime.geometry.Geometry;
import nanoverse.runtime.layers.cell.AgentLayer;
import nanoverse.runtime.structural.RangeMap;
import org.slf4j.*;

import java.util.Random;
import java.util.function.BiFunction;

/**
 * Created by dbborens on 10/20/2015.
 */
public class TrajectoryChooser {
    private final Random random;
    private final BiFunction<Coordinate, Coordinate, CoordinateTupleOptionMap> mapMaker;

    public TrajectoryChooser(Random random) {
        this.random = random;
        mapMaker = (a, b) -> new CoordinateTupleOptionMap(a, b);
    }

    public TrajectoryChooser(Random random,
                             BiFunction<Coordinate, Coordinate, CoordinateTupleOptionMap> mapMaker) {
        this.random = random;
        this.mapMaker = mapMaker;
    }

    public CoordinateTuple getNextTuple(Coordinate currentLocation, Coordinate currentDisplacement) {
        RangeMap<CoordinateTuple> chooser = mapMaker.apply(currentLocation, currentDisplacement);
        double weight = chooser.getTotalWeight();
        double x = random.nextDouble() * weight;
        return chooser.selectTarget(x);
    }

}
