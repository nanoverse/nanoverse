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
import nanoverse.runtime.structural.utilities.EpsilonUtil;

/**
 * Adjusts the nanoverse.runtime.agent's health by a predefined delta.
 * <p>
 * Created by David B Borenstein on 2/5/14.
 */
public class AdjustHealth extends Action {

    private double delta;

    public AdjustHealth(BehaviorCell callback, LayerManager layerManager, double delta) {
        super(callback, layerManager);
        this.delta = delta;
    }

    @Override
    public void run(Coordinate caller) throws HaltCondition {
        BehaviorCell cell = getCallback();
        double curHealth = cell.getHealth();
        double adjHealth = curHealth + delta;
        cell.setHealth(adjHealth);

//        // DEBUG CODE
//        Coordinate self = getLayerManager().getCellLayer().getLookupManager().getCellLocation(cell);
//        System.out.println("Adjusted cell at " + self + " from " + curHealth + " to " + adjHealth);
//        System.out.println("   Is this cell now divisible? " + cell.isDivisible());
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof AdjustHealth)) {
            return false;
        }

        AdjustHealth other = (AdjustHealth) obj;
        if (!EpsilonUtil.epsilonEquals(other.delta, this.delta)) {
            return false;
        }

        return true;
    }

    @Override
    public Action clone(BehaviorCell child) {
        AdjustHealth clone = new AdjustHealth(child, getLayerManager(), delta);
        return clone;
    }
}
