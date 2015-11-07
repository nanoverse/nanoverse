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

package nanoverse.compiler.pipeline.instantiate.loader.processes.discrete.filter;

import nanoverse.compiler.pipeline.instantiate.loader.Loader;
import nanoverse.compiler.pipeline.translate.nodes.*;
import nanoverse.compiler.pipeline.translate.symbol.InstantiableSymbolTable;
import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.layers.LayerManager;
import nanoverse.runtime.processes.discrete.filter.Filter;

import java.util.stream.Stream;

/**
 * Created by dbborens on 8/24/2015.
 */
public class FilterStreamLoader extends Loader<Stream<Filter>> {

    public Stream<Filter> instantiate(ListObjectNode node, LayerManager lm, GeneralParameters p) {
        if (node == null) {
            return Stream.empty();
        }

        return node.getMemberStream()
            .map(cNode -> (MapObjectNode) cNode)
            .map(cNode -> toFilter(cNode, lm, p));
    }

    private Filter toFilter(MapObjectNode cNode, LayerManager lm, GeneralParameters p) {
        InstantiableSymbolTable ist = cNode.getSymbolTable();
        FilterLoader loader = (FilterLoader) ist.getLoader();
        return loader.instantiate(cNode, lm, p);
    }
}
