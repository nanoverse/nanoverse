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

import nanoverse.compiler.pipeline.translate.symbol.*;
import nanoverse.compiler.pipeline.translate.symbol.geometry.set.CoordinateSetClassSymbolTable;
import nanoverse.compiler.pipeline.translate.symbol.primitive.integers.IntegerClassSymbolTable;
import nanoverse.compiler.pipeline.translate.symbol.processes.ProcessInstSymbolTable;
import nanoverse.runtime.processes.discrete.AgentProcess;

import java.util.HashMap;

/**
 * Created by dbborens on 7/21/2015.
 */
public abstract class DiscreteProcessInstSymbolTable<T extends AgentProcess>
    extends ProcessInstSymbolTable<T> {

    @Override
    public HashMap<String, MemberSymbol> resolveMembers() {
        HashMap<String, MemberSymbol> ret = super.resolveMembers();
        activeSites(ret);
        maxTargets(ret);
        return (ret);
    }

    private void maxTargets(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new IntegerClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "The maximum number of " +
            "locations to be affected by each occurrence of this " +
            "process. Setting maxTargets to -1 results in an unlimited " +
            "number of targets.");
        ret.put("maxTargets", ms);
    }

    private void activeSites(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new CoordinateSetClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "The set of sites that are " +
            "candidates for being affected by this Process.");
        ret.put("activeSites", ms);
    }
}
