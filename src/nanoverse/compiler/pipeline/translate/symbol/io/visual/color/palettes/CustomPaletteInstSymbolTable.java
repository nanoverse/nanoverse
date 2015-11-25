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

package nanoverse.compiler.pipeline.translate.symbol.io.visual.color.palettes;

import nanoverse.compiler.pipeline.instantiate.loader.Loader;
import nanoverse.compiler.pipeline.instantiate.loader.io.visual.color.palette.ColorMapLoader;
import nanoverse.compiler.pipeline.instantiate.loader.io.visual.color.palette.CustomPaletteLoader;
import nanoverse.compiler.pipeline.translate.symbol.ClassSymbolTable;
import nanoverse.compiler.pipeline.translate.symbol.DictionarySymbolTable;
import nanoverse.compiler.pipeline.translate.symbol.MemberSymbol;
import nanoverse.compiler.pipeline.translate.symbol.ResolvingSymbolTable;
import nanoverse.compiler.pipeline.translate.symbol.primitive.strings.StringClassSymbolTable;
import nanoverse.runtime.io.visual.color.palettes.CustomPalette;

import java.util.HashMap;

/**
 * Created by dbborens on 11/25/2015.
 */
public class CustomPaletteInstSymbolTable extends PaletteInstSymbolTable<CustomPalette<String>> {

    @Override
    public Loader getLoader() {
        return new CustomPaletteLoader<>();
    }

    @Override
    public String getDescription() {
        return "A custom mapping from agent class names to colors.";
    }

    @Override
    public HashMap<String, MemberSymbol> resolveMembers() {
        HashMap<String, MemberSymbol> ret = super.resolveMembers();
        mappings(ret);
        return ret;
    }

    private void mappings(HashMap<String, MemberSymbol> ret) {
        ClassSymbolTable cst = new StringClassSymbolTable();
        ResolvingSymbolTable rst = new DictionarySymbolTable<>(cst, ColorMapLoader::new);
        MemberSymbol ms = new MemberSymbol(rst, "List of agent class names and their associated colors.");
        ret.put("mappings", ms);
    }
}
