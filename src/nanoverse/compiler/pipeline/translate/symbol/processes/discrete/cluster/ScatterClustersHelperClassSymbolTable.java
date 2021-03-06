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

package nanoverse.compiler.pipeline.translate.symbol.processes.discrete.cluster;

import nanoverse.compiler.pipeline.translate.symbol.*;
import nanoverse.runtime.processes.discrete.cluster.SeparationStrategyManager;

import java.util.HashMap;
import java.util.function.Supplier;

/**
 * Created by dbborens on 7/22/2015.
 */
public class ScatterClustersHelperClassSymbolTable extends ClassSymbolTable<SeparationStrategyManager> {
    @Override
    public String getDescription() {
        return "Defines the manner in which cell clusters should " +
            "be separated when using semi-structured scatter rules.";
    }

    @Override
    public HashMap<String, Supplier<InstantiableSymbolTable>> resolveSubclasses() {
        HashMap<String, Supplier<InstantiableSymbolTable>> ret = new HashMap<>();
        strict(ret);
        compact(ret);
        contact(ret);
        return ret;
    }

    private void strict(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {
        Supplier<InstantiableSymbolTable> supplier = () -> new StrictSeparationClusterHelperInstSymbolTable();
        ret.put("strict", supplier);
    }

    private void compact(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {
        Supplier<InstantiableSymbolTable> supplier = () -> new CompactSeparatedClustersHelperInstSymbolTable();
        ret.put("compact", supplier);
    }

    private void contact(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {
        Supplier<InstantiableSymbolTable> supplier = () -> new ContactClustersHelperInstSymbolTable();
        ret.put("contact", supplier);
    }
}
