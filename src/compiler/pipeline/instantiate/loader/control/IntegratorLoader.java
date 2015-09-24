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

package compiler.pipeline.instantiate.loader.control;

import compiler.pipeline.instantiate.factory.control.IntegratorFactory;
import compiler.pipeline.instantiate.loader.Loader;
import control.GeneralParameters;
import control.Integrator;
import control.ProcessManager;
import io.serialize.SerializationManager;

/**
 * Created by dbborens on 8/13/15.
 */
public class IntegratorLoader extends Loader<Integrator> {

    private final IntegratorFactory factory;

    public IntegratorLoader() {
        factory = new IntegratorFactory();
    }

    public IntegratorLoader(IntegratorFactory factory) {
        this.factory = factory;
    }

    public Integrator instantiate(GeneralParameters p, ProcessManager processManager,
                      SerializationManager serializationManager) {

        factory.setP(p);
        factory.setProcessManager(processManager);
        factory.setSerializationManager(serializationManager);

        return factory.build();
    }
}
