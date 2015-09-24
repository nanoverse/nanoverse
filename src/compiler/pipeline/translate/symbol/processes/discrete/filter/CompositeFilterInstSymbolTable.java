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

package compiler.pipeline.translate.symbol.processes.discrete.filter;

import compiler.pipeline.instantiate.loader.Loader;
import compiler.pipeline.instantiate.loader.processes.discrete.filter.*;
import compiler.pipeline.translate.symbol.*;
import processes.discrete.filter.CompositeFilter;

import java.util.HashMap;

/**
 * Created by dbborens on 8/24/2015.
 */
public class CompositeFilterInstSymbolTable extends MapSymbolTable<CompositeFilter> {

    @Override
    public Loader getLoader() {
        return new CompositeFilterLoader();
    }

    @Override
    public String getDescription() {
        return "A composite filter represents a collection of filters, all " +
            "of which must be satisfied before a site is included as a valid " +
            "process target.";
    }

    @Override
    protected HashMap<String, MemberSymbol> resolveMembers() {
        HashMap<String, MemberSymbol> ret = super.resolveMembers();
        including(ret);
        return ret;
    }

    private void including(HashMap<String, MemberSymbol> ret) {
        ClassSymbolTable cst = new FilterClassSymbolTable();
        ListSymbolTable rst = new ListSymbolTable(cst, FilterStreamLoader::new);
        MemberSymbol ms = new MemberSymbol(rst, "The list of filters to include in the composite.");
        ret.put("including", ms);
    }
}
