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
package nanoverse.runtime.processes;

import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.control.arguments.IntegerArgument;
import nanoverse.runtime.control.halt.HaltCondition;
import nanoverse.runtime.layers.LayerManager;
import nanoverse.runtime.processes.gillespie.GillespieState;

public abstract class NanoverseProcess {

    private BaseProcessArguments arguments;


    public NanoverseProcess(BaseProcessArguments arguments) {
        this.arguments = arguments;
    }

    public int getID() {
        return arguments.getId();
    }

    public void iterate() throws HaltCondition {
        target();
        StepState stepState = arguments.getLayerManager().getStepState();
        fire(stepState);
    }

    /**
     * Convenience interface for target(...) -- calls it with a null GillespieState
     * argument.
     *
     * @throws HaltCondition
     */
    public void target() throws HaltCondition {
        target(null);
    }

    /**
     * Identifies possible update targets in the event of an iteration. Should
     * accept a null GillespieState for non-Gillespie events.
     *
     * @throws HaltCondition
     */
    public abstract void target(GillespieState gs) throws HaltCondition;

    /**
     * Chooses one of its available targets and executes the update.
     *
     * @param state
     * @throws HaltCondition
     */
    public abstract void fire(StepState state) throws HaltCondition;

    public IntegerArgument getPeriod() {
        return arguments.getPeriod();
    }

    public IntegerArgument getStart() {
        return arguments.getStart();
    }

    protected LayerManager getLayerManager() {
        return arguments.getLayerManager();
    }

    protected GeneralParameters getGeneralParameters() {
        return arguments.getGeneralParameters();
    }

    public abstract void init();
}
