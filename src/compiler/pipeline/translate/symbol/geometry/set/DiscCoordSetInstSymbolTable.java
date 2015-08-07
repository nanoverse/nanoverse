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

package compiler.pipeline.translate.symbol.geometry.set;

import compiler.pipeline.instantiate.loader.Loader;
import compiler.pipeline.translate.nodes.ObjectNode;
import compiler.pipeline.translate.symbol.*;
import compiler.pipeline.translate.symbol.control.identifiers.CoordinateClassSymbolTable;
import compiler.pipeline.translate.symbol.primitive.integers.IntegerClassSymbolTable;
import geometry.set.DiscSet;

import java.util.HashMap;

/**
 * Created by dbborens on 7/25/2015.
 */
public class DiscCoordSetInstSymbolTable extends MapSymbolTable<DiscSet> {
    @Override
    public String getDescription() {
        return "A coordinate set consisting of a disc with a specified radius and origin.";
    }

    @Override
    protected HashMap<String, MemberSymbol> resolveMembers() {
        HashMap<String, MemberSymbol> ret = super.resolveMembers();
        origin(ret);
        radius(ret);
        return ret;
    }

    private void origin(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new CoordinateClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "The origin of the disc (specified as a distance from the lower-left front corner).");
        ret.put("origin", ms);
    }

    private void radius(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new IntegerClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "The radius of the disc, where radius=0 is a single point.");
        ret.put("radius", ms);
    }

    @Override
    public Loader getLoader() {
        return null;
    }
}
