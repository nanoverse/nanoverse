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

import nanoverse.runtime.agent.targets.TargetRule;
import nanoverse.runtime.cells.BehaviorCell;
import nanoverse.runtime.control.arguments.*;
import nanoverse.runtime.control.halt.HaltCondition;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.layers.LayerManager;

import java.util.List;

/**
 * Created by dbborens on 2/11/14.
 */
public class Trigger extends Action {

    private String behaviorName;
    private TargetRule targetRule;

    // Highlight channels for the targeting and targeted nanoverse.runtime.cells
    private IntegerArgument selfChannel;
    private IntegerArgument targetChannel;

    /**
     * Trigger a predesignated behavior in a cell or set of nanoverse.runtime.cells designated by a
     * targeting rule.
     *
     * @param callback     The cell associated with this behavior
     * @param layerManager
     * @param behaviorName The name of the behavior to be triggered in the targets.
     * @param targetRule   The targeting rule used to identify targets when called.
     */
    public Trigger(BehaviorCell callback, LayerManager layerManager, String behaviorName, TargetRule targetRule, IntegerArgument selfChannel, IntegerArgument targetChannel) {
        super(callback, layerManager);
        this.behaviorName = behaviorName;
        this.targetRule = targetRule;
        this.selfChannel = selfChannel;
        this.targetChannel = targetChannel;
    }

    @Override
    public void run(Coordinate caller) throws HaltCondition {
        BehaviorCell callerCell = resolveCaller(caller);

        // Since the Trigger behavior is the cause of the triggered behaviors,
        // the caller for the triggered behaviors is this cell.
        Coordinate self = getOwnLocation();

        // If this cell is no longer on the lattice, then it can no longer act,
        // so skip the action.
        if (self == null) {
            return;
        }

        List<Coordinate> targets = targetRule.report(callerCell);

        for (Coordinate target : targets) {
            // We require an occupied cell for the target of trigger actions.
            BehaviorCell targetCell = getWithCast(target);
            if (targetCell == null) {
                continue;
            }
            targetCell.trigger(behaviorName, self);
            highlight(target, self);
        }
    }

    private void highlight(Coordinate target, Coordinate ownLocation) throws HaltCondition {
        doHighlight(targetChannel, target);
        doHighlight(selfChannel, ownLocation);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Trigger)) {
            return false;
        }

        Trigger other = (Trigger) obj;

        if (!other.behaviorName.equalsIgnoreCase(this.behaviorName)) {
            return false;
        }

        if (!other.targetRule.equals(this.targetRule)) {
            return false;
        }

        return true;
    }

    @Override
    public Action clone(BehaviorCell child) {
        TargetRule clonedTargeter = targetRule.clone(child);
        Trigger cloned = new Trigger(child, getLayerManager(), behaviorName, clonedTargeter, selfChannel, targetChannel);
        return cloned;
    }
}
