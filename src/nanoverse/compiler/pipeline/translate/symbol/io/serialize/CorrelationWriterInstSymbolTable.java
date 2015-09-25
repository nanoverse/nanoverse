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

package nanoverse.compiler.pipeline.translate.symbol.io.serialize;

import nanoverse.compiler.pipeline.instantiate.loader.Loader;
import nanoverse.compiler.pipeline.instantiate.loader.io.serialize.text.CorrelationWriterLoader;
import nanoverse.compiler.pipeline.translate.symbol.*;
import nanoverse.compiler.pipeline.translate.symbol.primitive.integers.IntegerClassSymbolTable;
import nanoverse.compiler.pipeline.translate.symbol.primitive.strings.StringClassSymbolTable;
import nanoverse.runtime.io.serialize.text.CorrelationWriter;

import java.util.HashMap;

/**
 * Created by dbborens on 7/26/2015.
 */
public class CorrelationWriterInstSymbolTable extends MapSymbolTable<CorrelationWriter> {
    @Override
    public String getDescription() {
        return "Calculate the pairwise correlation between individuals at " +
                "every distance. This calculation is used to measure spatial " +
                "structure. The results are measured at a specified time " +
                "point, and aggregated over all simulations.";
    }

    @Override
    protected HashMap<String, MemberSymbol> resolveMembers() {
        HashMap<String, MemberSymbol> ret = super.resolveMembers();
        filename(ret);
        time(ret);
        return ret;
    }

    private void time(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new IntegerClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "The time (in number of " +
                "integration cycles) at which the correlation should be " +
                "calculated. Specify one CorrelationWriter for each time " +
                "point at which a measurement is desired.");
        ret.put("time", ms);
    }

    private void filename(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new StringClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "The filename for the report.");
        ret.put("filename", ms);
    }

    @Override
    public Loader getLoader() {
        return new CorrelationWriterLoader();
    }
}
