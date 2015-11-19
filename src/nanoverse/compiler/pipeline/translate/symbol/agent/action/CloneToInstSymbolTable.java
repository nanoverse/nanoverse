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
import nanoverse.compiler.pipeline.instantiate.loader.agent.action.CloneToLoader;
import nanoverse.compiler.pipeline.translate.symbol.*;
import nanoverse.compiler.pipeline.translate.symbol.agent.targets.TargetRuleClassSymbolTable;
import nanoverse.compiler.pipeline.translate.symbol.primitive.booleans.BooleanClassSymbolTable;
import nanoverse.compiler.pipeline.translate.symbol.primitive.integers.IntegerClassSymbolTable;
import nanoverse.runtime.agent.action.CloneToDescriptor;

import java.util.HashMap;

/**
 * Created by dbborens on 7/22/2015.
 */
public class CloneToInstSymbolTable extends ActionInstSymbolTable<CloneToDescriptor> {
    @Override
    public String getDescription() {
        return "Places a copy or copies of the current cell at the target " +
            "site(s).";
    }

    @Override
    public HashMap<String, MemberSymbol> resolveMembers() {
        HashMap<String, MemberSymbol> ret = super.resolveMembers();
        targetHighlight(ret);
        selfHighlight(ret);
        target(ret);
        noReplacement(ret);
        return ret;
    }

    private void targetHighlight(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new IntegerClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "Highlight channel on which " +
            "to record the nanoverse.runtime.agent being triggered. If left null, no " +
            "highlight will be recorded.");
        ret.put("targetHighlight", ms);
    }

    private void selfHighlight(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new IntegerClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "Highlight channel on which " +
            "to record the triggering nanoverse.runtime.agent. If left null, no " +
            "highlight will be recorded.");
        ret.put("selfHighlight", ms);
    }

    private void target(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new TargetRuleClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "Rule(s) limiting valid " +
            "targets for this action.");
        ret.put("target", ms);
    }

    private void noReplacement(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new BooleanClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "If false, placing an nanoverse.runtime.agent " +
            "at an occupied location will replace the current nanoverse.runtime.agent.");
        ret.put("noReplacement", ms);
    }

    @Override
    public Loader getLoader() {
        return new CloneToLoader();
    }
}
