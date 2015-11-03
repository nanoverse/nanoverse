/*
 * Nanoverse: a declarative agent-based modeling language for natural and
 * social science.
 *
 * Copyright (c) 2015 David Bruce Borenstein and Nanoverse, LLC.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package nanoverse.runtime.processes.discrete.check;

import nanoverse.runtime.control.halt.*;
import nanoverse.runtime.layers.cell.NameMapViewer;
import nanoverse.runtime.processes.*;
import nanoverse.runtime.processes.discrete.*;
import nanoverse.runtime.processes.gillespie.GillespieState;
import nanoverse.runtime.structural.annotations.FactoryTarget;

/**
 * Checks for extinction or fixation events.
 * <p>
 * Created by dbborens on 1/13/14.
 */
public class CheckForFixation extends AgentProcess {

    @FactoryTarget
    public CheckForFixation(BaseProcessArguments arguments, AgentProcessArguments cpArguments) {
        super(arguments, cpArguments);
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
        NameMapViewer smv = getLayer().getViewer().getNameMapViewer();

        for (String s : smv.getNames()) {
            if (smv.getCount(s) == getLayer().getViewer().getOccupiedSites().count()) {
                throw new FixationEvent(s);
            }
        }
    }

    @Override
    public void init() {
    }
}
