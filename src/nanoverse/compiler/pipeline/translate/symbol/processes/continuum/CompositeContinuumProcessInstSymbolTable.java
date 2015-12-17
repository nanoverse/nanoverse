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

package nanoverse.compiler.pipeline.translate.symbol.processes.continuum;

import nanoverse.compiler.pipeline.instantiate.loader.Loader;
import nanoverse.compiler.pipeline.instantiate.loader.processes.continuum.CompositeContinuumProcessLoader;
import nanoverse.compiler.pipeline.translate.symbol.*;
import nanoverse.compiler.pipeline.translate.symbol.primitive.strings.StringClassSymbolTable;
import nanoverse.compiler.pipeline.translate.symbol.processes.ProcessClassSymbolTable;
import nanoverse.runtime.processes.continuum.CompositeContinuumProcess;

import java.util.HashMap;

/**
 * Created by dbborens on 12/17/2015.
 */
public class CompositeContinuumProcessInstSymbolTable extends ContinuumProcessInstSymbolTable<CompositeContinuumProcess> {
    @Override
    public String getDescription() {
        return "Perform a series of transformations to the specified " +
            "continuum layer. Transformations are superposed and then " +
            "solved all at once.";
    }

    @Override
    public HashMap<String, MemberSymbol> resolveMembers() {
        HashMap<String, MemberSymbol> ret = super.resolveMembers();
        layer(ret);
        children(ret);
        return ret;
    }

    private void children(HashMap<String, MemberSymbol> ret) {
        ClassSymbolTable rst = new ProcessClassSymbolTable();
        ListSymbolTable lst = new ListSymbolTable(rst, null);
        MemberSymbol ms = new MemberSymbol(lst, "List of transformations to be applied.");
        ret.put("children", ms);

    }

    private void layer(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new StringClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "Continuum layer to be transformed.");
        ret.put("layer", ms);
    }

    @Override
    public Loader getLoader() {
        return new CompositeContinuumProcessLoader();
    }
}
