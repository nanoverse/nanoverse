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
package nanoverse.compiler.pipeline.instantiate.factory.io.serialize.text;

import nanoverse.compiler.pipeline.instantiate.factory.Factory;
import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.io.serialize.text.InterfaceCensusWriter;
import nanoverse.runtime.layers.LayerManager;

public class InterfaceCensusWriterFactory implements Factory<InterfaceCensusWriter> {

    private final InterfaceCensusWriterFactoryHelper helper;

    private GeneralParameters p;
    private String focus;
    private LayerManager lm;

    public InterfaceCensusWriterFactory() {
        helper = new InterfaceCensusWriterFactoryHelper();
    }

    public InterfaceCensusWriterFactory(InterfaceCensusWriterFactoryHelper helper) {
        this.helper = helper;
    }

    public void setP(GeneralParameters p) {
        this.p = p;
    }

    public void setFocus(String focus) {
        this.focus = focus;
    }

    public void setLm(LayerManager lm) {
        this.lm = lm;
    }

    @Override
    public InterfaceCensusWriter build() {
        return helper.build(p, focus, lm);
    }
}