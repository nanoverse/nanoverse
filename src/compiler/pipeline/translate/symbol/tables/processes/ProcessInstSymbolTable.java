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

package compiler.pipeline.translate.symbol.tables.processes;

import compiler.pipeline.translate.symbol.symbols.MemberSymbol;
import compiler.pipeline.translate.symbol.tables.*;
import compiler.pipeline.translate.symbol.tables.primitive.integers.IntegerClassSymbolTable;
import processes.NanoverseProcess;

import java.util.HashMap;

/**
 * Created by dbborens on 7/21/2015.
 */
public abstract class ProcessInstSymbolTable<T extends NanoverseProcess> extends MapSymbolTable<T> {

    protected HashMap<String, MemberSymbol> resolveMembers() {
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
