/*
 * Copyright (c) 2014, 2015 David Bruce Borenstein and the
 * Trustees of Princeton University.
 *
 * This file is part of the Nanoverse simulation framework
 * (patent pending).
 *
 * This program is free software: you can redistribute it
 * and/or modify it under the terms of the GNU Affero General
 * Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * This program is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 * PURPOSE.  See the GNU Affero General Public License for
 * more details.
 *
 * You should have received a copy of the GNU Affero General
 * Public License along with this program.  If not, see
 * <http://www.gnu.org/licenses/>.
 */

package nanoverse.runtime.layers.cell;

import nanoverse.runtime.agent.Agent;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.structural.*;

import java.util.*;

/**
 * Direct representation of cell layer indices. Only the cell layer and
 * its content object should have direct access to this object. Processes
 * should use a AgentLayerViewer.
 *
 * @author David Bruce Borenstein
 * @test AgentLayerIndicesTest
 */
public class AgentLayerIndices {

    // Coordinate sets. We often need to know all the sites that
    // have a particular property so we can iterate over them,
    // so we track them as sets.

    // Occupied sites (non-vacant sites)
    protected AgentIndex occupiedSites;

    // Map that tracks count of agents with each state
    protected NonNullStringMap nameMap;

    protected AgentLocationIndex cellLocationIndex;

    public AgentLayerIndices() {
        occupiedSites = new AgentIndex();
        nameMap = new NonNullStringMap();
        cellLocationIndex = new AgentLocationIndex();
    }

    public Coordinate locate(Agent agent) {
        return cellLocationIndex.locate(agent);
    }

    public boolean isIndexed(Agent agent) {
        return cellLocationIndex.isIndexed(agent);
    }

    public boolean isOccupied(Coordinate cell) {
        Coordinate c = cell.canonicalize();
        if (occupiedSites.contains(c)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns a vector containing the canonical coordinate of each
     * active site on the lattice.
     *
     * @return
     */
    public Set<Coordinate> getOccupiedSites() {
        return occupiedSites.set();
    }

    public void refresh(Coordinate coord, Agent previous, Agent current) {
        if (previous != null) {
            remove(coord, previous);
        }

        if (current != null) {
            add(coord, current);
        }
    }

    private void remove(Coordinate coord, Agent agent) {
        cellLocationIndex.remove(agent);
        decrStateCount(agent);
        setOccupied(coord, false);
    }

    private void setOccupied(Coordinate coord, boolean isOccupied) {
        if (isOccupied) {
            occupiedSites.add(coord);
        } else {
            occupiedSites.remove(coord);
        }
    }

    private void decrStateCount(Agent agent) {
        String name = agent.getName();
        Integer currentCount = nameMap.get(name);
        if (currentCount == 1) {
            nameMap.remove(name);
        } else {
            nameMap.put(name, currentCount - 1);
        }
    }

    private void add(Coordinate coord, Agent agent) {
        cellLocationIndex.add(agent, coord);
        incrStateCount(agent);
        setOccupied(coord, true);
    }

    private void incrStateCount(Agent agent) {
        String name = agent.getName();

        if (!nameMap.containsKey(name)) {
            nameMap.put(name, 0);
        }

        Integer currentCount = nameMap.get(name);
        nameMap.put(name, currentCount + 1);
    }

    public AgentLayerIndices clone(CanonicalAgentMap cellMap) {
        AgentIndex clonedOccupied = new AgentIndex(occupiedSites);
        NonNullStringMap clonedStateMap = new NonNullStringMap(nameMap);
        AgentLayerIndices clone = new AgentLayerIndices();
        AgentLocationIndex clonedLocIndex = buildLocationIndex(cellMap);
        clone.cellLocationIndex = clonedLocIndex;
        clone.occupiedSites = clonedOccupied;
        clone.nameMap = clonedStateMap;
        return clone;
    }

    private AgentLocationIndex buildLocationIndex(CanonicalAgentMap cellMap) {
        AgentLocationIndex ret = new AgentLocationIndex();
        for (Coordinate key : cellMap.keySet()) {
            Agent value = cellMap.get(key);
            if (value != null) {
                ret.add(value, key);
            }
        }

        return ret;
    }

    public NonNullStringMap getNameMap() {
        return nameMap;
    }

    @Override
    public int hashCode() {
        int result = occupiedSites != null ? occupiedSites.hashCode() : 0;
        result = 31 * result + (nameMap != null ? nameMap.hashCode() : 0);
        result = 31 * result + (cellLocationIndex != null ? cellLocationIndex.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AgentLayerIndices indices = (AgentLayerIndices) o;
        if (occupiedSites != null ? !occupiedSites.equals(indices.occupiedSites) : indices.occupiedSites != null)
            return false;
        if (nameMap != null ? !nameMap.equals(indices.nameMap) : indices.nameMap != null)
            return false;

        // We don't want true equality of the cell location index, because we
        // make a new copy of each cell as part of cloning the AgentLayer.
        if (!valuesEqual(cellLocationIndex, indices.cellLocationIndex)) {
            return false;
        }

        return true;
    }

    /**
     * AgentLocationIndex is backed by the memory addresses of the keys, which we
     * don't want to compare. So we compare the values only.
     *
     * @param p
     * @param q
     * @return
     */
    private boolean valuesEqual(AgentLocationIndex p, AgentLocationIndex q) {
        Set<Coordinate> pCoords = new HashSet<>(p.values());
        Set<Coordinate> qCoords = new HashSet<>(q.values());

        if (pCoords.size() != qCoords.size()) {
            return false;
        }

        for (Coordinate c : pCoords) {
            if (!qCoords.contains(c)) {
                return false;
            }
        }

        return true;
    }
}
