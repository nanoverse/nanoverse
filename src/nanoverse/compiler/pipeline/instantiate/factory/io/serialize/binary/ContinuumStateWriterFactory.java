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
package nanoverse.compiler.pipeline.instantiate.factory.io.serialize.binary;

import nanoverse.compiler.pipeline.instantiate.factory.Factory;
import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.io.serialize.binary.ContinuumStateWriter;
import nanoverse.runtime.layers.LayerManager;

public class ContinuumStateWriterFactory implements Factory<ContinuumStateWriter> {

    private final ContinuumStateWriterFactoryHelper helper;

    private GeneralParameters p;
    private LayerManager lm;

    public ContinuumStateWriterFactory() {
        helper = new ContinuumStateWriterFactoryHelper();
    }

    public ContinuumStateWriterFactory(ContinuumStateWriterFactoryHelper helper) {
        this.helper = helper;
    }

    public void setP(GeneralParameters p) {
        this.p = p;
    }

    public void setLm(LayerManager lm) {
        this.lm = lm;
    }

    @Override
    public ContinuumStateWriter build() {
        return helper.build(p, lm);
    }
}