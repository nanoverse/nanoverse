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
package compiler.pipeline.instantiate.factory.processes;

import control.GeneralParameters;
import control.arguments.Argument;
import control.arguments.IntegerArgument;
import processes.BaseProcessArguments;
import layers.LayerManager;
import compiler.pipeline.instantiate.factory.Factory;

public class BaseProcessArgumentsFactory implements Factory<BaseProcessArguments> {

    private final BaseProcessArgumentsFactoryHelper helper;

    private LayerManager layerManager;
    private GeneralParameters generalParameters;
    private int id;
    private Argument<Integer> start;
    private Argument<Integer> period;

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

    public void setStart(Argument<Integer> start) {
        this.start = start;
    }

    public void setPeriod(Argument<Integer> period) {
        this.period = period;
    }

    @Override
    public BaseProcessArguments build() {
        return helper.build(layerManager, generalParameters, id, start, period);
    }
}