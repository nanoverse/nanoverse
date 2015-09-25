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
 * Causes the callback cell to swap locations with a neighbor.
 * <p>
 * Created by dbborens on 5/26/14.
 */
public class Swap extends Action {
    private TargetRule targetRule;
    private IntegerArgument selfChannel;
    private IntegerArgument targetChannel;

    public Swap(BehaviorCell callback, LayerManager layerManager,
                TargetRule targetRule, IntegerArgument selfChannel,
                IntegerArgument targetChannel) {
        super(callback, layerManager);
        this.targetRule = targetRule;
        this.selfChannel = selfChannel;
        this.targetChannel = targetChannel;
    }

    @Override
    public void run(Coordinate caller) throws HaltCondition {
        BehaviorCell callerCell = resolveCaller(caller);
        Coordinate self = getOwnLocation();
        List<Coordinate> targets = targetRule.report(callerCell);

        if (targets.size() != 1) {
            throw new IllegalStateException("Swap action requires exactly one " +
                    "target per event.");
        }

        Coordinate target = targets.get(0);

        getLayerManager().getCellLayer().getUpdateManager().swap(self, target);

        doHighlight(selfChannel, self);
        doHighlight(targetChannel, target);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Swap)) return false;

        Swap swap = (Swap) o;

        if (!targetRule.equals(swap.targetRule)) return false;

        return true;
    }

    @Override
    public Action clone(BehaviorCell child) {
        TargetRule clonedTargetRule = targetRule.clone(child);
        return new Swap(child, getLayerManager(), clonedTargetRule, selfChannel, targetChannel);
    }
}
