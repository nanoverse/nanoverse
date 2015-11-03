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

import java.util.*;
import java.util.function.BiFunction;

/**
 * Created by dbborens on 10/20/2015.
 */
public class WeightedShover {

    private final AgentLayer layer;
    private final ShoveOperationManager operationManager;
    private final ShoveHelper shoveHelper;
    private final WeightedShoveTargetChooser chooser;

    public WeightedShover(AgentLayer layer, Random random) {
        this.layer = layer;

        shoveHelper = new ShoveHelper(layer, random);

        operationManager = new ShoveOperationManager(shoveHelper,
            baseCaseFunction());

        chooser = new WeightedShoveTargetChooser(layer,
            random,
            shoveHelper,
            baseCaseFunction());
    }

    private BiFunction<Coordinate, Coordinate, Boolean> baseCaseFunction() {
        return (current, displacement) -> isBaseCase(current);
    }

    private boolean isBaseCase(Coordinate currentLocation) {
        return (!shoveHelper.isOccupied(currentLocation));
    }

    public WeightedShover(AgentLayer layer, ShoveOperationManager operationManager, ShoveHelper shoveHelper, WeightedShoveTargetChooser chooser) {
        this.layer = layer;
        this.operationManager = operationManager;
        this.shoveHelper = shoveHelper;
        this.chooser = chooser;
    }

    /**
     * shoves starting at the origin in a cardinal direction chosen by weight to nearest
     * vacancy along that direction. shoves until a vacancy is reached or failure.
     *
     * @param origin
     * @return affectedSites
     * @throws HaltCondition
     */
    public HashSet<Coordinate> shoveWeighted(Coordinate origin) throws HaltCondition {
        Coordinate chosenTarget = chooser.choose(origin);
        return doShove(origin, chosenTarget);
    }

    private HashSet<Coordinate> doShove(Coordinate origin, Coordinate chosenTarget) throws HaltCondition {
        Coordinate displacement = layer.getGeometry().
            getDisplacement(origin,
                chosenTarget, Geometry.APPLY_BOUNDARIES);

        HashSet<Coordinate> affectedSites = new HashSet<>();

        operationManager.doShove(origin, displacement, affectedSites);
        return affectedSites;
    }

}
