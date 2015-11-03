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

import java.util.*;
import java.util.function.BiFunction;

/**
 * CardinalShover pushes on agents in a random cardinal direction until a
 * vacancy is reached.
 * <p>
 * Created by dbborens on 10/20/2015.
 */
public class CardinalShover {

    private final CardinalShoverTargetHelper targetHelper;
    private final ShoveHelper shoveHelper;
    private final ShoveOperationManager operationManager;

    public CardinalShover(AgentLayer layer, Random random) {
        shoveHelper = new ShoveHelper(layer, random);
        targetHelper = new CardinalShoverTargetHelper(layer, random);
        operationManager = new ShoveOperationManager(shoveHelper, baseCaseFunction());
    }

    private BiFunction<Coordinate, Coordinate, Boolean> baseCaseFunction() {
        return (current, displacement) -> isBaseCase(current);
    }

    private boolean isBaseCase(Coordinate currentLocation) {
        return (!shoveHelper.isOccupied(currentLocation));
    }

    public CardinalShover(CardinalShoverTargetHelper targetHelper,
                          ShoveHelper shoveHelper,
                          ShoveOperationManager operationManager) {

        this.targetHelper = targetHelper;
        this.shoveHelper = shoveHelper;
        this.operationManager = operationManager;
    }

    /**
     * shoves starting at the origin in a randomly chosen cardinal direction until
     * a vacancy is reached or failure.
     *
     * @param origin
     * @return affectedSites
     * @throws HaltCondition
     */
    public HashSet<Coordinate> shoveRandom(Coordinate origin) throws HaltCondition {
        Coordinate displacement = targetHelper.getDisplacementToRandomTarget(origin);
        return doShove(origin, displacement);
    }

    public HashSet<Coordinate> doShove(Coordinate origin, Coordinate displacement) throws HaltCondition {
        HashSet<Coordinate> affectedSites = new HashSet<>();
        operationManager.doShove(origin, displacement, affectedSites);
        return affectedSites;
    }
}
