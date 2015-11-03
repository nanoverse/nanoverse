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
import nanoverse.compiler.pipeline.instantiate.loader.processes.discrete.filter.SampleFilterLoader;
import nanoverse.compiler.pipeline.translate.symbol.*;
import nanoverse.compiler.pipeline.translate.symbol.primitive.integers.IntegerClassSymbolTable;
import nanoverse.runtime.processes.discrete.filter.SampleFilter;

import java.util.HashMap;

/**
 * Created by dbborens on 8/24/2015.
 */
public class SampleFilterInstSymbolTable extends MapSymbolTable<SampleFilter> {
    @Override
    public Loader getLoader() {
        return new SampleFilterLoader();
    }

    @Override
    public String getDescription() {
        return "Sample filter takes a random sample of the candidates if the " +
            "number of candidates exceeds a specified limit. If the number " +
            "of candidates is at or below the limit, nothing happens. " +
            "Setting the limit to -1 is the same as having no limit.";
    }

    @Override
    protected HashMap<String, MemberSymbol> resolveMembers() {
        HashMap<String, MemberSymbol> ret = super.resolveMembers();
        maximum(ret);
        return ret;
    }

    private void maximum(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new IntegerClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "The maximum number of " +
            "agents allowed before sampling is applied.");
        ret.put("maximum", ms);
    }
}

