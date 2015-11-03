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
import org.slf4j.*;

import java.util.HashSet;
import java.util.function.BiFunction;

/**
 * Created by dbborens on 10/20/2015.
 */
public class ShoveOperationManager {
    private final Logger logger;
    private final ShoveHelper helper;
    private final BiFunction<Coordinate, Coordinate, Boolean> isBaseCase;

    public ShoveOperationManager(ShoveHelper helper, BiFunction<Coordinate, Coordinate, Boolean> isBaseCase) {
        logger = LoggerFactory.getLogger(ShoveOperationManager.class);
        this.helper = helper;
        this.isBaseCase = isBaseCase;
    }

    /**
     * @param currentLocation: starting location. the child will be placed in this
     *                         position after the parent is shoved.
     * @param displacement:    displacement vector to target, in natural basis of lattice.
     *                         this will be the same for each shove.
     * @param sites:           list of affected sites (for highlighting)
     *                         <p>
     */
    public void doShove(Coordinate currentLocation, Coordinate displacement, HashSet<Coordinate> sites) throws HaltCondition {

        logger.debug("Shoving. Origin: " + currentLocation + ". Displacement: " + displacement + ".");

        if (isBaseCase.apply(currentLocation, displacement)) {
            return;
        }

        CoordinateTuple tuple = helper.getNextTuple(currentLocation, displacement);
        Coordinate nextDisplacement = tuple.getDisplacement();
        Coordinate nextLocation = tuple.getOrigin();

        // use the same displacement vector d each time
        doShove(nextLocation, nextDisplacement, sites);

        helper.swap(currentLocation, nextLocation);

        sites.add(nextLocation);
    }
}
