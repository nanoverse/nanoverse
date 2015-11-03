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

package nanoverse.compiler.pipeline.translate.symbol.agent.action;

import nanoverse.compiler.pipeline.instantiate.loader.Loader;
import nanoverse.compiler.pipeline.instantiate.loader.agent.action.StochasticChoiceLoader;
import nanoverse.compiler.pipeline.instantiate.loader.agent.action.stochastic.DynamicActionRangeMapLoader;
import nanoverse.compiler.pipeline.translate.symbol.*;
import nanoverse.compiler.pipeline.translate.symbol.agent.action.stochastic.WeightedOptionClassSymbolTable;
import nanoverse.runtime.agent.action.StochasticChoiceDescriptor;

import java.util.HashMap;

/**
 * Created by dbborens on 7/22/2015.
 */
public class StochasticChoiceInstSymbolTable extends ActionInstSymbolTable<StochasticChoiceDescriptor> {
    @Override
    public String getDescription() {
        return "Choose one of several options at random. Options may be " +
            "unequally weighted.";
    }

    @Override
    protected HashMap<String, MemberSymbol> resolveMembers() {
        HashMap<String, MemberSymbol> ret = super.resolveMembers();
        options(ret);
        return ret;
    }

    private void options(HashMap<String, MemberSymbol> ret) {
        ClassSymbolTable cst = new WeightedOptionClassSymbolTable();
        ListSymbolTable lst = new ListSymbolTable(cst, DynamicActionRangeMapLoader::new);
        MemberSymbol ms = new MemberSymbol(lst, "List of options from which " +
            "to select.");
        ret.put("options", ms);
    }

    @Override
    public Loader getLoader() {
        return new StochasticChoiceLoader();
    }
}
