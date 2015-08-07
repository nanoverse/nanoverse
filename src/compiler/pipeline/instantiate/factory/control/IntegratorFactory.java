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

import control.GeneralParameters;
import control.ProcessManager;
import io.serialize.SerializationManager;
import control.Integrator;
import compiler.pipeline.instantiate.factory.Factory;

public class IntegratorFactory implements Factory<Integrator> {

    private final IntegratorFactoryHelper helper;

    private GeneralParameters p;
    private ProcessManager processManager;
    private SerializationManager serializationManager;

    public IntegratorFactory() {
        helper = new IntegratorFactoryHelper();
    }

    public IntegratorFactory(IntegratorFactoryHelper helper) {
        this.helper = helper;
    }

    public void setP(GeneralParameters p) {
        this.p = p;
    }

    public void setProcessManager(ProcessManager processManager) {
        this.processManager = processManager;
    }

    public void setSerializationManager(SerializationManager serializationManager) {
        this.serializationManager = serializationManager;
    }

    @Override
    public Integrator build() {
        return helper.build(p, processManager, serializationManager);
    }
}