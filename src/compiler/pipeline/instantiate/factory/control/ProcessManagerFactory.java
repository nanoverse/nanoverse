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
package compiler.pipeline.instantiate.factory.control;

import java.util.List;
import java.util.stream.Stream;

import control.ProcessManager;
import layers.LayerManager;
import compiler.pipeline.instantiate.factory.Factory;
import processes.NanoverseProcess;

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