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

package compiler.pipeline.translate.symbol.tables.agent.action.stochastic;

import agent.action.stochastic.WeightedOption;
import compiler.pipeline.translate.symbol.symbols.MemberSymbol;
import compiler.pipeline.translate.symbol.tables.*;
import compiler.pipeline.translate.symbol.tables.agent.action.ActionClassSymbolTable;
import compiler.pipeline.translate.symbol.tables.primitive.doubles.DoubleClassSymbolTable;

import java.util.HashMap;

/**
 * Created by dbborens on 7/23/2015.
 */
public class WeightedOptionInstSymbolTable extends MapSymbolTable<WeightedOption> {

    @Override
    public String getDescription() {
        return "A weighted option";
    }

    @Override
    protected HashMap<String, MemberSymbol> resolveMembers() {
        HashMap<String, MemberSymbol> ret = super.resolveMembers();
        weight(ret);
        action(ret);
        return ret;
    }

    private void action(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new ActionClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "The action to perform if this option is selected.");
        ret.put("action", ms);
    }

    private void weight(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new DoubleClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "The relative weighting of this option.");
        ret.put("weight", ms);
    }
}
