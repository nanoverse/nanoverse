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

package nanoverse.compiler.pipeline.translate.symbol.processes.discrete.cluster;

import nanoverse.compiler.pipeline.instantiate.loader.Loader;
import nanoverse.compiler.pipeline.instantiate.loader.processes.discrete.cluster.CompactSeparatedClustersHelperLoader;
import nanoverse.compiler.pipeline.translate.symbol.MapSymbolTable;
import nanoverse.runtime.processes.discrete.cluster.CompactSeparatedClustersHelper;

/**
 * Created by dbborens on 9/15/15.
 */
public class CompactSeparatedClustersHelperInstSymbolTable extends MapSymbolTable<CompactSeparatedClustersHelper> {

    @Override
    public Loader getLoader() {
        return new CompactSeparatedClustersHelperLoader();
    }

    @Override
    public String getDescription() {
        return "Clusters of agents that never intersect and that minimize " +
            "each cluster's surface-to-volume ratio.";
    }
}
