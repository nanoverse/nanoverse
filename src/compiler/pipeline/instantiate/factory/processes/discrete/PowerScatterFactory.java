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
package compiler.pipeline.instantiate.factory.processes.discrete;

import processes.discrete.PowerScatter;
import processes.discrete.CellProcessArguments;
import processes.discrete.cluster.ScatterClustersHelper;
import processes.BaseProcessArguments;
import control.arguments.CellDescriptor;
import compiler.pipeline.instantiate.factory.Factory;

public class PowerScatterFactory implements Factory<PowerScatter> {

    private final PowerScatterFactoryHelper helper;

    private BaseProcessArguments arguments;
    private CellProcessArguments cpArguments;
    private CellDescriptor cellDescriptor;
    private ScatterClustersHelper clustersHelper;

    public PowerScatterFactory() {
        helper = new PowerScatterFactoryHelper();
    }

    public PowerScatterFactory(PowerScatterFactoryHelper helper) {
        this.helper = helper;
    }

    public void setArguments(BaseProcessArguments arguments) {
        this.arguments = arguments;
    }

    public void setCpArguments(CellProcessArguments cpArguments) {
        this.cpArguments = cpArguments;
    }

    public void setCellDescriptor(CellDescriptor cellDescriptor) {
        this.cellDescriptor = cellDescriptor;
    }

    public void setClustersHelper(ScatterClustersHelper clustersHelper) {
        this.clustersHelper = clustersHelper;
    }

    @Override
    public PowerScatter build() {
        return helper.build(arguments, cpArguments, cellDescriptor, clustersHelper);
    }
}