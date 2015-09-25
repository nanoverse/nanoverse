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

package nanoverse.runtime.agent.action;

import nanoverse.runtime.cells.BehaviorCell;
import nanoverse.runtime.cells.Cell;
import nanoverse.runtime.control.arguments.*;
import nanoverse.runtime.control.halt.HaltCondition;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.layers.LayerManager;
import nanoverse.runtime.layers.cell.CellLayerViewer;
import nanoverse.runtime.layers.cell.CellLookupManager;
import nanoverse.runtime.processes.StepState;

/**
 * Actions are the consituent members of Behaviors. They
 * are strung together as an ordered list, called an
 * "action sequence." You can think of actions as anonymous,
 * predefined Behaviors.
 * <p>
 * Created by David B Borenstein on 1/22/14.
 */
public abstract class Action {

    private final BehaviorCell callback;
    private final LayerManager layerManager;

    public Action(BehaviorCell callback, LayerManager layerManager) {
        this.callback = callback;
        this.layerManager = layerManager;
    }

    protected LayerManager getLayerManager() {
        return layerManager;
    }

    public BehaviorCell getCallback() {
        return callback;
    }

    public abstract void run(Coordinate caller) throws HaltCondition;

    /**
     * Returns the location of the cell whose behavior this is.
     *
     * @return
     */
    protected Coordinate getOwnLocation() {
        CellLookupManager lookup = getLayerManager().getCellLayer().getLookupManager();
        BehaviorCell self = getCallback();

        // If the acting cell has been removed from the lattice, return no coordinate.
        // It's up to the particular Action to decide what happens at that point.
        if (!getLayerManager().getCellLayer().getViewer().exists(self)) {
            return null;
        }

        Coordinate location = lookup.getCellLocation(self);
        return location;
    }

    protected BehaviorCell resolveCaller(Coordinate caller) {
        // The caller is null, indicating that the call came from
        // a top-down process. Return null.
        if (caller == null) {
            return null;
        }

        // Blow up unless target coordinate contains a behavior cell.
        // In that case, return that cell.
        BehaviorCell callerCell = getWithCast(caller);

        return callerCell;
    }

    protected BehaviorCell getWithCast(Coordinate coord) {
        CellLayerViewer viewer = getLayerManager().getCellLayer().getViewer();

        if (!viewer.isOccupied(coord)) {
            return null;
//            throw new IllegalStateException("Expected, but did not find, an occupied site at " + coord
//                    + ".");
        }

        Cell putative = viewer.getCell(coord);

        if (!(putative instanceof BehaviorCell)) {
            throw new UnsupportedOperationException("Only BehaviorCells and top-down nanoverse.runtime.processes may trigger behaviors.");
        }

        BehaviorCell result = (BehaviorCell) putative;

        return result;
    }

    /**
     * Actions should be considered equal if they perform
     * the same function, but should NOT be concerned with
     * the identity of the callback.
     *
     * @param obj
     * @return
     */
    public abstract boolean equals(Object obj);

    public abstract Action clone(BehaviorCell child);

    protected void doHighlight(IntegerArgument channelArg, Coordinate toHighlight) throws HaltCondition {
        // If not using highlights, do nothing
        if (channelArg == null) {
            return;
        }

        if (!layerManager.getCellLayer().getGeometry().isInBounds(toHighlight)) {
            return;
        }

        Integer channel = channelArg.next();
        StepState stepState = getLayerManager().getStepState();
        stepState.highlight(toHighlight, channel);
    }

    protected boolean callbackExists() {
        return getLayerManager()
                .getCellLayer()
                .getViewer()
                .exists(getCallback());
    }
}
