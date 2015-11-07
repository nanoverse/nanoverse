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

package nanoverse.runtime.processes.continuum;

import nanoverse.runtime.control.halt.HaltCondition;
import nanoverse.runtime.layers.continuum.ContinuumLayerScheduler;
import nanoverse.runtime.processes.*;
import nanoverse.runtime.structural.annotations.FactoryTarget;

/**
 * Created by dbborens on 1/22/15.
 */
public class Integrate extends ContinuumProcess {
    private ContinuumLayerScheduler scheduler;

    @FactoryTarget
    public Integrate(BaseProcessArguments arguments, ContinuumLayerScheduler scheduler) {
        super(arguments);
        this.scheduler = scheduler;
    }

    @Override
    public void fire(StepState state) throws HaltCondition {
        scheduler.solve();
    }

    @Override
    public void init() {
    }
}
