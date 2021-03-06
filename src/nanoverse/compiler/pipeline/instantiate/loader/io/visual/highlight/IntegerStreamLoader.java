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

package nanoverse.compiler.pipeline.instantiate.loader.io.visual.highlight;

import nanoverse.compiler.pipeline.instantiate.loader.Loader;
import nanoverse.compiler.pipeline.instantiate.loader.primitive.integers.IntegerArgumentLoader;
import nanoverse.compiler.pipeline.translate.nodes.*;
import nanoverse.runtime.control.GeneralParameters;

import java.util.stream.Stream;

/**
 * Created by dbborens on 8/23/2015.
 */
public class IntegerStreamLoader extends Loader<Stream<Integer>> {

    public Stream<Integer> instantiate(ListObjectNode node, GeneralParameters p) {
        return node.getMemberStream()
            .map(cNode -> toInteger(cNode, p));
    }

    private Integer toInteger(ObjectNode node, GeneralParameters p) {
        MapObjectNode mNode = (MapObjectNode) node;
        IntegerArgumentLoader loader = (IntegerArgumentLoader) mNode.
            getSymbolTable().getLoader();

        return loader.instantiateToFirst(node, p.getRandom());
    }
}
