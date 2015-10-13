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

package nanoverse.runtime.processes.discrete;

import nanoverse.runtime.control.halt.*;
import nanoverse.runtime.processes.*;
import nanoverse.runtime.processes.gillespie.GillespieState;
import nanoverse.runtime.structural.annotations.FactoryTarget;

/**
 * Created by dbborens on 7/31/14.
 */
public class ManualHalt extends AgentProcess {
    private String message;

    @FactoryTarget
    public ManualHalt(BaseProcessArguments arguments, AgentProcessArguments cpArguments, String message) {
        super(arguments, cpArguments);
        this.message = message;
    }

    @Override
    public void target(GillespieState gs) throws HaltCondition {
    }

    @Override
    public void fire(StepState state) throws HaltCondition {
        throw new ManualHaltEvent(message);
    }

    @Override
    public void init() {
    }
}
