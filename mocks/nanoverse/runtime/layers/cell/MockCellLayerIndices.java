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

public class MockCellLayerIndices extends CellLayerIndices {

    private CellIndex occupied = new CellIndex();
    private CellIndex divisible = new CellIndex();
    private Coordinate lastCoord;
    private Cell lastPrevious;
    private Cell lastCurrent;

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

    public CellIndex getOccupiedSites() {
        return occupied;
    }

    public void setOccupiedSites(CellIndex occupied) {
        this.occupied = occupied;
    }

    public CellIndex getDivisibleSites() {
        return divisible;
    }

    public void setDivisibleSites(CellIndex divisible) {
        this.divisible = divisible;
    }

    @Override
    public void refresh(Coordinate coord, Cell previous, Cell current) {
        super.refresh(coord, previous, current);
        lastPrevious = previous;
        lastCurrent = current;
        lastCoord = coord;
    }

    public Coordinate getLastCoord() {
        return lastCoord;
    }

    public Cell getLastPrevious() {
        return lastPrevious;
    }

    public Cell getLastCurrent() {
        return lastCurrent;
    }
}
