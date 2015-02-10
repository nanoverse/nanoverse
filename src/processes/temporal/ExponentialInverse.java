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

package processes.temporal;

import control.halt.HaltCondition;
import processes.BaseProcessArguments;
import processes.StepState;

/**
 * Simple stochastic process for time scaling. Assumes all cells
 * are growing at the same rate: 1 cell per arbitrary unit. So
 * choose an exponentially distributed random number whose lambda
 * is inversely proportional to the number of cells. This is
 * equivalent to a Gillespie process in the case where cell division
 * is the only process and where all cells have an equal
 * probability of dividing.
 *
 * @author dbborens
 */
public class ExponentialInverse extends TimeProcess {

    public ExponentialInverse(BaseProcessArguments arguments) {
        super(arguments);
    }

    @Override
    public void init() {
    }

    @Override
    public void fire(StepState state) throws HaltCondition {
        double lambda = 1.0D / getLayerManager().getCellLayer().getViewer().getOccupiedSites().size();
        double dt = expRandom(lambda);
        state.advanceClock(dt);
    }

}
