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

package compiler.pipeline.translate.symbol.processes.discrete;

import compiler.pipeline.instantiate.Loader;
import compiler.pipeline.translate.nodes.ObjectNode;
import compiler.pipeline.translate.symbol.MemberSymbol;
import compiler.pipeline.translate.symbol.ResolvingSymbolTable;
import compiler.pipeline.translate.symbol.agent.AgentDescriptorClassSymbolTable;
import compiler.pipeline.translate.symbol.primitive.booleans.BooleanClassSymbolTable;
import processes.discrete.Fill;

import java.util.HashMap;

/**
 * Created by dbborens on 7/21/2015.
 */
public class FillInstSymbolTable extends DiscreteProcessInstSymbolTable<Fill> {
    @Override
    public String getDescription() {
        return "Fill in a region with agents.";
    }

    @Override
    protected HashMap<String, MemberSymbol> resolveMembers() {
        HashMap<String, MemberSymbol> ret = super.resolveMembers();
        description(ret);
        skipFilledSites(ret);
        return ret;
    }

    private void skipFilledSites(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new BooleanClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "If true, occupied sites are " +
                "ignored during fill operation. If false, filling over an " +
                "occupied site will result in an error.");
        ret.put("skipFilledSites", ms);
    }

    private void description(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new AgentDescriptorClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "A template for the agents to be scattered by this process.");
        ret.put("description", ms);
    }

    @Override
    public Loader getLoader(ObjectNode node) {
        return null;
    }
}
