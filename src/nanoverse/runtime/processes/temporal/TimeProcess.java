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

package nanoverse.runtime.processes.temporal;

import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.processes.BaseProcessArguments;
import nanoverse.runtime.processes.NanoverseProcess;
import nanoverse.runtime.processes.gillespie.GillespieState;

/**
 * Convenience superclass for time-specific nanoverse.runtime.processes,
 * including some methods for calculating elapsed time.
 * Probably not necessary to have a separate superclass
 * for this.
 * <p>
 * At the moment, TimeProcess is specific to a particular
 * cell layer.
 *
 * @author David Bruce Borenstein
 * @untested
 */
public abstract class TimeProcess extends NanoverseProcess {
    protected GeneralParameters p;

    public TimeProcess(BaseProcessArguments arguments) {

        super(arguments);
    }

    public void target(GillespieState gs) {
        // There's only one event that can happen--we update.
        if (gs != null) {
            gs.add(this.getID(), 1, 0.0D);
        }
    }

    /**
     * Returns an exponentially distributed random number.
     */
    protected double expRandom(double lambda) {
        // Get a random number between 0 (inc) and 1 (exc)
        double u = p.getRandom().nextDouble();

        // Inverse of exponential CDF
        return Math.log(1 - u) / (-1 * lambda);
    }
}
