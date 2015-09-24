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

package compiler.pipeline.translate.symbol.io.serialize;

import compiler.pipeline.instantiate.loader.Loader;
import compiler.pipeline.instantiate.loader.io.serialize.text.InterfaceCensusWriterLoader;
import compiler.pipeline.translate.symbol.*;
import compiler.pipeline.translate.symbol.primitive.integers.IntegerClassSymbolTable;
import io.serialize.text.InterfaceCensusWriter;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.HashMap;

/**
 * Created by dbborens on 8/21/2015.
 */
public class InterfaceCensusWriterInstSymbolTable extends MapSymbolTable<InterfaceCensusWriter> {

    @Override
    public Loader getLoader() {
        return new InterfaceCensusWriterLoader();
    }

    @Override
    public String getDescription() {
        return "Reports the populations of neighbors, by type, along the " +
                "population boundary of a particular cell type.";
    }

    @Override
    protected HashMap<String, MemberSymbol> resolveMembers() {
        HashMap<String, MemberSymbol> ret = super.resolveMembers();
        focalState(ret);
        return ret;
    }

    private void focalState(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new IntegerClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "The focal state for the " +
                "interface census.");
        ret.put("focalState", ms);
    }
}
