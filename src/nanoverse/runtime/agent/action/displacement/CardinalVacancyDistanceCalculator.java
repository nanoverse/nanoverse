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

import java.util.HashSet;
import java.util.function.BiFunction;

/**
 * Created by dbborens on 10/20/2015.
 */
public class CardinalVacancyDistanceCalculator {

    private final ShoveHelper shoveHelper;
    private final BiFunction<Coordinate, Coordinate, Boolean> baseCaseFunction;

    public CardinalVacancyDistanceCalculator(ShoveHelper shoveHelper, BiFunction<Coordinate, Coordinate, Boolean> baseCaseFunction) {
        this.shoveHelper = shoveHelper;
        this.baseCaseFunction = baseCaseFunction;
    }

    public void calculateDistToVacancy(Coordinate currentLocation, Coordinate d, HashSet<Coordinate> sites) throws HaltCondition {
        if (baseCaseFunction.apply(currentLocation, d) == true) {
            return;
        }

        CoordinateTuple tuple = shoveHelper.getNextTuple(currentLocation, d);
        Coordinate nextLocation = tuple.getOrigin();
        Coordinate nextDisplacement = tuple.getDisplacement();

        calculateDistToVacancy(nextLocation, nextDisplacement, sites);
        sites.add(nextLocation);
    }
}
