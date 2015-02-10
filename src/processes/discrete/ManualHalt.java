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
import control.halt.ManualHaltEvent;
import processes.BaseProcessArguments;
import processes.StepState;
import processes.gillespie.GillespieState;

/**
 * Created by dbborens on 7/31/14.
 */
public class ManualHalt extends CellProcess {
    private String message;

    public ManualHalt(BaseProcessArguments arguments, CellProcessArguments cpArguments, String message) {
        super(arguments, cpArguments);
        this.message = message;
    }

    @Override
    public void init() {
    }

    @Override
    public void target(GillespieState gs) throws HaltCondition {
    }

    @Override
    public void fire(StepState state) throws HaltCondition {
        throw new ManualHaltEvent(message);
    }
}
