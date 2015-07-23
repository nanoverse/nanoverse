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
package processes;

import control.GeneralParameters;
import control.arguments.*;
import control.halt.HaltCondition;
import layers.LayerManager;
import processes.gillespie.GillespieState;

public abstract class NanoverseProcess {

    private BaseProcessArguments arguments;
//    protected LayerManager layerManager;
//    protected GeneralParameters p;
//    private int id;
//    private IntegerArgument period;
//    private IntegerArgument start;


    public NanoverseProcess(BaseProcessArguments arguments) {
        this.arguments = arguments;
//        p = arguments.getGeneralParameters();
//        id = arguments.getId();
//        start = arguments.getStart();
//        period = arguments.getPeriod();
//        this.layerManager = arguments.getLayerManager();
    }

    public int getID() {
        return arguments.getId();
    }


    /**
     * Identifies possible update targets in the event of an iteration. Should
     * accept a null GillespieState for non-Gillespie events.
     *
     * @throws HaltCondition
     */
    public abstract void target(GillespieState gs) throws HaltCondition;

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
     * Chooses one of its available targets and executes the update.
     *
     * @param state
     * @throws HaltCondition
     */
    public abstract void fire(StepState state) throws HaltCondition;

    public void iterate() throws HaltCondition {
        target();
        StepState stepState = arguments.getLayerManager().getStepState();
        fire(stepState);
    }

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
