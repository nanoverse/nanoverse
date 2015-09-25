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

package nanoverse.runtime.control;

import nanoverse.runtime.control.halt.HaltCondition;
import nanoverse.runtime.layers.LayerManager;
import nanoverse.runtime.processes.NanoverseProcess;
import nanoverse.runtime.processes.StepState;
import nanoverse.runtime.structural.annotations.FactoryTarget;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.*;

/**
 * Created by dbborens on 1/6/14.
 */
public class ProcessManager {

    private List<NanoverseProcess> processList;
    private LayerManager layerManager;

    @FactoryTarget
    public ProcessManager(Stream<NanoverseProcess> processes, LayerManager layerManager) {
        processList = processes.collect(Collectors.toList());
        this.layerManager = layerManager;
    }

    protected List<NanoverseProcess> getTriggeredProcesses(int n) throws HaltCondition {

        ArrayList<NanoverseProcess> triggeredProcesses = new ArrayList<>(processList.size());

        for (NanoverseProcess process : processList) {
            if (triggered(n, process)) {
                triggeredProcesses.add(process);
            }
        }

        return triggeredProcesses;
    }

    protected boolean triggered(int n, NanoverseProcess process) throws HaltCondition {
        int period = process.getPeriod().next();
        int start = process.getStart().next();

        // Case 1: this is a 1-time event, and it is that one time.
        if (period == 0 && start == n) {
            return true;

            // Case 2: this is a 1-time event, and it isn't that time.
        } else if (period == 0 && start != n) {
            return false;

            // Case 3: We haven't reached the start time.
        } else if (n < start) {
            return false;

            // Case 4: We have reached the start time.
        } else if (n >= start) {
            // Adjust phase.
            int tt = n - start;

            // 4a: Phase-adjusted time fits period.
            if (tt % period == 0) {
                return true;

                // 4b: It doesn't.
            } else {
                return false;
            }

        } else {
            throw new IllegalStateException("Unconsidered trigger state reached.");
        }
    }

    public StepState doTriggeredProcesses(StepState stepState) throws HaltCondition {

        // Pass the step state object to the layer manager. This way, both actions
        // and nanoverse.runtime.processes can access it.
        layerManager.setStepState(stepState);

        // Get triggered events.
        List<NanoverseProcess> triggeredProcesses = getTriggeredProcesses(stepState.getFrame());

        // Fire each triggered cell event.
        for (NanoverseProcess process : triggeredProcesses) {
                process.iterate();
        }

        // There's no reason for the layer manager to touch the StepState
        // object until the next cycle. If it does, the program should blow up,
        // so we have it throw a null pointer exception.
        layerManager.setStepState(null);

        return stepState;
    }

    /**
     * Resets all nanoverse.runtime.layers and nanoverse.runtime.processes to their original
     * configurations.
     */
    public void init() {
        layerManager.reset();
        for (NanoverseProcess process : processList) {
            process.init();
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof ProcessManager)) {
            return false;
        }

        ProcessManager other = (ProcessManager) obj;

        if (other.processList.size() != this.processList.size()) {
            return false;
        }

        for (int i = 0; i < processList.size(); i++) {
            NanoverseProcess mine = processList.get(i);
            NanoverseProcess theirs = other.processList.get(i);

            if (!mine.equals(theirs)) {
                return false;
            }
        }

        return true;
    }
}
