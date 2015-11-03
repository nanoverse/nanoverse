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
import nanoverse.runtime.geometry.Geometry;
import nanoverse.runtime.layers.cell.AgentLayer;
import nanoverse.runtime.structural.RangeMap;

import java.util.Random;
import java.util.function.BiFunction;

/**
 * Created by dbborens on 10/20/2015.
 */
public class WeightedShoveTargetBuilder {
    private final AgentLayer layer;
    private final Random random;
    private final CardinalVacancyWeightCalculator calculator;

    public WeightedShoveTargetBuilder(AgentLayer layer,
                                      Random random,
                                      ShoveHelper shoveHelper,
                                      BiFunction<Coordinate, Coordinate, Boolean> baseCaseFunction) {

        this.layer = layer;
        this.random = random;
        calculator = new CardinalVacancyWeightCalculator(layer, shoveHelper, baseCaseFunction);
    }

    public WeightedShoveTargetBuilder(AgentLayer layer, Random random, CardinalVacancyWeightCalculator calculator) {
        this.layer = layer;
        this.random = random;
        this.calculator = calculator;
    }

    public RangeMap<Coordinate> buildRangeMap(Coordinate origin) throws HaltCondition {
        Coordinate[] neighbors = layer.getGeometry().getNeighbors(origin, Geometry.APPLY_BOUNDARIES);
        RangeMap<Coordinate> rangeMap = new RangeMap<>(neighbors.length);

        for (Coordinate neighbor : neighbors) {
            double weight = calculator.calcWeight(origin, neighbor);
            rangeMap.add(neighbor, weight);
        }

        return rangeMap;
    }

    public Coordinate chooseTarget(RangeMap<Coordinate> rangeMap) {
        double range = rangeMap.getTotalWeight();
        double x = random.nextDouble() * range;
        Coordinate chosen = rangeMap.selectTarget(x);
        return chosen;
    }
}
