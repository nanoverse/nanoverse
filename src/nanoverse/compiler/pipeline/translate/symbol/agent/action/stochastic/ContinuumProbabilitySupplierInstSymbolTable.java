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

package nanoverse.compiler.pipeline.translate.symbol.agent.action.stochastic;

import nanoverse.compiler.pipeline.instantiate.loader.Loader;
import nanoverse.compiler.pipeline.instantiate.loader.agent.action.stochastic.ContinuumProbabilitySupplierLoader;
import nanoverse.compiler.pipeline.translate.symbol.*;
import nanoverse.compiler.pipeline.translate.symbol.primitive.doubles.DoubleClassSymbolTable;
import nanoverse.compiler.pipeline.translate.symbol.primitive.strings.StringClassSymbolTable;
import nanoverse.runtime.agent.action.stochastic.ContinuumProbabilitySupplierDescriptor;

import java.util.HashMap;

/**
 * Created by dbborens on 8/25/2015.
 */
public class ContinuumProbabilitySupplierInstSymbolTable
    extends MapSymbolTable<ContinuumProbabilitySupplierDescriptor> {

    @Override
    public String getDescription() {
        return "The weight of the action being chosen depends on the " +
            "local value of a continuum layer. Given a local concentration x," +
            "the probability will be reported as weight = offset + " +
            "coefficient * x.";
    }

    @Override
    public Loader getLoader() {
        return new ContinuumProbabilitySupplierLoader();
    }

    @Override
    protected HashMap<String, MemberSymbol> resolveMembers() {
        HashMap<String, MemberSymbol> ret = super.resolveMembers();
        offset(ret);
        coefficient(ret);
        layer(ret);
        return ret;
    }

    private void layer(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new StringClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "The layer whose value should be used.");
        ret.put("layer", ms);
    }

    private void coefficient(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new DoubleClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "The factor by which to " +
            "multiply the local field value. See class description.");
        ret.put("coefficient", ms);
    }

    private void offset(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new DoubleClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "The weight given that the " +
            "local value x=0. See class description.");
        ret.put("offset", ms);
    }
}
