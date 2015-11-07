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

package nanoverse.compiler.pipeline.instantiate.helpers;

import nanoverse.compiler.pipeline.instantiate.loader.primitive.integers.IntegerArgumentLoader;
import nanoverse.compiler.pipeline.translate.nodes.*;
import nanoverse.runtime.control.arguments.IntegerArgument;

import java.util.Random;
import java.util.function.Supplier;

/**
 * Created by dbborens on 8/14/2015.
 */
public class IntegerLoadHelper {

    private LoaderRetriever retriever;

    public IntegerLoadHelper() {
        retriever = new LoaderRetriever();
    }

    public IntegerLoadHelper(LoaderRetriever retriever) {
        this.retriever = retriever;
    }

    public Integer load(MapObjectNode node, String id, Random random,
                        Supplier<Integer> defaultSupplier) {

        boolean required = (defaultSupplier == null);

        if (!required && node == null) {
            return defaultSupplier.get();
        }

        if (!required && !node.hasMember(id)) {
            return defaultSupplier.get();
        }

        ObjectNode cNode = node.getMember(id);
        IntegerArgumentLoader loader = (IntegerArgumentLoader)
            retriever.getLoader(node, id, required);

        return loader.instantiateToFirst(cNode, random);
    }

    public IntegerArgument loadArgument(MapObjectNode node, String id, Random random,
                                        Supplier<IntegerArgument> defaultSupplier) {

        boolean required = (defaultSupplier == null);

        if (!required && node == null) {
            return defaultSupplier.get();
        }

        if (!required && !node.hasMember(id)) {
            return defaultSupplier.get();
        }

        ObjectNode cNode = node.getMember(id);
        IntegerArgumentLoader loader = (IntegerArgumentLoader)
            retriever.getLoader(node, id, required);

        return loader.instantiate(cNode, random);
    }
}
