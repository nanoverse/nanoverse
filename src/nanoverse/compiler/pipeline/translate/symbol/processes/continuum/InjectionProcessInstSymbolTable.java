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
import nanoverse.compiler.pipeline.instantiate.loader.processes.continuum.InjectionProcessLoader;
import nanoverse.compiler.pipeline.translate.symbol.*;
import nanoverse.compiler.pipeline.translate.symbol.geometry.set.CoordinateSetClassSymbolTable;
import nanoverse.compiler.pipeline.translate.symbol.primitive.doubles.DoubleClassSymbolTable;
import nanoverse.compiler.pipeline.translate.symbol.primitive.strings.StringClassSymbolTable;
import nanoverse.runtime.processes.continuum.InjectionProcess;

import java.util.HashMap;

/**
 * Created by dbborens on 7/21/2015.
 */
public class InjectionProcessInstSymbolTable extends ContinuumProcessInstSymbolTable<InjectionProcess> {

    @Override
    public String getDescription() {
        return "Schedule a fixed-value source (injection) at a site or sites " +
            "of a specified continuum layer.";
    }

    @Override
    protected HashMap<String, MemberSymbol> resolveMembers() {
        HashMap<String, MemberSymbol> ret = super.resolveMembers();
        value(ret);
        layer(ret);
        activeSites(ret);
        return ret;
    }

    private void activeSites(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new CoordinateSetClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "Sites that this process should update.");
        ret.put("activeSites", ms);

    }

    private void layer(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new StringClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "Continuum layer upon which to schedule injection process.");
        ret.put("layer", ms);
    }

    private void value(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new DoubleClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "Value to inject at each affected site.");
        ret.put("value", ms);
    }

    @Override
    public Loader getLoader() {
        return new InjectionProcessLoader();
    }
}
