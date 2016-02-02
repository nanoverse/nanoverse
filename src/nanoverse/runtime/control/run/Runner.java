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

package nanoverse.runtime.control.run;

import nanoverse.runtime.control.*;
import nanoverse.runtime.structural.annotations.FactoryTarget;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by dbborens on 11/26/14.
 */
public class Runner implements Runnable {

    private GeneralParameters p;
    private Integrator integrator;
    private Thread UI;
    private AtomicBoolean isRunningFlag;

    @FactoryTarget(displayName = "Project")
    public Runner(GeneralParameters p, Integrator integrator) {
        this.p = p;
        this.integrator = integrator;
    }

    public void run() {
        integrator.setIsRunningFlag(isRunningFlag);

        int n = p.getNumInstances();
        for (int i = 0; i < n; i++) {
            integrator.doNext();

            // This instructs the parameter handler to re-initialize the random
            // number generator and to update paths to reflect the next
            // iterate. It is only invoked if there are remaining iterates.
            if (i < p.getNumInstances() - 1) {
                p.advance();
            }
        }
    }

    public void setUI(Thread UI) {
        this.UI = UI;
    }

    public void setIsRunningFlag(AtomicBoolean isRunningFlag) {
        this.isRunningFlag = isRunningFlag;
    }
}
