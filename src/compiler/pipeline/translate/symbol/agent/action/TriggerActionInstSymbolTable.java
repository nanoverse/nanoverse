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

package compiler.pipeline.translate.symbol.agent.action;

import agent.action.*;
import compiler.pipeline.instantiate.loader.Loader;
import compiler.pipeline.instantiate.loader.agent.action.TriggerActionLoader;
import compiler.pipeline.translate.symbol.MemberSymbol;
import compiler.pipeline.translate.symbol.ResolvingSymbolTable;
import compiler.pipeline.translate.symbol.agent.targets.TargetRuleClassSymbolTable;
import compiler.pipeline.translate.symbol.primitive.integers.IntegerClassSymbolTable;
import compiler.pipeline.translate.symbol.primitive.strings.StringClassSymbolTable;

import java.util.HashMap;

/**
 * Created by dbborens on 7/22/2015.
 */
public class TriggerActionInstSymbolTable extends ActionInstSymbolTable<TriggerDescriptor> {

    @Override
    public String getDescription() {
        return "Trigger an agent to perform a specified behavior.";
    }

    @Override
    protected HashMap<String, MemberSymbol> resolveMembers() {
        HashMap<String, MemberSymbol> ret = super.resolveMembers();
        target(ret);
        behavior(ret);
        selfHighlight(ret);
        targetHighlight(ret);
        return ret;
    }

    private void targetHighlight(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new IntegerClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "Highlight channel on which " +
                "to record the agent being triggered. If left null, no " +
                "highlight will be recorded.");
        ret.put("targetHighlight", ms);
    }

    private void selfHighlight(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new IntegerClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "Highlight channel on which " +
                "to record the triggering agent. If left null, no " +
                "highlight will be recorded.");
        ret.put("selfHighlight", ms);

    }

    private void behavior(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new StringClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "The name of the behavior to " +
                "trigger. It is assumed that the target has a behavior of " +
                "this name.");
        ret.put("behavior", ms);
    }

    private void target(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new TargetRuleClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "Rule(s) limiting valid " +
                "targets for this action.");
        ret.put("target", ms);
    }

    @Override
    public Loader getLoader() {
        return new TriggerActionLoader();
    }
}
