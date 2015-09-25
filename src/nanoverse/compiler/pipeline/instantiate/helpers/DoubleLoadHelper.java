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

package nanoverse.compiler.pipeline.instantiate.helpers;

import nanoverse.compiler.pipeline.instantiate.loader.primitive.doubles.DoubleArgumentLoader;
import nanoverse.compiler.pipeline.translate.nodes.*;
import nanoverse.runtime.control.arguments.DoubleArgument;

import java.util.Random;
import java.util.function.Supplier;

/**
 * Created by dbborens on 8/14/2015.
 */
public class DoubleLoadHelper {
    private LoaderRetriever retriever;

    public DoubleLoadHelper() {
        retriever = new LoaderRetriever();
    }

    public DoubleLoadHelper(LoaderRetriever retriever) {
        this.retriever = retriever;
    }

    public Double load(MapObjectNode node, String id, Random random,
                       Supplier<Double> defaultSupplier) {

        boolean required = (defaultSupplier == null);

        if (!required && node == null) {
            return defaultSupplier.get();
        }

        if (!required && !node.hasMember(id)) {
            return defaultSupplier.get();
        }

        ObjectNode cNode = node.getMember(id);
        DoubleArgumentLoader loader = (DoubleArgumentLoader)
            retriever.getLoader(node, id, required);

        return loader.instantiateToFirst(cNode, random);
    }

    public DoubleArgument loadArgument(MapObjectNode node,
                                       String id,
                                       Random random,
                                       Supplier<DoubleArgument> defaultSupplier) {

        boolean required = (defaultSupplier == null);

        if (!required && node == null) {
            return defaultSupplier.get();
        }

        if (!required && !node.hasMember(id)) {
            return defaultSupplier.get();
        }

        ObjectNode cNode = node.getMember(id);
        DoubleArgumentLoader loader = (DoubleArgumentLoader)
            retriever.getLoader(node, id, required);

        return loader.instantiate(cNode, random);
    }
}
