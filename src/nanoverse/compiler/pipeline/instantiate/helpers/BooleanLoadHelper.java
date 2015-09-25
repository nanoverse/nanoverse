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

import nanoverse.compiler.pipeline.instantiate.loader.primitive.booleans.BooleanArgumentLoader;
import nanoverse.compiler.pipeline.translate.nodes.*;

import java.util.Random;
import java.util.function.Supplier;

/**
 * Created by dbborens on 8/14/2015.
 */
public class BooleanLoadHelper {
    private LoaderRetriever retriever;

    public BooleanLoadHelper() {
        retriever = new LoaderRetriever();
    }

    public BooleanLoadHelper(LoaderRetriever retriever) {
        this.retriever = retriever;
    }

    public Boolean load(MapObjectNode node, String id, Random random,
                        Supplier<Boolean> defaultSupplier) {

        boolean required = (defaultSupplier == null);

        if (!required && node == null) {
            return defaultSupplier.get();
        }

        if (!required && !node.hasMember(id)) {
            return defaultSupplier.get();
        }

        ObjectNode cNode = node.getMember(id);
        BooleanArgumentLoader loader = (BooleanArgumentLoader)
            retriever.getLoader(node, id, required);

        return loader.instantiateToFirst(cNode, random);
    }
}
