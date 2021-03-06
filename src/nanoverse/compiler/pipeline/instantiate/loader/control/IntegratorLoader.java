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

package nanoverse.compiler.pipeline.instantiate.loader.control;

import nanoverse.compiler.pipeline.instantiate.factory.control.IntegratorFactory;
import nanoverse.compiler.pipeline.instantiate.loader.Loader;
import nanoverse.runtime.control.*;
import nanoverse.runtime.io.serialize.SerializationManager;

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
