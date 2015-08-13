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

import control.arguments.Argument;
import processes.discrete.CellProcessArguments;
import control.arguments.IntegerArgument;
import processes.discrete.cluster.ScatterClustersHelper;
import processes.discrete.ScatterClusters;
import processes.BaseProcessArguments;
import control.arguments.CellDescriptor;
import compiler.pipeline.instantiate.factory.Factory;

public class ScatterClustersFactory implements Factory<ScatterClusters> {

    private final ScatterClustersFactoryHelper helper;

    private BaseProcessArguments arguments;
    private CellProcessArguments cpArguments;
    private Argument<Integer> neighborCount;
    private CellDescriptor cellDescriptor;
    private ScatterClustersHelper clustersHelper;

    public ScatterClustersFactory() {
        helper = new ScatterClustersFactoryHelper();
    }

    public ScatterClustersFactory(ScatterClustersFactoryHelper helper) {
        this.helper = helper;
    }

    public void setArguments(BaseProcessArguments arguments) {
        this.arguments = arguments;
    }

    public void setCpArguments(CellProcessArguments cpArguments) {
        this.cpArguments = cpArguments;
    }

    public void setNeighborCount(Argument<Integer> neighborCount) {
        this.neighborCount = neighborCount;
    }

    public void setCellDescriptor(CellDescriptor cellDescriptor) {
        this.cellDescriptor = cellDescriptor;
    }

    public void setClustersHelper(ScatterClustersHelper clustersHelper) {
        this.clustersHelper = clustersHelper;
    }

    @Override
    public ScatterClusters build() {
        return helper.build(arguments, cpArguments, neighborCount, cellDescriptor, clustersHelper);
    }
}