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

package nanoverse.compiler.pipeline.translate.symbol.geometry.set;

import nanoverse.compiler.pipeline.instantiate.loader.Loader;
import nanoverse.compiler.pipeline.instantiate.loader.geometry.set.HLineCoordinateSetLoader;
import nanoverse.compiler.pipeline.translate.symbol.*;
import nanoverse.compiler.pipeline.translate.symbol.control.identifiers.CoordinateClassSymbolTable;
import nanoverse.compiler.pipeline.translate.symbol.primitive.integers.IntegerClassSymbolTable;
import nanoverse.runtime.geometry.set.HorizontalLineSet;

import java.util.HashMap;

/**
 * Created by dbborens on 7/25/2015.
 */
public class HLineCoordSetInstSymbolTable extends MapSymbolTable<HorizontalLineSet> {
    @Override
    public String getDescription() {
        return "A coordinate set consisting of a horizontal line with a specified length and origin.";
    }

    @Override
    public HashMap<String, MemberSymbol> resolveMembers() {
        HashMap<String, MemberSymbol> ret = super.resolveMembers();
        origin(ret);
        length(ret);
        return ret;
    }

    private void origin(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new CoordinateClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "The origin of the disc (specified as a distance from the lower-left front corner).");
        ret.put("origin", ms);
    }

    private void length(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new IntegerClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "The length of the disc, where length=1 is a single point.");
        ret.put("length", ms);
    }

    @Override
    public Loader getLoader() {
        return new HLineCoordinateSetLoader();
    }
}
