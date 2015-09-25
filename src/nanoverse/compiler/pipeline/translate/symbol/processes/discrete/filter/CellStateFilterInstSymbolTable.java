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

package nanoverse.compiler.pipeline.translate.symbol.processes.discrete.filter;

import nanoverse.compiler.pipeline.instantiate.loader.Loader;
import nanoverse.compiler.pipeline.instantiate.loader.processes.discrete.filter.CellStateFilterLoader;
import nanoverse.compiler.pipeline.translate.symbol.*;
import nanoverse.compiler.pipeline.translate.symbol.primitive.integers.IntegerClassSymbolTable;
import nanoverse.runtime.processes.discrete.filter.CellStateFilter;

import java.util.HashMap;

/**
 * Created by dbborens on 8/24/2015.
 */
public class CellStateFilterInstSymbolTable extends MapSymbolTable<CellStateFilter> {
    @Override
    public Loader getLoader() {
        return new CellStateFilterLoader();
    }

    @Override
    public String getDescription() {
        return "Restricts the process to agents with a specific state/class.";
    }

    @Override
    protected HashMap<String, MemberSymbol> resolveMembers() {
        HashMap<String, MemberSymbol> ret = super.resolveMembers();
        state(ret);
        return ret;
    }

    private void state(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new IntegerClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "The nanoverse.runtime.agent state/class to which the process should be restricted.");
        ret.put("state", ms);
    }
}
