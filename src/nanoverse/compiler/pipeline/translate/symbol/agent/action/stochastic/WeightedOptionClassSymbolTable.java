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

package nanoverse.compiler.pipeline.translate.symbol.agent.action.stochastic;

import nanoverse.compiler.pipeline.translate.symbol.*;
import nanoverse.runtime.agent.action.stochastic.WeightedOption;

import java.util.HashMap;
import java.util.function.Supplier;

/**
 * Created by dbborens on 7/23/2015.
 */
public class WeightedOptionClassSymbolTable extends ClassSymbolTable<WeightedOption> {

    @Override
    public String getDescription() {
        return "An weighted option for a stochastic behavior choice.";
    }

    @Override
    public HashMap<String, Supplier<InstantiableSymbolTable>> resolveSubclasses() {
        HashMap<String, Supplier<InstantiableSymbolTable>> ret = new HashMap<>(1);
        option(ret);
        return ret;
    }

    private void option(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {
        Supplier<InstantiableSymbolTable> supplier = WeightedOptionInstSymbolTable::new;
        ret.put("Option", supplier);
    }
}
