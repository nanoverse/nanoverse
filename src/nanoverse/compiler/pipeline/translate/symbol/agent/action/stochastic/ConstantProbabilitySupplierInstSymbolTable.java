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

package nanoverse.compiler.pipeline.translate.symbol.agent.action.stochastic;

import nanoverse.compiler.pipeline.instantiate.loader.Loader;
import nanoverse.compiler.pipeline.instantiate.loader.agent.action.stochastic.ConstantProbabilitySupplierLoader;
import nanoverse.compiler.pipeline.translate.symbol.*;
import nanoverse.compiler.pipeline.translate.symbol.primitive.doubles.DoubleClassSymbolTable;
import nanoverse.runtime.agent.action.stochastic.ConstantProbabilitySupplierDescriptor;

import java.util.HashMap;

/**
 * Created by dbborens on 8/25/2015.
 */
public class ConstantProbabilitySupplierInstSymbolTable extends MapSymbolTable<ConstantProbabilitySupplierDescriptor> {

    @Override
    public Loader getLoader() {
        return new ConstantProbabilitySupplierLoader();
    }

    @Override
    public String getDescription() {
        return "The weight of choosing the action is fixed.";
    }

    @Override
    protected HashMap<String, MemberSymbol> resolveMembers() {
        HashMap<String, MemberSymbol> ret = super.resolveMembers();
        value(ret);
        return ret;
    }

    private void value(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new DoubleClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "The weight value to report.");
        ret.put("value", ms);
    }
}
