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
import nanoverse.runtime.control.halt.HaltCondition;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.layers.LayerManager;

/**
 * An action that calls other actions on run.
 *
 * @see nanoverse.runtime.agent.Action
 */
public class CompoundAction extends Action {

    protected final BehaviorCell callback;
    protected final LayerManager layerManager;

    // Each action in the actionSequence array is fired,
// in order, when the trigger(...) method is invoked.
    protected final Action[] actionSequence;

    public CompoundAction(BehaviorCell callback, LayerManager layerManager, Action[] actionSequence) {
        super(callback, layerManager);
        this.callback = callback;
        this.layerManager = layerManager;
        this.actionSequence = actionSequence;
    }

    protected LayerManager getLayerManager() {
        return layerManager;
    }

    public BehaviorCell getCallback() {
        return callback;
    }

    public void run(Coordinate caller) throws HaltCondition {
        for (Action action : actionSequence) {
            action.run(caller);
        }
    }

    /**
     * Behaviors are equal if and only if their action sequences
     * consist of an equivalent list of actions.
     *
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        // The object should be a behavior.
        if (!(obj instanceof CompoundAction)) {
            return false;
        }

        CompoundAction other = (CompoundAction) obj;

        // The object should have the same number of actions.
        if (other.getActionSequence().length != this.getActionSequence().length) {
            return false;
        }

        // Each action should be equivalent, and in the correct order.
        for (int i = 0; i < getActionSequence().length; i++) {
            Action p = this.getActionSequence()[i];
            Action q = other.getActionSequence()[i];
            if (!actionsEqual(p, q)) {
                return false;
            }
        }

        // The behaviors are equivalent.
        return true;
    }

    private boolean actionsEqual(Action p, Action q) {
        if (!p.equals(q)) {
            return false;
        }

        return true;
    }

    protected Action[] getActionSequence() {
        return actionSequence;
    }

    public CompoundAction clone(BehaviorCell child) {
        Action[] clonedActionSequence = cloneActionSequence(child);
        CompoundAction clone = new CompoundAction(child, layerManager, clonedActionSequence);
        return clone;
    }

    protected Action[] cloneActionSequence(BehaviorCell child) {
        int n = actionSequence.length;
        Action[] clonedActionSequence = new Action[n];
        for (int i = 0; i < n; i++) {
            Action action = actionSequence[i];
            Action clonedAction = action.clone(child);
            clonedActionSequence[i] = clonedAction;
        }

        return clonedActionSequence;
    }
}
