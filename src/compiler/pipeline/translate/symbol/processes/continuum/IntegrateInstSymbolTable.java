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

package compiler.pipeline.translate.symbol.processes.continuum;

import compiler.pipeline.instantiate.loader.Loader;
import compiler.pipeline.instantiate.loader.processes.continuum.IntegrateLoader;
import compiler.pipeline.translate.nodes.ObjectNode;
import compiler.pipeline.translate.symbol.*;
import compiler.pipeline.translate.symbol.primitive.strings.StringClassSymbolTable;
import processes.continuum.Integrate;

import java.util.HashMap;

/**
 * Created by dbborens on 7/21/2015.
 */
public class IntegrateInstSymbolTable extends ContinuumProcessInstSymbolTable<Integrate> {

    @Override
    public String getDescription() {
        return "Update the state of the specified continuum layer.";
    }

    @Override
    protected HashMap<String, MemberSymbol> resolveMembers() {
        HashMap<String, MemberSymbol> ret = super.resolveMembers();
        layer(ret);
        return ret;
    }

    private void layer(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new StringClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "Continuum layer to integrate forward.");
        ret.put("layer", ms);
    }

    @Override
    public Loader getLoader() {
        return new IntegrateLoader();
    }
}
