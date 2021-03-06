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

/**
 * Created by dbborens on 10/20/2015.
 */
public class ShoveManager {


    private final CardinalShover cardinalShover;
    private final ShortestPathShover shortestPathShover;
    private final WeightedShover weightedShover;

    public ShoveManager(AgentLayer layer, Random random) {
        cardinalShover = new CardinalShover(layer, random);
        shortestPathShover = new ShortestPathShover(layer, random);
        weightedShover = new WeightedShover(layer, random);
    }

    public ShoveManager(CardinalShover cardinalShover, ShortestPathShover shortestPathShover, WeightedShover weightedShover) {
        this.cardinalShover = cardinalShover;
        this.shortestPathShover = shortestPathShover;
        this.weightedShover = weightedShover;
    }

    /**
     * Push the row of agents at origin toward target, such that origin
     * winds up vacant. Return a list of affected agents.
     *
     * @param origin The site to become vacant.
     * @param target A currently unoccupied site that will become occupied at
     *               the end of the shove process. The entire line of agents,
     *               including the cell at the origin, will have been pushed
     *               in the direction of the target.
     * @return A set of coordinates that were affected by the shove operation.
     */
    public HashSet<Coordinate> shove(Coordinate origin, Coordinate target) throws HaltCondition {
        return shortestPathShover.shove(origin, target);
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
        return cardinalShover.shoveRandom(origin);
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
        return weightedShover.shoveWeighted(origin);
    }
}
