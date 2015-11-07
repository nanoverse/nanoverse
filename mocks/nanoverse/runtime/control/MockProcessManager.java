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

import nanoverse.runtime.control.halt.HaltCondition;
import nanoverse.runtime.processes.*;

import java.util.List;
import java.util.stream.Stream;

/**
 * Created by dbborens on 1/13/14.
 */
public class MockProcessManager extends ProcessManager {
    int timesIterated;

    boolean doTriggeredProcessedCalled;
    private List<NanoverseProcess> triggeredProcesses;
    private double stepStateDt;

    public MockProcessManager() {
        super(Stream.empty(), null);
        timesIterated = 0;
        doTriggeredProcessedCalled = false;
        stepStateDt = 0.0;
    }

    public void setTriggeredProcesses(List<NanoverseProcess> triggeredProcesses) {
        this.triggeredProcesses = triggeredProcesses;
    }

    @Override
    public StepState doTriggeredProcesses(StepState stepState) throws HaltCondition {
        timesIterated++;
        for (NanoverseProcess p : triggeredProcesses) {
            p.fire(stepState);
        }
        stepState.advanceClock(stepStateDt);
        return stepState;
    }

    @Override
    protected List<NanoverseProcess> getTriggeredProcesses(int n) {
        return triggeredProcesses;
    }

    @Override
    public void init() {
    }

    public void setStepStateDt(double stepStateDt) {
        this.stepStateDt = stepStateDt;
    }

    public int getTimesIterated() {
        return timesIterated;
    }
}