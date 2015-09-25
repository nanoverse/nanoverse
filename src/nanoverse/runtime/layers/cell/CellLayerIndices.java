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

import nanoverse.runtime.cells.Cell;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.structural.*;

import java.util.*;

/**
 * Direct representation of cell layer indices. Only the cell layer and
 * its content object should have direct access to this object. Processes
 * should use a CellLayerViewer.
 *
 * @author David Bruce Borenstein
 * @test CellLayerIndicesTest
 */
public class CellLayerIndices {

    // Coordinate sets. We often need to know all the sites that
    // have a particular property so we can iterate over them,
    // so we track them as sets.

    // Occupied sites (non-vacant sites)
    protected CellIndex occupiedSites;

    // Divisible sites
    protected CellIndex divisibleSites;

    // Map that tracks count of nanoverse.runtime.cells with each state
    protected NonNullIntegerMap stateMap;

//    public CellLocationIndex getCellLocationIndex() {
//        return cellLocationIndex;
//    }

    protected CellLocationIndex cellLocationIndex;

    //protected CellLayerContent callback;

    // IdentityHashMap resolves the actual memory address of the
    // key, ie, using == instead of equals(...). This way, no matter
    // how equality is defined for nanoverse.runtime.cells, the cell-->coordinate map
    // will uniquely map nanoverse.runtime.cells to a location.
//    protected IdentityHashMap<Cell, Coordinate> cellToCoord;

    public CellLayerIndices() {
        occupiedSites = new CellIndex();
        divisibleSites = new CellIndex();
        stateMap = new NonNullIntegerMap();
        cellLocationIndex = new CellLocationIndex();
    }

    public Coordinate locate(Cell cell) {
        return cellLocationIndex.locate(cell);
    }

    public boolean isIndexed(Cell cell) {
        return cellLocationIndex.isIndexed(cell);
    }

    public boolean isOccupied(Coordinate cell) {
        Coordinate c = cell.canonicalize();
        if (occupiedSites.contains(c)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isDivisible(Coordinate cell) {
        Coordinate c = cell.canonicalize();
        if (divisibleSites.contains(c)) {
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

    /**
     * Returns a list of divisible sites on the lattice.
     *
     * @return
     */
    public Set<Coordinate> getDivisibleSites() {
        return divisibleSites.set();
    }

    public void refresh(Coordinate coord, Cell previous, Cell current) {
        if (previous != null) {
            remove(coord, previous);
        }

        if (current != null) {
            add(coord, current);
        }
    }

    private void remove(Coordinate coord, Cell cell) {
        cellLocationIndex.remove(cell);
        decrStateCount(cell);
        setOccupied(coord, false);
        setDivisible(coord, false);
    }

    private void setDivisible(Coordinate coord, boolean isDivisible) {
        if (isDivisible) {
            divisibleSites.add(coord);
        } else {
            divisibleSites.remove(coord);
        }

    }

    private void setOccupied(Coordinate coord, boolean isOccupied) {
        if (isOccupied) {
            occupiedSites.add(coord);
        } else {
            occupiedSites.remove(coord);
        }
    }

    private void decrStateCount(Cell cell) {
        Integer currentState = cell.getState();
        Integer currentCount = stateMap.get(currentState);
        if (currentCount == 1) {
            stateMap.remove(currentState);
        } else {
            stateMap.put(currentState, currentCount - 1);
        }
    }

    private void add(Coordinate coord, Cell cell) {
        cellLocationIndex.add(cell, coord);
        incrStateCount(cell);
        setOccupied(coord, true);
        setDivisible(coord, cell.isDivisible());
    }

    private void incrStateCount(Cell cell) {
        Integer currentState = cell.getState();

        if (!stateMap.containsKey(currentState)) {
            stateMap.put(currentState, 0);
        }

        Integer currentCount = stateMap.get(currentState);
        stateMap.put(currentState, currentCount + 1);
    }

    public CellLayerIndices clone(CanonicalCellMap cellMap) {
        CellIndex clonedOccupied = new CellIndex(occupiedSites);
        CellIndex clonedDivisible = new CellIndex(divisibleSites);
        NonNullIntegerMap clonedStateMap = new NonNullIntegerMap(stateMap);
        CellLayerIndices clone = new CellLayerIndices();
        CellLocationIndex clonedLocIndex = buildLocationIndex(cellMap);
        clone.cellLocationIndex = clonedLocIndex;
        clone.occupiedSites = clonedOccupied;
        clone.divisibleSites = clonedDivisible;
        clone.stateMap = clonedStateMap;
        return clone;
    }

    private CellLocationIndex buildLocationIndex(CanonicalCellMap cellMap) {
        CellLocationIndex ret = new CellLocationIndex();
        for (Coordinate key : cellMap.keySet()) {
            Cell value = cellMap.get(key);
            if (value != null) {
                ret.add(value, key);
            }
        }

        return ret;
    }

    public NonNullIntegerMap getStateMap() {
        return stateMap;
    }

    @Override
    public int hashCode() {
        int result = occupiedSites != null ? occupiedSites.hashCode() : 0;
        result = 31 * result + (divisibleSites != null ? divisibleSites.hashCode() : 0);
        result = 31 * result + (stateMap != null ? stateMap.hashCode() : 0);
        result = 31 * result + (cellLocationIndex != null ? cellLocationIndex.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CellLayerIndices indices = (CellLayerIndices) o;

        if (divisibleSites != null ? !divisibleSites.equals(indices.divisibleSites) : indices.divisibleSites != null)
            return false;
        if (occupiedSites != null ? !occupiedSites.equals(indices.occupiedSites) : indices.occupiedSites != null)
            return false;
        if (stateMap != null ? !stateMap.equals(indices.stateMap) : indices.stateMap != null)
            return false;

        // We don't want true equality of the cell location index, because we
        // make a new copy of each cell as part of cloning the CellLayer.
        if (!valuesEqual(cellLocationIndex, indices.cellLocationIndex)) {
            return false;
        }

        return true;
    }

    /**
     * CellLocationIndex is backed by the memory addresses of the keys, which we
     * don't want to compare. So we compare the values only.
     *
     * @param p
     * @param q
     * @return
     */
    private boolean valuesEqual(CellLocationIndex p, CellLocationIndex q) {
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
