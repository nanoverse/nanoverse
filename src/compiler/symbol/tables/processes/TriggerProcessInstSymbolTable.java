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

package compiler.symbol.tables.processes;

import compiler.symbol.symbols.MemberSymbol;
import compiler.symbol.tables.ResolvingSymbolTable;
import compiler.symbol.tables.primitive.booleans.BooleanClassSymbolTable;
import compiler.symbol.tables.primitive.strings.StringClassSymbolTable;
import compiler.symbol.tables.processes.discrete.DiscreteProcessInstSymbolTable;
import processes.discrete.TriggerProcess;

import java.util.HashMap;

/**
 * Created by dbborens on 7/21/2015.
 */
public class TriggerProcessInstSymbolTable extends DiscreteProcessInstSymbolTable<TriggerProcess> {
    @Override
    public String getDescription() {
        return "Trigger agents to perform a specified Action.";
    }

    @Override
    protected HashMap<String, MemberSymbol> resolveMembers() {
        HashMap<String, MemberSymbol> ret = super.resolveMembers();
        behavior(ret);
        skipVacantSites(ret);
        requireNeighbors(ret);
        return ret;
    }

    private void requireNeighbors(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new BooleanClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "LEGACY: Require that all " +
                "potential targets have non-vacant neighbors. Use a filter " +
                "instead.");
        ret.put("requireNeighbors", ms);
    }

    private void skipVacantSites(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new BooleanClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "LEGACY: Require that all " +
                "potential targets be occupied. Use a filter instead.");
        ret.put("skipVacantSites", ms);
    }

    private void behavior(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new StringClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "Name of behavior to " +
                "trigger. It is assumed that all potential targets have a " +
                "behavior with this name. If they do not, you will get an " +
                "error!");
        ret.put("behavior", ms);
    }
}
