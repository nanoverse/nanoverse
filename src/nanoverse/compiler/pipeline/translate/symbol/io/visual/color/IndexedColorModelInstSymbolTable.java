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

package nanoverse.compiler.pipeline.translate.symbol.io.visual.color;

import nanoverse.compiler.pipeline.instantiate.loader.Loader;
import nanoverse.compiler.pipeline.instantiate.loader.io.visual.color.IndexedColorModelLoader;
import nanoverse.compiler.pipeline.translate.symbol.MapSymbolTable;
import nanoverse.compiler.pipeline.translate.symbol.MemberSymbol;
import nanoverse.compiler.pipeline.translate.symbol.ResolvingSymbolTable;
import nanoverse.compiler.pipeline.translate.symbol.io.visual.color.palettes.PaletteClassSymbolTable;
import nanoverse.runtime.io.visual.color.IndexedColorModel;

import java.util.HashMap;

/**
 * Created by dbborens on 7/27/2015.
 */
public class IndexedColorModelInstSymbolTable extends MapSymbolTable<IndexedColorModel> {
    @Override
    public String getDescription() {
        return "The indexed color model has a specific color associated with " +
            "each given cell state (class). It is the default color model.";
    }

    @Override
    public HashMap<String, MemberSymbol> resolveMembers() {
        HashMap<String, MemberSymbol> ret =  super.resolveMembers();
        palette(ret);
        return ret;
    }

    private void palette(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new PaletteClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "The color palette to be used.");
        ret.put("palette", ms);
    }

    @Override
    public Loader getLoader() {
        return new IndexedColorModelLoader();
    }
}
