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
import nanoverse.compiler.pipeline.instantiate.loader.io.visual.color.SurfaceColorModelLoader;
import nanoverse.compiler.pipeline.translate.symbol.*;
import nanoverse.compiler.pipeline.translate.symbol.primitive.doubles.DoubleClassSymbolTable;
import nanoverse.runtime.io.visual.color.SurfaceColorModel;

import java.util.HashMap;

/**
 * Created by dbborens on 7/27/2015.
 */
public class SurfaceColorModelInstSymbolTable extends MapSymbolTable<SurfaceColorModel> {
    @Override
    public String getDescription() {
        return "The surface growth color model is an overlay on another " +
            "color model. It adjusts the luminance and saturation of a " +
            "given site by a set scaling factor if (and only if) the " +
            "nanoverse.runtime.agent on that site has at least one adjacent vacancy.";
    }

    @Override
    public HashMap<String, MemberSymbol> resolveMembers() {
        HashMap<String, MemberSymbol> ret = super.resolveMembers();
        base(ret);
        saturationScale(ret);
        luminanceScale(ret);
        return ret;
    }

    private void base(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new ColorModelClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "The color model upon which " +
            "this color model should be overlaid.");
        ret.put("base", ms);
    }

    private void saturationScale(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new DoubleClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "The factor by which the " +
            "saturation of the base model should be scaled if a site has " +
            "an adjacent vacancy.");
        ret.put("saturation", ms);
    }

    private void luminanceScale(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new DoubleClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "The factor by which the " +
            "luminance of the base model should be scaled if a site has " +
            "an adjacent vacancy.");
        ret.put("luminance", ms);
    }

    @Override
    public Loader getLoader() {
        return new SurfaceColorModelLoader();
    }
}
