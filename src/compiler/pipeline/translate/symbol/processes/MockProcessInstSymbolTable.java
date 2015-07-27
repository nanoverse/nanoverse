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

package compiler.pipeline.translate.symbol.processes;

import compiler.pipeline.translate.symbol.MemberSymbol;
import compiler.pipeline.translate.symbol.ResolvingSymbolTable;
import compiler.pipeline.translate.symbol.primitive.doubles.DoubleClassSymbolTable;
import compiler.pipeline.translate.symbol.primitive.integers.IntegerClassSymbolTable;
import compiler.pipeline.translate.symbol.primitive.strings.StringClassSymbolTable;
import processes.MockProcess;

import java.util.HashMap;

/**
 * Created by dbborens on 7/21/2015.
 */
public class MockProcessInstSymbolTable extends ProcessInstSymbolTable<MockProcess> {
    @Override
    public String getDescription() {
        return "LEGACY: Mock process, used in legacy tests.";
    }

    @Override
    protected HashMap<String, MemberSymbol> resolveMembers() {
        HashMap<String, MemberSymbol> ret = super.resolveMembers();
        identifier(ret);
        weight(ret);
        count(ret);
        return ret;
    }

    private void count(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new IntegerClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "Arbitrary counter field, used for testing.");
        ret.put("count", ms);
    }

    private void weight(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new DoubleClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "LEGACY: Totally obsolete -- set to anything and ignore.");
        ret.put("weight", ms);

    }

    private void identifier(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new StringClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "Arbitrary identifier, used for testing.");
        ret.put("identifier", ms);
    }
}
