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

package nanoverse.compiler.pipeline.translate.symbol.io.visual.highlight;

import nanoverse.compiler.pipeline.instantiate.loader.Loader;
import nanoverse.compiler.pipeline.instantiate.loader.io.visual.highlight.BullseyeGlyphLoader;
import nanoverse.compiler.pipeline.translate.symbol.*;
import nanoverse.compiler.pipeline.translate.symbol.primitive.doubles.DoubleClassSymbolTable;
import nanoverse.compiler.pipeline.translate.symbol.primitive.strings.StringClassSymbolTable;
import nanoverse.runtime.io.visual.highlight.BullseyeGlyph;

import java.util.HashMap;

/**
 * Created by dbborens on 7/28/2015.
 */
public class BullseyeGlyphInstSymbolTable extends MapSymbolTable<BullseyeGlyph> {
    @Override
    public String getDescription() {
        return "A bullseye glyph appears as a series of concentric circles with alternating colors.";
    }

    @Override
    protected HashMap<String, MemberSymbol> resolveMembers() {
        HashMap<String, MemberSymbol> ret = super.resolveMembers();
        primaryColor(ret);
        secondaryColor(ret);
        size(ret);
        return ret;
    }

    public void size(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new DoubleClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "Size of the bullseye, " +
            "specified as a multiple of the coordinate site in the " +
            "visualization.");
        ret.put("size", ms);
    }

    public void primaryColor(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new StringClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "Primary color of the " +
            "bullseye, represented as a hexidecimal RGB code (RRGGBB).");
        ret.put("primaryColor", ms);
    }

    public void secondaryColor(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new StringClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "Secondary color of the " +
            "bullseye, represented as a hexidecimal RGB code (RRGGBB).");
        ret.put("secondaryColor", ms);
    }

    @Override
    public Loader getLoader() {
        return new BullseyeGlyphLoader();
    }
}
