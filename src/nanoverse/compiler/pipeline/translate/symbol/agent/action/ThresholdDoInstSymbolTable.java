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

package nanoverse.compiler.pipeline.translate.symbol.agent.action;

import nanoverse.compiler.pipeline.instantiate.loader.Loader;
import nanoverse.compiler.pipeline.instantiate.loader.agent.action.ThresholdDoLoader;
import nanoverse.compiler.pipeline.translate.symbol.*;
import nanoverse.compiler.pipeline.translate.symbol.primitive.doubles.DoubleClassSymbolTable;
import nanoverse.compiler.pipeline.translate.symbol.primitive.strings.StringClassSymbolTable;
import nanoverse.runtime.agent.action.ThresholdDoDescriptor;

import java.util.HashMap;

/**
 * Created by dbborens on 7/22/2015.
 */
public class ThresholdDoInstSymbolTable extends ActionInstSymbolTable<ThresholdDoDescriptor> {
    @Override
    public String getDescription() {
        return "Perform a particular action if a lattice concentration " +
            "falls within a particular range.";
    }

    @Override
    public HashMap<String, MemberSymbol> resolveMembers() {
        HashMap<String, MemberSymbol> ret = super.resolveMembers();
        minimum(ret);
        maximum(ret);
        layer(ret);
        action(ret);
        return ret;
    }

    private void action(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new ActionClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "The action to be performed if the condition is met.");
        ret.put("action", ms);
    }

    private void layer(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new StringClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "The ID of the layer whose value is to be used.");
        ret.put("layer", ms);
    }

    private void maximum(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new DoubleClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "Inclusive maximum value for affirmative decision.");
        ret.put("maximum", ms);
    }

    private void minimum(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new DoubleClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "Inclusive minimum value for affirmative decision.");
        ret.put("minimum", ms);
    }

    @Override
    public Loader getLoader() {
        return new ThresholdDoLoader();
    }
}
