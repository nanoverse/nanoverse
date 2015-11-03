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

import java.util.HashSet;
import java.util.function.BiFunction;

/**
 * Created by dbborens on 10/20/2015.
 */
public class CardinalVacancyWeightCalculator {

    private final CardinalVacancyDistanceCalculator calculator;
    private final AgentLayer layer;

    public CardinalVacancyWeightCalculator(AgentLayer layer, ShoveHelper shoveHelper, BiFunction<Coordinate, Coordinate, Boolean> baseCaseFunction) {
        this.layer = layer;
        calculator = new CardinalVacancyDistanceCalculator(shoveHelper, baseCaseFunction);
    }

    public CardinalVacancyWeightCalculator(CardinalVacancyDistanceCalculator calculator, AgentLayer layer) {
        this.calculator = calculator;
        this.layer = layer;
    }

    /**
     * Calculate the weight to be given to a particular cardinal direction,
     * based on the distance from the origin to the nearest vacancy in that
     * cardinal direction
     */
    public double calcWeight(Coordinate origin, Coordinate target) throws HaltCondition {
        Coordinate displacement = layer.getGeometry().
            getDisplacement(origin,
                target, Geometry.APPLY_BOUNDARIES);
        HashSet<Coordinate> affectedSites = new HashSet<>();
        calculator.calculateDistToVacancy(origin, displacement, affectedSites);
        int dist = affectedSites.size();
        return 1.0 / dist;
    }
}
