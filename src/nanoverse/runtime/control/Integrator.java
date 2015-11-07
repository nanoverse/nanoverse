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

package nanoverse.runtime.control;

import nanoverse.runtime.control.halt.*;
import nanoverse.runtime.io.serialize.SerializationManager;
import nanoverse.runtime.processes.StepState;
import nanoverse.runtime.structural.annotations.FactoryTarget;

public class Integrator {

    private final ProcessManager processManager;
    protected double time = 0.0D;
    private GeneralParameters p;
    private SerializationManager serializationManager;

    @FactoryTarget
    public Integrator(GeneralParameters p, ProcessManager processManager,
                      SerializationManager serializationManager) {

        // Assign member variables.
        this.p = p;
        this.serializationManager = serializationManager;
        this.processManager = processManager;
    }

    public HaltCondition doNext() {
        processManager.init();
        serializationManager.init();
        HaltCondition ex = go();

        // This step includes any analysis or visualizations that take place
        // after the integrator is done running. It also closes any open handles
        // or managers instantiated by the serialization manager on behalf
        // of the instance.
        serializationManager.dispatchHalt(ex);

        System.out.println("Working directory: " + System.getProperty("user.dir"));
        System.out.println("Simulation path: " + p.getInstancePath());

        // This instructs the parameter handler to re-initialize the random
        // number generator and to update paths to reflect the next
        // iterate.
        p.advance();
        return ex;
    }

    /**
     * Run all iterations, including the initial condition (t=0),
     * updating any solutes and agents, as well as advancing the clock,
     * according to the nanoverse.runtime.processes specified in the project file.
     *
     * @return
     */
    private HaltCondition go() {
        for (int n = 0; n < p.T(); n++) {
            StepState state = new StepState(time, n);
            try {
                state = processManager.doTriggeredProcesses(state);
            } catch (HaltCondition haltCondition) {
                haltCondition.setGillespie(state.getTime());
                return haltCondition;
            }

            // Send the results to the serialization manager.
            serializationManager.flush(state);
            time = state.getTime();
        }

        // If we got here, it's because we got through the outermost
        // loop, which proceeds for a specified number of iterations
        // before terminating. (This prevents infinite loops.)
        StepMaxReachedEvent ret = new StepMaxReachedEvent();
        ret.setGillespie(time);
        return ret;
    }

    @Override
    public int hashCode() {
        int result = processManager != null ? processManager.hashCode() : 0;
        result = 31 * result + (serializationManager != null ? serializationManager.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Integrator that = (Integrator) o;

        if (processManager != null ? !processManager.equals(that.processManager) : that.processManager != null)
            return false;
        if (serializationManager != null ? !serializationManager.equals(that.serializationManager) : that.serializationManager != null)
            return false;

        return true;
    }
}
