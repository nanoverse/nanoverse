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

package nanoverse.compiler.pipeline.translate.symbol.processes;

import nanoverse.compiler.pipeline.translate.symbol.*;
import nanoverse.compiler.pipeline.translate.symbol.primitive.integers.IntegerClassSymbolTable;
import nanoverse.runtime.processes.NanoverseProcess;

import java.util.HashMap;

/**
 * Created by dbborens on 7/21/2015.
 */
public abstract class ProcessInstSymbolTable<T extends NanoverseProcess> extends MapSymbolTable<T> {

    public HashMap<String, MemberSymbol> resolveMembers() {
        HashMap<String, MemberSymbol> ret = super.resolveMembers();
        period(ret);
        start(ret);
        return ret;
    }

    private void start(HashMap<String, MemberSymbol> ret) {
        ClassSymbolTable st = new IntegerClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(st, "Time at which to begin " +
            "executing this process (specified in integration cycles).");
        ret.put("start", ms);
    }

    private void period(HashMap<String, MemberSymbol> ret) {
        ClassSymbolTable st = new IntegerClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(st, "Waiting period between " +
            "occurrences of this process. period=0 means do only once.");
        ret.put("period", ms);
    }


}
