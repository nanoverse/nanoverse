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

package compiler.pipeline.instantiate.helpers;

import compiler.pipeline.instantiate.loader.primitive.integers.IntegerArgumentLoader;
import compiler.pipeline.translate.nodes.*;
import control.arguments.IntegerArgument;

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

        ObjectNode cNode = node.getMember(id);
        IntegerArgumentLoader loader = (IntegerArgumentLoader)
                retriever.getLoader(node, id, required);

        if (loader == null) {
            return defaultSupplier.get();
        }

        return loader.instantiateToFirst(cNode, random);
    }

    public IntegerArgument loadArgument(MapObjectNode node, String id, Random random,
                                        Supplier<IntegerArgument> defaultSupplier) {

        boolean required = (defaultSupplier == null);

        ObjectNode cNode = node.getMember(id);
        IntegerArgumentLoader loader = (IntegerArgumentLoader)
                retriever.getLoader(node, id, required);

        if (loader == null) {
            return defaultSupplier.get();
        }

        return loader.instantiate(cNode, random);
    }
}
