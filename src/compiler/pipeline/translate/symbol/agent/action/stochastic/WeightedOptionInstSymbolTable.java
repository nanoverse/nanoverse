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

package compiler.pipeline.translate.symbol.agent.action.stochastic;

import agent.action.stochastic.WeightedOption;
import compiler.pipeline.instantiate.loader.Loader;
import compiler.pipeline.instantiate.loader.agent.action.stochastic.WeightedOptionLoader;
import compiler.pipeline.translate.nodes.ObjectNode;
import compiler.pipeline.translate.symbol.*;
import compiler.pipeline.translate.symbol.agent.action.ActionClassSymbolTable;
import compiler.pipeline.translate.symbol.primitive.doubles.DoubleClassSymbolTable;

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

    @Override
    public Loader getLoader() {
        return new WeightedOptionLoader();
    }
}
