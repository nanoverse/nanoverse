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

package nanoverse.compiler.pipeline.translate.symbol.io.visual.highlight;

import nanoverse.compiler.pipeline.instantiate.loader.Loader;
import nanoverse.compiler.pipeline.instantiate.loader.io.visual.highlight.CrosshairsGlyphLoader;
import nanoverse.compiler.pipeline.translate.symbol.*;
import nanoverse.compiler.pipeline.translate.symbol.primitive.doubles.DoubleClassSymbolTable;
import nanoverse.compiler.pipeline.translate.symbol.primitive.strings.StringClassSymbolTable;
import nanoverse.runtime.io.visual.highlight.CrosshairsGlyph;

import java.util.HashMap;

/**
 * Created by dbborens on 7/28/2015.
 */
public class CrosshairsGlyphInstSymbolTable extends MapSymbolTable<CrosshairsGlyph> {
    @Override
    public String getDescription() {
        return "A crosshairs glyph appears as a circle overlaid with a symmetrical cross.";
    }

    @Override
    public HashMap<String, MemberSymbol> resolveMembers() {
        HashMap<String, MemberSymbol> ret = super.resolveMembers();
        circle(ret);
        cross(ret);
        color(ret);
        return ret;
    }

    private void color(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new StringClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "Hexadecimal representation of the glyph's color (RRGGBB).");
        ret.put("color", ms);
    }

    private void cross(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new DoubleClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "Size of the cross of the " +
            "crosshairs, specified as a multiple of the size of the " +
            "circle component of the crosshairs.");
        ret.put("cross", ms);
    }

    private void circle(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new DoubleClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "Size of the circle of the " +
            "crosshairs, specified as a multiple of the size of the " +
            "lattice site in the visualization.");
        ret.put("circle", ms);
    }

    @Override
    public Loader getLoader() {
        return new CrosshairsGlyphLoader();
    }
}
