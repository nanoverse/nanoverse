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

package nanoverse.compiler.pipeline.translate.symbol.io.visual;

import nanoverse.compiler.pipeline.instantiate.loader.Loader;
import nanoverse.compiler.pipeline.instantiate.loader.io.visual.KymographLoader;
import nanoverse.compiler.pipeline.instantiate.loader.io.visual.highlight.HighlightManagerLoader;
import nanoverse.compiler.pipeline.translate.symbol.*;
import nanoverse.compiler.pipeline.translate.symbol.io.visual.color.ColorModelClassSymbolTable;
import nanoverse.compiler.pipeline.translate.symbol.io.visual.highlight.HighlightClassSymbolTable;
import nanoverse.compiler.pipeline.translate.symbol.primitive.integers.IntegerClassSymbolTable;
import nanoverse.runtime.io.visual.kymograph.Kymograph;

import java.util.HashMap;

/**
 * Created by dbborens on 7/27/2015.
 */
public class KymographInstSymbolTable extends MapSymbolTable<Kymograph> {
    @Override
    public String getDescription() {
        return "A kymograph is a visualization for 1D (Line) arenas whose y-" +
            "axis corresponds to coordinate and whose x-axis corresponds " +
            "to time. One file is created per simulation instance.";
    }

    @Override
    protected HashMap<String, MemberSymbol> resolveMembers() {
        HashMap<String, MemberSymbol> ret = super.resolveMembers();
        edge(ret);
        outline(ret);
        color(ret);
        highlights(ret);
        return ret;
    }

    public void highlights(HashMap<String, MemberSymbol> ret) {
        ClassSymbolTable cst = new HighlightClassSymbolTable();
        ResolvingSymbolTable rst = new ListSymbolTable<>(cst, HighlightManagerLoader::new);
        MemberSymbol ms = new MemberSymbol(rst, "Specifies which highlight " +
            "channels to visualize, if any, and how they should be " +
            "visualized.");
        ret.put("highlights", ms);
    }

    public void color(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new ColorModelClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "Specifies how simulation " +
            "state should be encoded into color, if at all.");
        ret.put("color", ms);
    }

    public void outline(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new IntegerClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "Specifies the thickness of " +
            "the outline around each lattice site.");
        ret.put("outline", ms);
    }

    public void edge(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new IntegerClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "Specifies the size of each " +
            "lattice site edge (and, by extension, the size of the image " +
            "as a whole).");
        ret.put("edge", ms);

    }

    @Override
    public Loader getLoader() {
        return new KymographLoader();
    }
}
