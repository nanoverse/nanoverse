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

package cells;

import control.halt.HaltCondition;
import control.identifiers.Coordinate;
import layers.LayerManager;
import layers.cell.CellLayer;
import layers.cell.CellLookupManager;

/**
 * A helper class for cells that triggers
 * update events related to the life cycle
 * of the cell, such as death or divisibility.
 * <p>
 * Created by dbborens on 2/21/14.
 */
public class CallbackManager {

    private Cell cell;
    private LayerManager layerManager;

    public CallbackManager(Cell cell, LayerManager layerManager) {
        this.cell = cell;
        this.layerManager = layerManager;
    }

    /**
     * Signals to the LayerManager that the callback cell is dead
     * and should be removed from the simulation.
     */
    public void die() {
        CellLayer layer = layerManager.getCellLayer();
        Coordinate coord = layer.getLookupManager().getCellLocation(cell);
        layer.getUpdateManager().banish(coord);
    }

    /**
     * Signals to the LayerManager that the callback cell may have
     * changed its divisibility status and should be checked.
     */
    public void refreshDivisibility() throws HaltCondition {
        CellLayer layer = layerManager.getCellLayer();

        if (layer.getViewer().exists(cell)) {
            Coordinate coord = layer.getLookupManager().getCellLocation(cell);
            layer.getUpdateManager().banish(coord);
            layer.getUpdateManager().place(cell, coord);
        }
    }

    public LayerManager getLayerManager() {
        return layerManager;
    }

    public Coordinate getMyLocation() {
        CellLayer layer = layerManager.getCellLayer();
        CellLookupManager lookupManager = layer.getLookupManager();
        Coordinate coord = lookupManager.getCellLocation(cell);
        return coord;
    }
}
