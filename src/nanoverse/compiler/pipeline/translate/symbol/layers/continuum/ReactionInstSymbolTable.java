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

package nanoverse.compiler.pipeline.translate.symbol.layers.continuum;

import nanoverse.compiler.pipeline.instantiate.loader.Loader;
import nanoverse.compiler.pipeline.instantiate.loader.layers.continuum.ReactionLoader;
import nanoverse.compiler.pipeline.translate.symbol.*;
import nanoverse.compiler.pipeline.translate.symbol.primitive.doubles.DoubleClassSymbolTable;
import nanoverse.compiler.pipeline.translate.symbol.primitive.strings.StringClassSymbolTable;
import nanoverse.runtime.layers.continuum.Reaction;

import java.util.HashMap;


/**
 * Created by dbborens on 8/25/2015.
 */
public class ReactionInstSymbolTable extends MapSymbolTable<Reaction> {

    @Override
    public Loader getLoader() {
        return new ReactionLoader();
    }

    @Override
    public String getDescription() {
        return "Agents can interact with the local value of a continuum. " +
            "Reactions specify the terms of those interactions, allowing " +
            "both constant increase or decrease (injection) and proportional " +
            "scaling (exponentiation).";
    }

    @Override
    protected HashMap<String, MemberSymbol> resolveMembers() {
        HashMap<String, MemberSymbol> ret = super.resolveMembers();
        inj(ret);
        exp(ret);
        layer(ret);
        return ret;
    }

    private void layer(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new StringClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "The name of the continuum " +
            "layer with which to react.");
        ret.put("layer", ms);
    }

    private void exp(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new DoubleClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "Amount by which to scale " +
            "the local value of the continuum field. Over many iterations, " +
            "this leads to exponential growth, hence the name.");
        ret.put("exp", ms);
    }

    private void inj(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new DoubleClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "Constant amount by which to " +
            "increase local continuum value.");
        ret.put("inj", ms);
    }
}
