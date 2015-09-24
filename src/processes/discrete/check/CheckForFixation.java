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

package processes.discrete.check;

import control.halt.FixationEvent;
import control.halt.HaltCondition;
import layers.cell.StateMapViewer;
import processes.BaseProcessArguments;
import processes.StepState;
import processes.discrete.CellProcess;
import processes.discrete.CellProcessArguments;
import processes.gillespie.GillespieState;
import structural.annotations.FactoryTarget;

/**
 * Checks for extinction or fixation events.
 * <p>
 * Created by dbborens on 1/13/14.
 */
public class CheckForFixation extends CellProcess {

    @FactoryTarget
    public CheckForFixation(BaseProcessArguments arguments, CellProcessArguments cpArguments) {
        super(arguments, cpArguments);
    }

    @Override
    public void init() {
    }

    @Override
    public void target(GillespieState gs) throws HaltCondition {
        // There's only one event that can happen in this process.
        if (gs != null) {
            gs.add(this.getID(), 1, 0.0D);
        }
    }

    @Override
    public void fire(StepState state) throws HaltCondition {
//        System.out.println("Executing check for fixation.");
        StateMapViewer smv = getLayer().getViewer().getStateMapViewer();

        for (Integer s : smv.getStates()) {
            if (smv.getCount(s) == getLayer().getViewer().getOccupiedSites().size()) {
                throw new FixationEvent(s);
            }
        }
    }
}
