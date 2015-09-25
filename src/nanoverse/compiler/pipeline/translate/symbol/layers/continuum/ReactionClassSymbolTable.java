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

package nanoverse.compiler.pipeline.translate.symbol.layers.continuum;

import nanoverse.compiler.pipeline.translate.symbol.*;
import nanoverse.runtime.layers.continuum.Reaction;

import java.util.HashMap;
import java.util.function.Supplier;

/**
 * Created by dbborens on 7/24/2015.
 */
public class ReactionClassSymbolTable extends ClassSymbolTable<Reaction> {

    @Override
    protected HashMap<String, Supplier<InstantiableSymbolTable>> resolveSubclasses() {
        HashMap<String, Supplier<InstantiableSymbolTable>> ret = new HashMap<>();
        reaction(ret);
        return ret;
    }

    @Override
    public String getDescription() {
        return "Agents can interact with the local value of a continuum. " +
            "Reactions specify the terms of those interactions, allowing " +
            "both constant increase or decrease (injection) and proportional " +
            "scaling (exponentiation).";
    }

    private void reaction(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {
        Supplier<InstantiableSymbolTable> supplier = ReactionInstSymbolTable::new;
        ret.put("Reaction", supplier);
    }
}
