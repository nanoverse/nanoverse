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

package nanoverse.compiler.pipeline.translate.symbol.io.visual.highlight;

import nanoverse.compiler.pipeline.translate.symbol.*;
import nanoverse.runtime.io.visual.highlight.Highlight;

import java.util.HashMap;
import java.util.function.Supplier;

/**
 * Created by dbborens on 7/27/2015.
 */
public class HighlightClassSymbolTable extends ClassSymbolTable<Highlight> {

    @Override
    public String getDescription() {
        return "A highlight describes how selected locations (specified " +
            "using a highlight channel) should be accentuated in a " +
            "visualization.";
    }

    @Override
    protected HashMap<String, Supplier<InstantiableSymbolTable>> resolveSubclasses() {
        HashMap<String, Supplier<InstantiableSymbolTable>> ret = new HashMap<>();
        highlight(ret);
        return ret;
    }

    private void highlight(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {
        Supplier<InstantiableSymbolTable> supplier = HighlightInstSymbolTable::new;
        ret.put("Highlight", supplier);
    }
}
