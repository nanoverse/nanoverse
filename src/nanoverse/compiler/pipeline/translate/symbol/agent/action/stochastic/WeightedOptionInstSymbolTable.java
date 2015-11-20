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

import nanoverse.compiler.pipeline.instantiate.loader.Loader;
import nanoverse.compiler.pipeline.instantiate.loader.agent.action.CompoundActionLoader;
import nanoverse.compiler.pipeline.instantiate.loader.agent.action.stochastic.WeightedOptionLoader;
import nanoverse.compiler.pipeline.translate.symbol.*;
import nanoverse.compiler.pipeline.translate.symbol.agent.action.ActionClassSymbolTable;
import nanoverse.runtime.agent.action.stochastic.WeightedOption;

import java.util.HashMap;

/**
 * Created by dbborens on 7/23/2015.
 */
public class WeightedOptionInstSymbolTable extends MapSymbolTable<WeightedOption> {

    @Override
    public String getDescription() {
        return "A weighted option";
    }

    @Override
    public HashMap<String, MemberSymbol> resolveMembers() {
        HashMap<String, MemberSymbol> ret = super.resolveMembers();
        weight(ret);
        action(ret);
        return ret;
    }

    private void action(HashMap<String, MemberSymbol> ret) {
        ClassSymbolTable cst = new ActionClassSymbolTable();
        ListSymbolTable lst = new ListSymbolTable(cst, () -> new CompoundActionLoader());
        MemberSymbol ms = new MemberSymbol(lst, "The action(s) to perform if this option is selected.");
        ret.put("action", ms);
    }

    private void weight(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new ProbabilitySupplierClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "The relative weighting of this option.");
        ret.put("weight", ms);
    }

    @Override
    public Loader getLoader() {
        return new WeightedOptionLoader();
    }
}
