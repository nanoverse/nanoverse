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

import control.arguments.*;
import control.halt.ExtinctionEvent;
import control.halt.HaltCondition;
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
public class CheckForExtinction extends CellProcess {

    private double threshold;
    private Argument<Double> thresholdArg;

    @FactoryTarget
    public CheckForExtinction(BaseProcessArguments arguments, CellProcessArguments cpArguments, Argument<Double> thresholdArg) {
        super(arguments, cpArguments);
        this.thresholdArg = thresholdArg;
    }

    @Override
    public void init() {
        try {
            threshold = thresholdArg.next();
        } catch (HaltCondition ex) {
            throw new IllegalStateException(ex);
        }
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
        // Handle true extinction exactly
        boolean thresholdIsZero = getGeneralParameters().epsilonEquals(threshold, 0.0);
        boolean noOccupiedSites = getLayer().getViewer().getOccupiedSites().size() == 0;
        if (thresholdIsZero && noOccupiedSites) {
            throw new ExtinctionEvent();
        }

        double totalSites = getLayer().getGeometry().getCanonicalSites().length * 1.0;
        double sitesOccupied = getLayer().getViewer().getOccupiedSites().size() * 1.0;

        double occupancy = sitesOccupied / totalSites;

        if (occupancy < threshold) {
            throw new ExtinctionEvent();
        }
    }
}
