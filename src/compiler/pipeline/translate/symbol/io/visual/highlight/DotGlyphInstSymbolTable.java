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

package compiler.pipeline.translate.symbol.io.visual.highlight;

import compiler.pipeline.instantiate.loader.Loader;
import compiler.pipeline.instantiate.loader.io.visual.highlight.DotGlyphLoader;
import compiler.pipeline.translate.nodes.ObjectNode;
import compiler.pipeline.translate.symbol.*;
import compiler.pipeline.translate.symbol.primitive.doubles.DoubleClassSymbolTable;
import compiler.pipeline.translate.symbol.primitive.strings.StringClassSymbolTable;
import io.visual.highlight.DotGlyph;

import java.util.HashMap;

/**
 * Created by dbborens on 7/28/2015.
 */
public class DotGlyphInstSymbolTable extends MapSymbolTable<DotGlyph> {
    @Override
    public String getDescription() {
        return "A dot glyph appears as a filled circle.";
    }

    @Override
    protected HashMap<String, MemberSymbol> resolveMembers() {
        HashMap<String, MemberSymbol> ret = super.resolveMembers();
        color(ret);
        size(ret);
        return ret;
    }

    private void size(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new DoubleClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "The size of the glyph, as a " +
                "fraction of the size of the lattice site in the " +
                "visualization.");
        ret.put("size", ms);
    }

    private void color(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new StringClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "Hexadecimal representation of the dot's color (RRGGBB).");
        ret.put("color", ms);
    }

    @Override
    public Loader getLoader() {
        return new DotGlyphLoader();
    }
}
