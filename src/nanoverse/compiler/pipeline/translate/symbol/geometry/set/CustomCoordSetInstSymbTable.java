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

package nanoverse.compiler.pipeline.translate.symbol.geometry.set;

import nanoverse.compiler.pipeline.instantiate.loader.Loader;
import nanoverse.compiler.pipeline.instantiate.loader.geometry.set.CustomCoordinateSetLoader;
import nanoverse.compiler.pipeline.translate.symbol.*;
import nanoverse.compiler.pipeline.translate.symbol.control.identifiers.CoordinateClassSymbolTable;
import nanoverse.runtime.geometry.set.CustomSet;

import java.util.HashMap;

/**
 * Created by dbborens on 7/25/2015.
 */
public class CustomCoordSetInstSymbTable extends MapSymbolTable<CustomSet> {
    @Override
    public String getDescription() {
        return "A user-specified set of coordinates.";
    }

    @Override
    protected HashMap<String, MemberSymbol> resolveMembers() {
        HashMap<String, MemberSymbol> ret = super.resolveMembers();
        members(ret);
        return ret;
    }

    private void members(HashMap<String, MemberSymbol> ret) {
        ClassSymbolTable cst = new CoordinateClassSymbolTable();
        ListSymbolTable lst = new ListSymbolTable(cst, CustomCoordinateSetLoader::new);
        MemberSymbol ms = new MemberSymbol(lst, "The list of coordinates to include.");
        ret.put("members", ms);
    }

    @Override
    public Loader getLoader() {
        return new CustomCoordinateSetLoader();
    }
}
