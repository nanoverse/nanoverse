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

package agent;

import agent.action.Action;
import agent.action.CompoundAction;
import cells.BehaviorCell;
import layers.LayerManager;

/**
 * A Behavior is an ordered sequence of Actions, associated
 * with a particular agent cell and invoked by name. Behaviors
 * can be triggered (invoked) either by the actions of other
 * cells or directly via a top-down process in your model.
 * <p>
 * Each cell has its own set of Behaviors, which can affect the
 * neighborhood of the cell as well as the cell itself. These
 * Behaviors can include Actions that trigger the Behaviors of
 * neighboring cells.
 * <p>
 * The defining feature of a Behavior is its ordered list of
 * Actions, called the "action sequence." When triggered (invoked),
 * the Actions in the action sequence are fired one at a time.
 * <p>
 * Notably, the Behavior actually extends from a class of Action.
 * This is because it can be thought of an Action that contains
 * other actions.
 * <p>
 * Created by David B Borenstein on 1/21/14.
 */
public class Behavior extends CompoundAction {

    public Behavior(BehaviorCell callback, LayerManager layerManager, Action[] actionSequence) {
        super(callback, layerManager, actionSequence);
    }

    @Override
    public boolean equals(Object obj) {
        // Behaviors are CompoundActions, but not vice versa
        if (!(obj instanceof Behavior)) {
            return false;
        }

        // Otherwise, the definition of equality for Behaviors
        // and CompoundActions are the same.
        return super.equals(obj);
    }

    @Override
    public Behavior clone(BehaviorCell child) {
        Action[] clonedActionSequence = cloneActionSequence(child);
        Behavior clone = new Behavior(child, layerManager, clonedActionSequence);
        return clone;
    }

}
