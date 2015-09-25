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

package nanoverse.compiler.pipeline.instantiate.loader.processes.discrete.cluster;

import nanoverse.compiler.pipeline.instantiate.factory.processes.discrete.cluster.CompactSeparatedClustersHelperFactory;
import nanoverse.compiler.pipeline.translate.nodes.MapObjectNode;
import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.layers.LayerManager;
import nanoverse.runtime.layers.cell.CellLayer;
import nanoverse.runtime.processes.discrete.cluster.CompactSeparatedClustersHelper;

/**
 * Created by dbborens on 8/29/15.
 */
public class CompactSeparatedClustersHelperLoader extends ScatterClustersHelperLoader<CompactSeparatedClustersHelper> {

    private CompactSeparatedClustersHelperFactory factory;

    public CompactSeparatedClustersHelperLoader() {
        this.factory = factory;
    }

    public CompactSeparatedClustersHelperLoader
            (CompactSeparatedClustersHelperFactory factory) {

        this.factory = factory;
    }

    @Override
    public CompactSeparatedClustersHelper instantiate(MapObjectNode node, LayerManager lm, GeneralParameters p) {
        CellLayer layer = lm.getCellLayer();
        factory.setLayer(layer);
        return factory.build();
    }

    public CompactSeparatedClustersHelper instantiate(LayerManager lm, GeneralParameters p) {
        return instantiate(null, lm, p);
    }
}
