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
package nanoverse.compiler.pipeline.instantiate.factory.processes;

import nanoverse.compiler.pipeline.instantiate.factory.Factory;
import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.control.arguments.IntegerArgument;
import nanoverse.runtime.layers.LayerManager;
import nanoverse.runtime.processes.BaseProcessArguments;

public class BaseProcessArgumentsFactory implements Factory<BaseProcessArguments> {

    private final BaseProcessArgumentsFactoryHelper helper;

    private LayerManager layerManager;
    private GeneralParameters generalParameters;
    private int id;
    private IntegerArgument start;
    private IntegerArgument period;

    public BaseProcessArgumentsFactory() {
        helper = new BaseProcessArgumentsFactoryHelper();
    }

    public BaseProcessArgumentsFactory(BaseProcessArgumentsFactoryHelper helper) {
        this.helper = helper;
    }

    public void setLayerManager(LayerManager layerManager) {
        this.layerManager = layerManager;
    }

    public void setGeneralParameters(GeneralParameters generalParameters) {
        this.generalParameters = generalParameters;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStart(IntegerArgument start) {
        this.start = start;
    }

    public void setPeriod(IntegerArgument period) {
        this.period = period;
    }

    @Override
    public BaseProcessArguments build() {
        return helper.build(layerManager, generalParameters, id, start, period);
    }
}