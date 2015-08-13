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
package compiler.pipeline.instantiate.factory.processes.continuum;

import control.arguments.Argument;
import processes.continuum.InjectionProcess;
import control.arguments.DoubleArgument;
import processes.BaseProcessArguments;
import geometry.set.CoordinateSet;
import compiler.pipeline.instantiate.factory.Factory;

public class InjectionProcessFactory implements Factory<InjectionProcess> {

    private final InjectionProcessFactoryHelper helper;

    private BaseProcessArguments arguments;
    private DoubleArgument valueArg;
    private String layerId;
    private CoordinateSet activeSites;

    public InjectionProcessFactory() {
        helper = new InjectionProcessFactoryHelper();
    }

    public InjectionProcessFactory(InjectionProcessFactoryHelper helper) {
        this.helper = helper;
    }

    public void setArguments(BaseProcessArguments arguments) {
        this.arguments = arguments;
    }

    public void setValueArg(DoubleArgument valueArg) {
        this.valueArg = valueArg;
    }

    public void setLayerId(String layerId) {
        this.layerId = layerId;
    }

    public void setActiveSites(CoordinateSet activeSites) {
        this.activeSites = activeSites;
    }

    @Override
    public InjectionProcess build() {
        return helper.build(arguments, valueArg, layerId, activeSites);
    }
}