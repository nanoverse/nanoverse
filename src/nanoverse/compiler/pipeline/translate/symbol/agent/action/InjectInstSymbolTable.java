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
import nanoverse.compiler.pipeline.instantiate.loader.agent.action.InjectLoader;
import nanoverse.compiler.pipeline.translate.symbol.*;
import nanoverse.compiler.pipeline.translate.symbol.primitive.doubles.DoubleClassSymbolTable;
import nanoverse.compiler.pipeline.translate.symbol.primitive.strings.StringClassSymbolTable;
import nanoverse.runtime.agent.action.InjectDescriptor;

import java.util.HashMap;

/**
 * Created by dbborens on 7/22/2015.
 */
public class InjectInstSymbolTable extends ActionInstSymbolTable<InjectDescriptor> {
    @Override
    public String getDescription() {
        return "Adjust the value of a continuum layer by a specified amount at this nanoverse.runtime.agent's location.";
    }

    @Override
    protected HashMap<String, MemberSymbol> resolveMembers() {
        HashMap<String, MemberSymbol> ret = super.resolveMembers();
        delta(ret);
        layer(ret);
        return ret;
    }

    private void layer(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new StringClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "The ID of the layer on which to perform the operation.");
        ret.put("layer", ms);
    }

    private void delta(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new DoubleClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "The amount by which to adjust the local value of the continuum.");
        ret.put("delta", ms);
    }

    @Override
    public Loader getLoader() {
        return new InjectLoader();
    }
}
