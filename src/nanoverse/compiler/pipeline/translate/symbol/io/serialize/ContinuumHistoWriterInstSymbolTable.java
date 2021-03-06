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
import nanoverse.compiler.pipeline.instantiate.loader.io.serialize.text.ContinuumHistoWriterLoader;
import nanoverse.compiler.pipeline.translate.symbol.*;
import nanoverse.compiler.pipeline.translate.symbol.primitive.booleans.BooleanClassSymbolTable;
import nanoverse.compiler.pipeline.translate.symbol.primitive.strings.StringClassSymbolTable;
import nanoverse.runtime.io.serialize.text.ContinuumHistoWriter;

import java.util.HashMap;

/**
 * Created by dbborens on 7/26/2015.
 */
public class ContinuumHistoWriterInstSymbolTable extends MapSymbolTable<ContinuumHistoWriter> {
    @Override
    public String getDescription() {
        return "ContinuumHistoWriter provides a histogram of solute " +
            "concentrations at each time point recorded in each instance " +
            "of the simulation. Data are reported in a tab-delimited " +
            "text file, with one text file per simulation instance.";
    }

    @Override
    public HashMap<String, MemberSymbol> resolveMembers() {
        HashMap<String, MemberSymbol> ret = super.resolveMembers();
        layer(ret);
        occupied(ret);
        return ret;
    }

    private void layer(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new StringClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "The name of the continuum layer on which to report.");
        ret.put("layer", ms);
    }

    private void occupied(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new BooleanClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "If true, only sites that " +
            "are occupied on the cell layer will be included in the " +
            "histogram; if false, all sites are included.");
        ret.put("occupied", ms);
    }

    @Override
    public Loader getLoader() {
        return new ContinuumHistoWriterLoader();
    }
}
