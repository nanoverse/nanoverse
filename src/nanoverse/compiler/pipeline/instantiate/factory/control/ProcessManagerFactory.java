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
package nanoverse.compiler.pipeline.instantiate.factory.control;

import nanoverse.compiler.pipeline.instantiate.factory.Factory;
import nanoverse.runtime.control.ProcessManager;
import nanoverse.runtime.layers.LayerManager;
import nanoverse.runtime.processes.NanoverseProcess;

import java.util.stream.Stream;

public class ProcessManagerFactory implements Factory<ProcessManager> {

    private final ProcessManagerFactoryHelper helper;

    private Stream<NanoverseProcess> processes;
    private LayerManager layerManager;

    public ProcessManagerFactory() {
        helper = new ProcessManagerFactoryHelper();
    }

    public ProcessManagerFactory(ProcessManagerFactoryHelper helper) {
        this.helper = helper;
    }

    public void setProcesses(Stream<NanoverseProcess> processes) {
        this.processes = processes;
    }

    public void setLayerManager(LayerManager layerManager) {
        this.layerManager = layerManager;
    }

    @Override
    public ProcessManager build() {
        return helper.build(processes, layerManager);
    }
}