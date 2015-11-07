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

package nanoverse.runtime.layers.cell;

import nanoverse.runtime.agent.Agent;
import nanoverse.runtime.control.identifiers.Coordinate;

public class MockAgentLayerIndices extends AgentLayerIndices {

    private AgentIndex occupied = new AgentIndex();
    private AgentIndex divisible = new AgentIndex();
    private Coordinate lastCoord;
    private Agent lastPrevious;
    private Agent lastCurrent;

    public void setOccupied(Coordinate k, Boolean v) {
        if (v)
            occupied.add(k);
        else
            occupied.remove(k);
    }

    public void setDivisible(Coordinate k, Boolean v) {
        if (v)
            divisible.add(k);
        else
            divisible.remove(k);
    }

    public boolean isOccupied(Coordinate k) {
        return occupied.contains(k);
    }

    public boolean isDivisible(Coordinate k) {
        return divisible.contains(k);
    }

    public AgentIndex getOccupiedSites() {
        return occupied;
    }

    public void setOccupiedSites(AgentIndex occupied) {
        this.occupied = occupied;
    }

    public AgentIndex getDivisibleSites() {
        return divisible;
    }

    public void setDivisibleSites(AgentIndex divisible) {
        this.divisible = divisible;
    }

    @Override
    public void refresh(Coordinate coord, Agent previous, Agent current) {
        super.refresh(coord, previous, current);
        lastPrevious = previous;
        lastCurrent = current;
        lastCoord = coord;
    }

    public Coordinate getLastCoord() {
        return lastCoord;
    }

    public Agent getLastPrevious() {
        return lastPrevious;
    }

    public Agent getLastCurrent() {
        return lastCurrent;
    }
}
