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

package processes.discrete;

import control.halt.HaltCondition;
import layers.cell.StateMapViewer;
import processes.BaseProcessArguments;
import processes.StepState;
import processes.gillespie.GillespieState;

/**
 * Reports information about the model state. Useful for debugging
 * broken eSLIME scripts.
 * Created by dbborens on 3/7/14.
 */
public class DiagnosticProcess extends CellProcess {
    public DiagnosticProcess(BaseProcessArguments arguments, CellProcessArguments cpArguments) {
        super(arguments, cpArguments);
    }

    @Override
    public void target(GillespieState gs) throws HaltCondition {
        // Could add some debug information about the Gillespie state...
        if (gs != null) {
            gs.add(this.getID(), 1, 0.0D);
        }
    }

    @Override
    public void init() {
    }

    @Override
    public void fire(StepState state) throws HaltCondition {
        System.out.println("Occupied sites:" + getLayer().getViewer().getOccupiedSites().size());
        System.out.println("Divisible sites:" + getLayer().getViewer().getDivisibleSites().size());

        System.out.println("Cells by type:");
        StateMapViewer smv = getLayer().getViewer().getStateMapViewer();
        for (Integer s : smv.getStates()) {
            System.out.println("   type " + s + ": " + smv.getCount(s));
        }
    }
}
