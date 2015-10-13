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

import nanoverse.runtime.agent.Cell;
import nanoverse.runtime.control.identifiers.Coordinate;

import java.util.IdentityHashMap;

/**
 * Class for associating nanoverse.runtime.cells to coordinates, with
 * nanoverse.runtime.cells identified by reference (==) rather than
 * object equality.
 * <p>
 * Created by David B Borenstein on 2/5/14.
 */
public class CellLocationIndex extends IdentityHashMap<Cell, Coordinate> {


    public CellLocationIndex() {
        super();
    }

    public void add(Cell cell, Coordinate coordinate) {
        if (containsKey(cell)) {
            throw new IllegalStateException("Attempting to overwrite existing location key.");
        }

        put(cell, coordinate);
    }

    public Coordinate locate(Cell cell) {
        if (!containsKey(cell)) {
            throw new IllegalStateException("Attempting to locate a cell that does not have an indexed spatial location.");
        }

        return get(cell);

    }

    @Override
    public Coordinate remove(Object key) {
        return super.remove(key);
    }

    public boolean isIndexed(Cell cell) {
        return containsKey(cell);
    }

}
