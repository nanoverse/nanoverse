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

package nanoverse.compiler.pipeline.translate.symbol.processes.temporal;

import nanoverse.compiler.pipeline.instantiate.loader.Loader;
import nanoverse.compiler.pipeline.instantiate.loader.processes.temporal.TickLoader;
import nanoverse.compiler.pipeline.translate.symbol.*;
import nanoverse.compiler.pipeline.translate.symbol.primitive.doubles.DoubleClassSymbolTable;
import nanoverse.compiler.pipeline.translate.symbol.processes.ProcessInstSymbolTable;
import nanoverse.runtime.processes.temporal.Tick;

import java.util.HashMap;

/**
 * Created by dbborens on 7/21/2015.
 */
public class TickInstSymbolTable extends ProcessInstSymbolTable<Tick> {
    @Override
    public String getDescription() {
        return "Advance the simulation clock by the specified dt.";
    }

    @Override
    protected HashMap<String, MemberSymbol> resolveMembers() {
        HashMap<String, MemberSymbol> ret = super.resolveMembers();
        dt(ret);
        return ret;
    }

    private void dt(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new DoubleClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "Interval by which to advance simulation time.");
        ret.put("dt", ms);
    }

    @Override
    public Loader getLoader() {
        return new TickLoader();
    }
}
