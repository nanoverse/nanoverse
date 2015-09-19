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

import compiler.error.MissingArgumentError;
import compiler.pipeline.instantiate.loader.Loader;
import compiler.pipeline.translate.nodes.*;
import compiler.pipeline.translate.symbol.InstantiableSymbolTable;

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
            throw new MissingArgumentError(field, parent.getInstantiatingClass());
        } else {
            return null;
        }

    }
}
