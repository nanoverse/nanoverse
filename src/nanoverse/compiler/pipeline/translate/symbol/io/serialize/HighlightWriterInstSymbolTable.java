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

package nanoverse.compiler.pipeline.translate.symbol.io.serialize;

import nanoverse.compiler.pipeline.instantiate.loader.Loader;
import nanoverse.compiler.pipeline.instantiate.loader.io.serialize.binary.HighlightWriterLoader;
import nanoverse.compiler.pipeline.instantiate.loader.io.visual.highlight.IntegerStreamLoader;
import nanoverse.compiler.pipeline.translate.symbol.*;
import nanoverse.compiler.pipeline.translate.symbol.primitive.integers.IntegerClassSymbolTable;
import nanoverse.runtime.control.arguments.IntegerArgument;
import nanoverse.runtime.io.serialize.binary.HighlightWriter;

import java.util.HashMap;

/**
 * Created by dbborens on 7/26/2015.
 */
public class HighlightWriterInstSymbolTable extends MapSymbolTable<HighlightWriter> {
    @Override
    public String getDescription() {
        return "Highlights can be used to flag particular coordinates for " +
            "special accents in visualizations. HighlightWriter produces " +
            "files containing yes-or-no values for each location at each " +
            "recorded time in a simulation. The writer produces one file " +
            "per specified channel per instance.";
    }

    @Override
    public HashMap<String, MemberSymbol> resolveMembers() {
        HashMap<String, MemberSymbol> ret = super.resolveMembers();
        channels(ret);
        return ret;
    }

    private void channels(HashMap<String, MemberSymbol> ret) {
        ClassSymbolTable cst = new IntegerClassSymbolTable();
        ListSymbolTable<IntegerArgument> lst = new ListSymbolTable<>(cst, IntegerStreamLoader::new);
        MemberSymbol ms = new MemberSymbol(lst, "List of channels whose " +
            "state is to be monitored and recorded.");
        ret.put("channels", ms);
    }

    @Override
    public Loader getLoader() {
        return new HighlightWriterLoader();
    }
}
