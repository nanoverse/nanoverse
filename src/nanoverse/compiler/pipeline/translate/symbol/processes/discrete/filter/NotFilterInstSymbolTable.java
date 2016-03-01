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

package nanoverse.compiler.pipeline.translate.symbol.processes.discrete.filter;

import nanoverse.compiler.pipeline.instantiate.loader.Loader;
import nanoverse.compiler.pipeline.instantiate.loader.processes.discrete.filter.DepthFilterLoader;
import nanoverse.compiler.pipeline.instantiate.loader.processes.discrete.filter.NotFilterLoader;
import nanoverse.compiler.pipeline.translate.symbol.MapSymbolTable;
import nanoverse.compiler.pipeline.translate.symbol.MemberSymbol;
import nanoverse.compiler.pipeline.translate.symbol.ResolvingSymbolTable;
import nanoverse.compiler.pipeline.translate.symbol.primitive.integers.IntegerClassSymbolTable;
import nanoverse.runtime.processes.discrete.filter.DepthFilter;
import nanoverse.runtime.processes.discrete.filter.NotFilter;

import java.util.HashMap;

/**
 * Created by dbborens on 8/24/2015.
 */
public class NotFilterInstSymbolTable extends MapSymbolTable<NotFilter> {
    @Override
    public Loader getLoader() {
        return new NotFilterLoader();
    }

    @Override
    public String getDescription() {
        return "Inverts the truth conditions of another filter.";
    }

    @Override
    public HashMap<String, MemberSymbol> resolveMembers() {
        HashMap<String, MemberSymbol> ret = super.resolveMembers();
        child(ret);
        return ret;
    }

    private void child(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new FilterClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "The filter to invert.");
        ret.put("child", ms);
    }
}

