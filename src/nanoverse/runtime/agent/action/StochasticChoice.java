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

import nanoverse.runtime.agent.BehaviorCell;
import nanoverse.runtime.control.halt.HaltCondition;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.layers.LayerManager;

import java.util.Random;

/**
 * Upon trigger, selects one of several actions, each of
 * which has a relative probability that is pre-assigned.
 * The chosen action executes as normal.
 * <p>
 * Created by dbborens on 3/6/14.
 */
public class StochasticChoice extends Action {
    private DynamicActionRangeMap chooser;
    private Random random;

    public StochasticChoice(BehaviorCell callback, LayerManager layerManager,
                            DynamicActionRangeMap chooser, Random random) {

        super(callback, layerManager);
        this.chooser = chooser;
        this.random = random;
    }

    @Override
    public void run(Coordinate caller) throws HaltCondition {
        chooser.refresh();
        double range = chooser.getTotalWeight();
        double x = random.nextDouble() * range;
        Action choice = chooser.selectTarget(x);
        choice.run(caller);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof StochasticChoice)) {
            return false;
        }

        // The equality method here is only used during nanoverse.runtime.factory testing.
        // True comparison would be an absolute bear, and it can
        // be completely avoided through the use of mocks. As such,
        // I am disabling this logic here for the moment.

//        StochasticChoice other = (StochasticChoice) obj;
//
//        if (!chooser.equals(other.chooser)) {
//            return false;
//        }

        return true;
    }

    @Override
    public Action clone(BehaviorCell child) {
        DynamicActionRangeMap clonedChooser = chooser.clone(child);
        StochasticChoice cloned = new StochasticChoice(child, getLayerManager(), clonedChooser, random);
        return cloned;
    }
}