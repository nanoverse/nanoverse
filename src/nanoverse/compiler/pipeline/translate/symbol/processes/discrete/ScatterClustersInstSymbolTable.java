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
import nanoverse.compiler.pipeline.instantiate.loader.processes.discrete.ScatterClustersLoader;
import nanoverse.compiler.pipeline.translate.symbol.*;
import nanoverse.compiler.pipeline.translate.symbol.agent.AgentDescriptorClassSymbolTable;
import nanoverse.compiler.pipeline.translate.symbol.primitive.integers.IntegerClassSymbolTable;
import nanoverse.compiler.pipeline.translate.symbol.processes.discrete.cluster.ScatterClustersHelperClassSymbolTable;
import nanoverse.runtime.processes.discrete.ScatterClusters;

import java.util.HashMap;

/**
 * Created by dbborens on 7/21/2015.
 */
public class ScatterClustersInstSymbolTable extends DiscreteProcessInstSymbolTable<ScatterClusters> {

    @Override
    public String getDescription() {
        return "Scatter agents in clusters of a specific size.";
    }

    @Override
    public HashMap<String, MemberSymbol> resolveMembers() {
        HashMap<String, MemberSymbol> ret = super.resolveMembers();
        description(ret);
        separation(ret);
        neighbors(ret);
        return ret;
    }

    private void neighbors(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new IntegerClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "Minimum number of neighbors for each nanoverse.runtime.agent.");
        ret.put("neighbors", ms);
    }

    private void separation(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new ScatterClustersHelperClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "Separation rule for clusters.");
        ret.put("separation", ms);
    }

    private void description(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new AgentDescriptorClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "A template for the agents to be scattered by this process.");
        ret.put("description", ms);
    }

    @Override
    public Loader getLoader() {
        return new ScatterClustersLoader();
    }
}
