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

package nanoverse.compiler.pipeline.translate.symbol.control.identifiers;

import nanoverse.compiler.pipeline.instantiate.loader.Loader;
import nanoverse.compiler.pipeline.instantiate.loader.control.identifiers.CoordinateLoader;
import nanoverse.compiler.pipeline.translate.symbol.*;
import nanoverse.compiler.pipeline.translate.symbol.primitive.integers.IntegerClassSymbolTable;
import nanoverse.runtime.control.identifiers.Coordinate;

import java.util.HashMap;

/**
 * Created by dbborens on 7/25/2015.
 */
public class CoordinateInstSymbolTable extends MapSymbolTable<Coordinate> {
    @Override
    public String getDescription() {
        return "A coordinate specifies a location in the simulation space.";
    }

    @Override
    protected HashMap<String, MemberSymbol> resolveMembers() {
        HashMap<String, MemberSymbol> ret = super.resolveMembers();
        x(ret);
        y(ret);
        z(ret);
        flags(ret);
        return ret;
    }

    private void loadSymbol(HashMap<String, MemberSymbol> ret, String letter, String applicable) {
        ResolvingSymbolTable rst = new IntegerClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "The " + letter + " coordinate, for " + applicable + " geometries.");
        ret.put(letter, ms);
    }

    private void x(HashMap<String, MemberSymbol> ret) {
        loadSymbol(ret, "x", "2D and 3D");
    }

    private void y(HashMap<String, MemberSymbol> ret) {
        loadSymbol(ret, "y", "all");
    }

    private void z(HashMap<String, MemberSymbol> ret) {
        loadSymbol(ret, "z", "3D");
    }

    private void flags(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new IntegerClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "LEGACY: Special flags for the coordinate. Generally not relevant.");
        ret.put("flags", ms);
    }

    @Override
    public Loader getLoader() {
        return new CoordinateLoader();
    }
}
