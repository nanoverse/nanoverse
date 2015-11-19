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

package nanoverse.compiler.pipeline.translate.symbol.processes.discrete;

import nanoverse.compiler.pipeline.instantiate.loader.Loader;
import nanoverse.compiler.pipeline.instantiate.loader.processes.discrete.FillLoader;
import nanoverse.compiler.pipeline.translate.symbol.*;
import nanoverse.compiler.pipeline.translate.symbol.agent.AgentDescriptorClassSymbolTable;
import nanoverse.compiler.pipeline.translate.symbol.primitive.booleans.BooleanClassSymbolTable;
import nanoverse.runtime.processes.discrete.Fill;

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
    public HashMap<String, MemberSymbol> resolveMembers() {
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
        ret.put("skipFilled", ms);
    }

    private void description(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new AgentDescriptorClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "A template for the agents to be scattered by this process.");
        ret.put("description", ms);
    }

    @Override
    public Loader getLoader() {
        return new FillLoader();
    }
}
