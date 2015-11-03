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
import nanoverse.runtime.structural.RangeMap;

import java.util.Random;
import java.util.function.BiFunction;

/**
 * Created by dbborens on 10/20/2015.
 */
public class WeightedShoveTargetChooser {

    private final WeightedShoveTargetBuilder builder;

    public WeightedShoveTargetChooser(AgentLayer layer,
                                      Random random,
                                      ShoveHelper shoveHelper,
                                      BiFunction<Coordinate, Coordinate, Boolean> baseCaseFunction) {

        builder = new WeightedShoveTargetBuilder(layer, random, shoveHelper, baseCaseFunction);
    }

    public WeightedShoveTargetChooser(WeightedShoveTargetBuilder builder) {
        this.builder = builder;
    }

    public Coordinate choose(Coordinate origin) throws HaltCondition {
        RangeMap<Coordinate> rangeMap = builder.buildRangeMap(origin);
        Coordinate chosenTarget = builder.chooseTarget(rangeMap);
        return chosenTarget;
    }

}
