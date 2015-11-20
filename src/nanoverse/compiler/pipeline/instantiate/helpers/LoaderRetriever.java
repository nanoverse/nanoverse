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

import nanoverse.compiler.error.MissingArgumentError;
import nanoverse.compiler.pipeline.instantiate.loader.Loader;
import nanoverse.compiler.pipeline.translate.nodes.*;
import nanoverse.compiler.pipeline.translate.symbol.InstantiableSymbolTable;

/**
 * Created by dbborens on 8/14/2015.
 */
public class LoaderRetriever {
    public Loader getLoader(MapObjectNode parent, String field, boolean require) {
        if (parent == null || !parent.hasMember(field)) {
            return handleMissingData(parent, field, require);
        }

        ObjectNode node = parent.getMember(field);
        if (node == null && require) {
        } else if (node == null) {
            return null;
        }

        InstantiableSymbolTable ist = node.getSymbolTable();
        Loader loader = ist.getLoader();
        return loader;
    }

    private Loader handleMissingData(MapObjectNode parent, String field, boolean require) {
        if (require) {
            throw new MissingArgumentError(field, parent.getInstantiatingClass(), parent.lineNumber());
        } else {
            return null;
        }

    }
}
