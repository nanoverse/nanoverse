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
package compiler.pipeline.instantiate.factory.processes.discrete.cluster;

import control.GeneralParameters;
import layers.cell.CellLayer;
import processes.discrete.cluster.CompactSeparatedClustersHelper;
import compiler.pipeline.instantiate.factory.Factory;

public class CompactSeparatedClustersHelperFactory implements Factory<CompactSeparatedClustersHelper> {

    private final CompactSeparatedClustersHelperFactoryHelper helper;

    private CellLayer layer;
    private GeneralParameters p;

    public CompactSeparatedClustersHelperFactory() {
        helper = new CompactSeparatedClustersHelperFactoryHelper();
    }

    public CompactSeparatedClustersHelperFactory(CompactSeparatedClustersHelperFactoryHelper helper) {
        this.helper = helper;
    }

    public void setLayer(CellLayer layer) {
        this.layer = layer;
    }

    public void setP(GeneralParameters p) {
        this.p = p;
    }

    @Override
    public CompactSeparatedClustersHelper build() {
        return helper.build(layer, p);
    }
}