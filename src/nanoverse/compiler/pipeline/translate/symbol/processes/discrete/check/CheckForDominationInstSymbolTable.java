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

package nanoverse.compiler.pipeline.translate.symbol.processes.discrete.check;

import nanoverse.compiler.pipeline.instantiate.loader.Loader;
import nanoverse.compiler.pipeline.instantiate.loader.processes.discrete.check.CheckForDominationLoader;
import nanoverse.compiler.pipeline.translate.symbol.*;
import nanoverse.compiler.pipeline.translate.symbol.primitive.doubles.DoubleClassSymbolTable;
import nanoverse.compiler.pipeline.translate.symbol.primitive.integers.IntegerClassSymbolTable;
import nanoverse.compiler.pipeline.translate.symbol.processes.discrete.DiscreteProcessInstSymbolTable;
import nanoverse.runtime.processes.discrete.check.CheckForDomination;

import java.util.HashMap;

/**
 * Created by dbborens on 7/21/2015.
 */
public class CheckForDominationInstSymbolTable extends DiscreteProcessInstSymbolTable<CheckForDomination> {
    @Override
    public String getDescription() {
        return "Halt the simulation when the target cell type has the " +
            "specified fraction of the overall live cell population.";
    }

    @Override
    protected HashMap<String, MemberSymbol> resolveMembers() {
        HashMap<String, MemberSymbol> ret = super.resolveMembers();
        threshold(ret);
        target(ret);
        return ret;
    }

    private void threshold(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new DoubleClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "Maximum occupancy before halt is triggered.");
        ret.put("threshold", ms);
    }

    private void target(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new IntegerClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "Agent class to check for domination.");
        ret.put("target", ms);
    }

    @Override
    public Loader getLoader() {
        return new CheckForDominationLoader();
    }
}
