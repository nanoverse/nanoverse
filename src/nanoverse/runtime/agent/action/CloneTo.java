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
import nanoverse.runtime.cells.Cell;
import nanoverse.runtime.control.arguments.*;
import nanoverse.runtime.control.halt.HaltCondition;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.layers.LayerManager;
import nanoverse.runtime.layers.cell.CellLayerViewer;
import nanoverse.runtime.layers.cell.CellUpdateManager;

import java.util.List;
import java.util.Random;

/**
 * Places a copy or copies of the current cell at the target site(s).
 * This uses the "replicate" method, meaning that the state of the cell is
 * exactly preserved.
 * <p>
 * Created by dbborens on 5/2/14.
 */
public class CloneTo extends Action {

    private TargetRule targetRule;

    // Replace occupied sites?
    private boolean noReplace;

    // Highlight channels for the targeting and targeted nanoverse.runtime.cells
    private IntegerArgument selfChannel;
    private IntegerArgument targetChannel;

    private Random random;

    public CloneTo(BehaviorCell behaviorCell, LayerManager layerManager,
                   TargetRule targetRule, boolean noReplace,
                   IntegerArgument selfChannel,
                   IntegerArgument targetChannel, Random random) {

        super(behaviorCell, layerManager);
        this.targetRule = targetRule;
        this.selfChannel = selfChannel;
        this.targetChannel = targetChannel;
        this.noReplace = noReplace;
        this.random = random;
    }

    @Override
    public void run(Coordinate caller) throws HaltCondition {
        BehaviorCell callerCell = resolveCaller(caller);

        Coordinate self = getOwnLocation();

        List<Coordinate> targets = targetRule.report(callerCell);

        CellUpdateManager u = getLayerManager().getCellLayer().getUpdateManager();
        CellLayerViewer v = getLayerManager().getCellLayer().getViewer();

        for (Coordinate target : targets) {

            // Make replicate
            Cell child = getCallback().replicate();

            // Place replicate at target site
            if (!v.isOccupied(target)) {
                u.place(child, target);
            } else if (noReplace) {
                throw new IllegalStateException("In CloneTo: Attempted to " +
                        "place child at occupied position (leading to " +
                        "replacement), but <no-replacment /> flag is set.");
            } else {
                u.banish(target);
                u.place(child, target);
            }
            // Highlight sites
            highlight(target, self);
        }

    }

    private void highlight(Coordinate target, Coordinate ownLocation) throws HaltCondition {
        doHighlight(targetChannel, target);
        doHighlight(selfChannel, ownLocation);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CloneTo cloneTo = (CloneTo) o;

        if (targetRule != null ? !targetRule.equals(cloneTo.targetRule) : cloneTo.targetRule != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = targetRule != null ? targetRule.hashCode() : 0;
        result = 31 * result + (selfChannel != null ? selfChannel.hashCode() : 0);
        result = 31 * result + (targetChannel != null ? targetChannel.hashCode() : 0);
        return result;
    }

    @Override
    public Action clone(BehaviorCell child) {
        TargetRule clonedTargeter = targetRule.clone(child);
        return new CloneTo(child, getLayerManager(), clonedTargeter, noReplace,
                selfChannel, targetChannel, random);
    }
}
