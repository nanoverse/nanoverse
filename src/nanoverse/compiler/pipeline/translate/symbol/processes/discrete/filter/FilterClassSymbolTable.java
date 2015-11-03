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

import nanoverse.compiler.pipeline.translate.symbol.*;
import nanoverse.runtime.processes.discrete.filter.Filter;

import java.util.HashMap;
import java.util.function.Supplier;

/**
 * Created by dbborens on 7/23/2015.
 */
public class FilterClassSymbolTable extends ClassSymbolTable<Filter> {
    @Override
    public String getDescription() {
        return "Filters restrict the targets for nanoverse.runtime.processes (system-wide, top-" +
            "down events) according to a specific rule.";
    }

    @Override
    protected HashMap<String, Supplier<InstantiableSymbolTable>> resolveSubclasses() {
        HashMap<String, Supplier<InstantiableSymbolTable>> ret = new HashMap<>();
        name(ret);
        composite(ret);
        depth(ret);
        sample(ret);
        nullFilter(ret);
        return ret;
    }

    private void name(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {
        Supplier<InstantiableSymbolTable> st = AgentClassFilterInstSymbolTable::new;
        ret.put("Name", st);
    }

    private void composite(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {
        Supplier<InstantiableSymbolTable> st = CompositeFilterInstSymbolTable::new;
        ret.put("Composite", st);
    }

    private void depth(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {
        Supplier<InstantiableSymbolTable> st = DepthFilterInstSymbolTable::new;
        ret.put("Depth", st);
    }

    private void sample(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {
        Supplier<InstantiableSymbolTable> st = SampleFilterInstSymbolTable::new;
        ret.put("Sample", st);
    }

    private void nullFilter(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {
        Supplier<InstantiableSymbolTable> st = NullFilterInstSymbolTable::new;
        ret.put("Null", st);
    }

}
