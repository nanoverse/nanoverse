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

package compiler.pipeline.instantiate.loader.processes.discrete.filter;

import compiler.pipeline.instantiate.helpers.LoadHelper;
import compiler.pipeline.translate.nodes.*;
import control.GeneralParameters;
import layers.LayerManager;
import layers.cell.CellLayer;
import processes.discrete.filter.Filter;

import java.util.stream.Stream;

/**
 * Created by dbborens on 8/24/2015.
 */
public class CompositeFilterInterpolator {
    private final LoadHelper load;
    private final CompositeFilterDefaults defaults;

    public CompositeFilterInterpolator() {
        load = new LoadHelper();
        defaults = new CompositeFilterDefaults();
    }

    public CompositeFilterInterpolator(LoadHelper load,
                                       CompositeFilterDefaults defaults) {
        this.load = load;
        this.defaults = defaults;
    }

    public Stream<Filter> including(MapObjectNode node, LayerManager lm, GeneralParameters p) {
        FilterStreamLoader loader = (FilterStreamLoader) load.getLoader(node, "including", false);

        if (loader == null) {
            return defaults.including();
        }

        ListObjectNode cNode = (ListObjectNode) node.getMember("including");
        return loader.instantiate(cNode, lm, p);
    }
}
